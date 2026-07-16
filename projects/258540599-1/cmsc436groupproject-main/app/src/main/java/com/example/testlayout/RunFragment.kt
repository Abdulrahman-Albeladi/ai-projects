package com.example.testlayout

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject
import java.time.LocalDate
import java.util.Timer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class RunFragment : Fragment() {

    private lateinit var startStopButton: Button
    private lateinit var stopwatchTextView: TextView
    private lateinit var headerTextView: TextView
    private lateinit var speedTextView: TextView

    private lateinit var executor: ExecutorService
    private var stopwatchTask: Future<*>? = null
    private lateinit var stopwatchRunnable: StopwatchRunnable

    private lateinit var hostActivity: Activity
    private lateinit var sensorManager: SensorManager
    private var linearAccelerationSensor: Sensor? = null
    private val sensorEventListener = LinearAccelerationListener()

    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private val locationCallback = RunLocationCallback()

    private lateinit var database: DatabaseReference
    private var logTimer: Timer? = null

    private var clockStarted = false
    private var runEnded = false
    private var startLocation: Location? = null
    private var endLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.fragment_run, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hostActivity = requireActivity()
        database = FirebaseDatabase.getInstance().reference

        startStopButton = view.findViewById(R.id.run_start_stop_button)
        stopwatchTextView = view.findViewById(R.id.run_stopwatch)
        headerTextView = view.findViewById(R.id.run_header)
        speedTextView = view.findViewById(R.id.run_speed)

        initializeSensor()
        initializeLocationUpdates()
        createStopwatch()

        startStopButton.setOnClickListener {
            when (startStopButton.text.toString()) {
                START_LABEL -> startRun()
                STOP_LABEL -> stopRun()
            }
        }

        stopwatchTextView.setOnLongClickListener {
            saveAndResetRun()
            true
        }

        logTimer = Timer().apply {
            schedule(LogTask(sensorEventListener), 0L, LOG_INTERVAL_MILLIS)
        }
    }

    private fun initializeSensor() {
        sensorManager = hostActivity.getSystemService(Activity.SENSOR_SERVICE) as SensorManager
        linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        linearAccelerationSensor?.let { sensor ->
            sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL,
            )
        } ?: Log.w(TAG, "Linear acceleration sensor is unavailable")
    }

    private fun initializeLocationUpdates() {
        locationClient = LocationServices.getFusedLocationProviderClient(hostActivity)
        locationRequest = LocationRequest.Builder(LOCATION_INTERVAL_MILLIS)
            .setMaxUpdateAgeMillis(MAX_LOCATION_AGE_MILLIS)
            .build()

        requestLocationUpdatesIfPermitted()
    }

    private fun requestLocationUpdatesIfPermitted() {
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(
                hostActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
                LOCATION_PERMISSION_REQUEST_CODE,
            )
            return
        }

        locationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper(),
        )
    }

    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            hostActivity,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            hostActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun createStopwatch() {
        executor = Executors.newSingleThreadExecutor()
        stopwatchRunnable = StopwatchRunnable(this, stopwatchTextView)
        stopwatchTask = executor.submit(stopwatchRunnable)
    }

    private fun startRun() {
        Log.i(TAG, "Stopwatch started")
        stopwatchRunnable.resume()
        startStopButton.text = STOP_LABEL
        headerTextView.text = RUNNING_LABEL

        clockStarted = true
        runEnded = false
        startLocation = null
        endLocation = null

        getCurrentLocation { location ->
            if (location != null && startLocation == null) {
                startLocation = location
            }
        }
    }

    private fun stopRun() {
        Log.i(TAG, "Stopwatch stopped")
        stopwatchRunnable.pause()
        startStopButton.text = START_LABEL
        headerTextView.text = STOPPED_LABEL

        clockStarted = false
        runEnded = true

        getCurrentLocation { location ->
            if (location != null) {
                endLocation = location
            }
        }

        submitLongestRunTime(stopwatchRunnable.totalTime)
    }

    private fun saveAndResetRun() {
        getCurrentLocation { location ->
            if (location != null) {
                endLocation = location
            }
            storeLocalPreferences(hostActivity, stopwatchRunnable)
            resetStopwatch()
        }
    }

    private fun getCurrentLocation(onLocationReceived: (Location?) -> Unit) {
        if (!hasLocationPermission()) {
            Log.w(TAG, "Location permission has not been granted")
            onLocationReceived(null)
            return
        }

        locationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener(onLocationReceived)
            .addOnFailureListener { exception ->
                Log.e(TAG, "Could not get current location", exception)
                onLocationReceived(null)
            }
    }

    private fun resetStopwatch() {
        stopwatchTask?.cancel(true)
        executor.shutdownNow()

        stopwatchTextView.text = DEFAULT_STOPWATCH_TEXT
        startStopButton.text = START_LABEL
        headerTextView.text = STOPPED_LABEL
        speedTextView.text = ""

        clockStarted = false
        runEnded = false
        startLocation = null
        endLocation = null

        createStopwatch()
    }

    fun storeLocalPreferences(context: Context, stopwatchRunnable: StopwatchRunnable) {
        val run = JSONObject().apply {
            put("date", LocalDate.now().toString())

            val distance = if (startLocation != null && endLocation != null) {
                startLocation!!.distanceTo(endLocation!!)
            } else {
                Toast.makeText(context, "Invalid location data", Toast.LENGTH_SHORT).show()
                0.0f
            }

            put("dist", distance)
            put("run time", stopwatchRunnable.totalTime)
            put("average accel", "${sensorEventListener.avgVelocity}mph")
        }

        val preferences: SharedPreferences = context.getSharedPreferences(
            "${context.packageName}_preferences",
            Context.MODE_PRIVATE,
        )

        preferences.edit()
            .putString("run ${StopwatchRunnable.totalRuns}", run.toString())
            .apply()

        Log.w(TAG, run.toString())
    }

    private fun submitLongestRunTime(runTime: String) {
        val leaderboardEntry = LeaderboardEntry(runTime)
        database.child("leaderboard").push().setValue(leaderboardEntry)
            .addOnSuccessListener {
                Log.w(TAG, "Leaderboard entry written: $runTime")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to write leaderboard entry", exception)
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && hasLocationPermission()) {
            requestLocationUpdatesIfPermitted()
        }
    }

    override fun onDestroyView() {
        linearAccelerationSensor?.let {
            sensorManager.unregisterListener(sensorEventListener, it)
        }
        locationClient.removeLocationUpdates(locationCallback)
        stopwatchTask?.cancel(true)
        executor.shutdownNow()
        logTimer?.cancel()
        logTimer = null

        super.onDestroyView()
    }

    inner class LinearAccelerationListener : SensorEventListener {
        private val velocities = mutableListOf<Double>()
        private var previousVelocity = 0.0
        private var velocity = 0.0

        var avgVelocity: Double = 0.0
            private set

        fun logVelocity() {
            Log.i(TAG, "speed = $velocity")
        }

        fun logAvgVelocity() {
            Log.i(TAG, "avg speed = $avgVelocity")
        }

        override fun onSensorChanged(event: SensorEvent?) {
            val sensor = linearAccelerationSensor ?: return
            if (event?.sensor != sensor || event.values.size < 3) {
                return
            }

            val accelerationMagnitude = kotlin.math.sqrt(
                event.values.sumOf { value -> value.toDouble() * value.toDouble() },
            )
            velocity = previousVelocity + accelerationMagnitude * SENSOR_INTERVAL_SECONDS
            previousVelocity = velocity

            velocities.add(velocity)
            avgVelocity = velocities.average()

            if (clockStarted) {
                speedTextView.text = String.format(Locale.US, "%.2f mph", velocity)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
    }

    inner class RunLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation ?: run {
                Log.i(TAG, "Location could not be updated")
                return
            }

            if (clockStarted && startLocation == null) {
                startLocation = location
            }
            if (runEnded) {
                endLocation = location
            }
        }
    }

    companion object {
        private const val TAG = "RunFragment"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 101
        private const val LOCATION_INTERVAL_MILLIS = 100L
        private const val MAX_LOCATION_AGE_MILLIS = 1_000L
        private const val LOG_INTERVAL_MILLIS = 5_000L
        private const val SENSOR_INTERVAL_SECONDS = 0.001

        private const val START_LABEL = "START"
        private const val STOP_LABEL = "STOP"
        private const val RUNNING_LABEL = "Running"
        private const val STOPPED_LABEL = "Stopped"
        private const val DEFAULT_STOPWATCH_TEXT = "00:00:00"
    }
}
