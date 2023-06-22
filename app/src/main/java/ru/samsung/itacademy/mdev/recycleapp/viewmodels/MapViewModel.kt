package ru.samsung.itacademy.mdev.recycleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.runtime.image.ImageProvider

data class PlacemarkIcon(val location: Point, val imageProvider: ImageProvider)

class MapViewModel : ViewModel() {
    private val _mapObjects = MutableLiveData<MapObjectCollection>()
    val mapObjects: LiveData<MapObjectCollection> = _mapObjects

    private val _placemarkIcon = MutableLiveData<PlacemarkIcon?>()
    val placemarkIcon: LiveData<PlacemarkIcon?> = _placemarkIcon

    fun addPlacemarkIcon(icon: PlacemarkIcon) {
        _placemarkIcon.value = icon
    }

    fun clearMapObjects() {
        mapObjects.value?.clear()
    }
}