package ru.samsung.itacademy.mdev.recycleapp.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import ru.samsung.itacademy.mdev.recycleapp.R
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import ru.samsung.itacademy.mdev.recycleapp.viewmodels.MapViewModel

class MapFragment : Fragment(), Session.SearchListener {

    private lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var searchManager: SearchManager
    private lateinit var searchSession: Session
    private lateinit var mapViewModel: MapViewModel
    private var hasLocationPermission = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        mapView = view.findViewById(R.id.map_view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        requestLocationPermission()

        val locationCard = view.findViewById<CardView>(R.id.location_card)
        locationCard.setOnClickListener {
            moveToCurrentLocation()
        }

        mapView.map.addCameraListener(object : CameraListener {
            override fun onCameraPositionChanged(
                p0: Map,
                p1: CameraPosition,
                p2: CameraUpdateReason,
                p3: Boolean
            ) {
                performSearch("вторсырье")
            }
        })

        mapViewModel.placemarkIcon.observe(viewLifecycleOwner, Observer { icon ->
            if (icon != null) {
                val placemark = mapView.map.mapObjects.addPlacemark(icon.location)
                placemark.setIcon(icon.imageProvider)
            }
        })

        return view
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            hasLocationPermission = true
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        if (hasLocationPermission) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            val currentPoint = Point(it.latitude, it.longitude)
                            mapView.map.move(
                                CameraPosition(currentPoint, 15.0f, 0.0f, 0.0f),
                                Animation(Animation.Type.SMOOTH, 2f),
                                null
                            )
                            performSearch("вторсырье")
                        }
                    }
            }
        }
    }

    private fun moveToCurrentLocation() {
        getCurrentLocation()
    }

    private fun performSearch(query: String) {
        val visibleRegion = mapView.map.visibleRegion
        val geometry = Geometry.fromBoundingBox(BoundingBox(visibleRegion.bottomLeft, visibleRegion.topRight))

        searchSession = searchManager.submit(
            query,
            geometry,
            SearchOptions(),
            this
        )
    }

    override fun onSearchResponse(response: Response) {
        val mapObjects = mapView.map.mapObjects
        mapObjects.clear()

        if (!isAdded) {
            return
        }

        for (searchResult in response.collection.children) {
            val resultLocation = searchResult.obj?.geometry?.get(0)?.point
            if (resultLocation != null) {
                val placemark = mapObjects.addPlacemark(resultLocation)
                placemark.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.place))
            }
        }
    }


    override fun onSearchError(p0: Error) {
        val errorMessage = "Произошла ошибка при поиске."
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hasLocationPermission = true
                getCurrentLocation()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 100
    }
}
