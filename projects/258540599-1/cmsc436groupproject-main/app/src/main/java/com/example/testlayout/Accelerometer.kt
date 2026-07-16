package com.example.testlayout

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

class Accelerometer(context: Context) {

    private val sensorManager: SensorManager =
        requireNotNull(getSystemService(context, Context.SENSOR_SERVICE::class.java)) {
            "Sensor service is unavailable."
        }

    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor != sensor || event.values.size < 3) {
                return
            }

            val x = event.values[0].toDouble()
            val y = event.values[1].toDouble()
            val z = event.values[2].toDouble()
            val linearAccelerationMagnitude = Math.sqrt(x * x + y * y + z * z)

            Log.i(LOG_TAG, linearAccelerationMagnitude.toString())
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
    }

    init {
        sensor?.let {
            sensorManager.registerListener(
                sensorEventListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    fun getSensorManager(): SensorManager = sensorManager

    fun unregister() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    private companion object {
        const val LOG_TAG = "RunFragment"
    }
}
