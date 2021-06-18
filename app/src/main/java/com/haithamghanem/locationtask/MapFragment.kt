package com.haithamghanem.locationtask

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.LocationListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() , LocationListener{
    companion object{
        var markerLatitude: Double = 0.0
        var markerLongitude: Double = 0.0
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment



        supportMapFragment.getMapAsync {
            val markerOptionsTargetLocation  = MarkerOptions()
            val markerOptionsCurrentLocation  = MarkerOptions()


            markerOptionsTargetLocation.
            position(LatLng(31.2744807,30.0078468))
                .title("Destination")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            it.addMarker(markerOptionsTargetLocation)

            val googleMap = it

            markerOptionsCurrentLocation.position(LatLng(markerLatitude, markerLongitude))
                .title("Current Position lat: $markerLatitude/ lon: $markerLongitude")
                it.addMarker(markerOptionsCurrentLocation)

            it.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(markerLatitude,
                markerLongitude),8F))

            Log.d("TAG", "in map $markerLatitude///$markerLongitude")

        }
    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }


}