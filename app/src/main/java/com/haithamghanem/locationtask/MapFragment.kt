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




//            it.setOnMapClickListener {
//
//
//                //hna awl mados 3ayz a3ml marker fa h3ml init lel marker options
//                val markerOptionsOfCurrentLocation = MarkerOptions()
//
//                //b3den lazm agip el posisiton bta3 el marker
//                markerOptionsOfCurrentLocation.position(it)
//
//
////                //ha7ot el position el lat wl longitude fl eli 3ayz aro7lohom
////                markerLatitude = it.latitude
////                markerLongitude = it.longitude
//
//
//                //b3den ha3ml title lel marker bl latitude wl longitude bta3 el makan eli wa2f 3leh
//                markerOptionsOfCurrentLocation.title("${it.latitude} : ${it.longitude}")
//
//                //b3den a3ml remover le kol el markers 3shan lma a3ml zoom el marker el adim yro7 w a7ot marker gded
//                googleMap.clear()
//
//                //b3den a3ml animation 3shan a3ml zoom 3ala el marker
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 10F))
//
//                //add another marker in the new position
//                googleMap.addMarker(markerOptionsOfCurrentLocation)
//                googleMap.addMarker(markerOptionsTargetLocation)
//            }
        }
    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }


}