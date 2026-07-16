package com.example.testlayout

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class SettingsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_setting, container, false).also { view ->
            recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
                layoutManager = LinearLayoutManager(requireContext())
            }

            childFragmentManager.commit {
                replace(R.id.settings_container, PreferenceFragment.newInstance())
            }
        }
    }

    class PreferenceFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var database: DatabaseReference
        private lateinit var leaderboardQuery: Query

        private var recyclerView: RecyclerView? = null
        private var dataDisplay = DEFAULT_DATA_DISPLAY
        private var leaderboardEntries: List<LeaderboardEntry> = emptyList()

        private val leaderboardListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                leaderboardEntries = snapshot.children.mapNotNull { child ->
                    child.getValue(LeaderboardEntry::class.java)
                }.sortedByDescending { entry ->
                    entry.runTime.toLongOrNull() ?: Long.MIN_VALUE
                }
                updateLeaderboardAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Error fetching leaderboard: ${error.message}")
            }
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            sharedPreferences = requireNotNull(preferenceManager.sharedPreferences)
            sharedPreferences.registerOnSharedPreferenceChangeListener(this)

            database = FirebaseDatabase.getInstance().reference
            leaderboardQuery = database.child(LEADERBOARD_PATH).orderByChild(RUN_TIME_KEY)

            findPreference<Preference>(RESET_APP_KEY)?.setOnPreferenceClickListener {
                resetAppData()
                true
            }

            initializePreferences()
        }

        override fun onResume() {
            super.onResume()
            recyclerView = (parentFragment as? SettingsFragment)?.recyclerView
            updateLeaderboardAdapter()
        }

        override fun onPause() {
            recyclerView = null
            super.onPause()
        }

        override fun onDestroy() {
            if (::leaderboardQuery.isInitialized) {
                leaderboardQuery.removeEventListener(leaderboardListener)
            }
            if (::sharedPreferences.isInitialized) {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
            }
            super.onDestroy()
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
            when (key) {
                BACKGROUND_COLOR_KEY -> {
                    updateBackgroundColor(
                        sharedPreferences.getString(BACKGROUND_COLOR_KEY, DEFAULT_BACKGROUND_COLOR)
                            ?: DEFAULT_BACKGROUND_COLOR
                    )
                }
                DATA_DISPLAY_KEY -> {
                    updateDataDisplay(
                        sharedPreferences.getString(DATA_DISPLAY_KEY, DEFAULT_DATA_DISPLAY)
                            ?: DEFAULT_DATA_DISPLAY
                    )
                }
            }
        }

        private fun initializePreferences() {
            updateBackgroundColor(
                sharedPreferences.getString(BACKGROUND_COLOR_KEY, DEFAULT_BACKGROUND_COLOR)
                    ?: DEFAULT_BACKGROUND_COLOR
            )
            updateDataDisplay(
                sharedPreferences.getString(DATA_DISPLAY_KEY, DEFAULT_DATA_DISPLAY)
                    ?: DEFAULT_DATA_DISPLAY
            )
            leaderboardQuery.addValueEventListener(leaderboardListener)
        }

        private fun updateBackgroundColor(color: String) {
            showToast("Background color changed to $color")

            val colorValue = when (color) {
                "blue" -> Color.BLUE
                "green" -> Color.GREEN
                "red" -> Color.RED
                else -> Color.WHITE
            }

            view?.post {
                view?.setBackgroundColor(colorValue)
            }
        }

        private fun updateDataDisplay(selection: String) {
            dataDisplay = selection
            showToast("Data display set to $selection")
            updateLeaderboardAdapter()
        }

        private fun updateLeaderboardAdapter() {
            val displayedEntries = when (dataDisplay) {
                "top3" -> leaderboardEntries.take(3)
                "top" -> leaderboardEntries.take(1)
                else -> leaderboardEntries
            }

            val targetRecyclerView = recyclerView ?: return
            targetRecyclerView.post {
                targetRecyclerView.adapter = LeaderboardAdapter(displayedEntries)
            }
        }

        private fun resetAppData() {
            showToast("App data reset!")
            sharedPreferences.edit().clear().apply()
            clearFirebaseData()
            activity?.recreate()
        }

        private fun clearFirebaseData() {
            database.child(LEADERBOARD_PATH).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Leaderboard data cleared")
                } else {
                    showToast("Failed to clear leaderboard data")
                }
            }
        }

        private fun showToast(message: String) {
            if (isAdded) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        companion object {
            private const val BACKGROUND_COLOR_KEY = "background_color"
            private const val DATA_DISPLAY_KEY = "data_display"
            private const val RESET_APP_KEY = "reset_app"
            private const val DEFAULT_BACKGROUND_COLOR = "default"
            private const val DEFAULT_DATA_DISPLAY = "top"
            private const val LEADERBOARD_PATH = "leaderboard"
            private const val RUN_TIME_KEY = "runTime"

            @JvmStatic
            fun newInstance() = PreferenceFragment()
        }
    }
}
