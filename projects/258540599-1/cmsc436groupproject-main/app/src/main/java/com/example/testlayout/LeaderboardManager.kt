package com.example.testlayout

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class LeaderboardManager(private val context: Context) {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun displayLeaderboard(recyclerView: RecyclerView) {
        database.child("leaderboard")
            .orderByChild("runTime")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val leaderboard = snapshot.children.mapNotNull { data ->
                        data.getValue(LeaderboardEntry::class.java)
                    }.sortedByDescending { entry ->
                        convertTimeToMillis(entry.runTime)
                    }

                    updateRecyclerView(recyclerView, leaderboard)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Unable to fetch leaderboard data", error.toException())
                }
            })
    }

    private fun updateRecyclerView(
        recyclerView: RecyclerView,
        leaderboard: List<LeaderboardEntry>
    ) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = LeaderboardAdapter(leaderboard)
    }

    private fun convertTimeToMillis(time: String): Long {
        return try {
            val formatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
                isLenient = false
            }
            formatter.parse(time)?.time ?: 0L
        } catch (exception: Exception) {
            Log.e(TAG, "Unable to parse leaderboard runtime: $time", exception)
            0L
        }
    }

    private companion object {
        const val TAG = "LeaderboardManager"
        const val TIME_FORMAT = "HH:mm:ss"
    }
}

class LeaderboardAdapter(private val leaderboard: List<LeaderboardEntry>) :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    class LeaderboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rankTextView: TextView = itemView.findViewById(R.id.rank)
        val runTimeTextView: TextView = itemView.findViewById(R.id.run_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.leaderboard_item, parent, false)
        return LeaderboardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val entry = leaderboard[position]
        holder.rankTextView.text = (position + 1).toString()
        holder.runTimeTextView.text = entry.runTime
    }

    override fun getItemCount(): Int = leaderboard.size
}
