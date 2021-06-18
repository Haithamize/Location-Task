package com.haithamghanem.locationtask

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.haithamghanem.locationtask.App.Companion.CHANNEL_ID


class LocationService : Service() {

    companion object{
        var HAS_REACHED_DESTINATION = false
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var targetLocation: Location
    private  var distanceInMeters: Float = 0.0F
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()
        targetLocation = Location("")
        targetLocation.latitude = 31.2744807
        targetLocation.longitude = 30.0078468
        getLocationUpdates()
        startLocationUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Location Task")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
        return START_REDELIVER_INTENT
    }

    private fun getLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create()
        locationRequest.interval = 2000
        locationRequest.fastestInterval = 2000
//        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
        locationRequest.priority =
            LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                Log.d("TAG", "latitude: sayed")
                if (locationResult.locations.isNotEmpty()) {
                    // get latest location
                    val location =
                        locationResult.lastLocation

                    distanceInMeters =  targetLocation.distanceTo(location)
                    Log.d("distance", "onLocationResult: $distanceInMeters")

                    if(distanceInMeters > 0 && distanceInMeters < 2){
                        Toast.makeText(this@LocationService, "You arrived", Toast.LENGTH_SHORT).show()
                        HAS_REACHED_DESTINATION = true
                    }else{
                        HAS_REACHED_DESTINATION = false
                    }

                    MapFragment.markerLatitude = location.latitude
                    MapFragment.markerLongitude = location.longitude

                    Log.d(
                        "TAG",
                        "latitude: ${location.latitude} + longitude: ${location.longitude}"
                    )
                    // use your location object
                    // get latitude , longitude and other info from this
                }
            }
        }
    }
//    private fun isMarkerOutsideCircle(
//        centerLatLng: LatLng,
//        draggedLatLng: LatLng,
//        radius: Double
//    ): Boolean {
//        val distances = FloatArray(1)
//        Location.distanceBetween(
//            centerLatLng.latitude,
//            centerLatLng.longitude,
//            draggedLatLng.latitude,
//            draggedLatLng.longitude, distances
//        )
//        return radius < distances[0]
//    }


    //Permissions were taken when we first opened the application
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}