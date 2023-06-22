package ru.samsung.itacademy.mdev.recycleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import ru.samsung.itacademy.mdev.recycleapp.models.AdvicesModel
import ru.samsung.itacademy.mdev.recycleapp.models.FactsModel

class HomeViewModel : ViewModel() {
    private val _advicesData = MutableLiveData<List<AdvicesModel>>()
    val advicesData: LiveData<List<AdvicesModel>> get() = _advicesData

    private val _factDescription = MutableLiveData<String>()
    val factDescription: LiveData<String> get() = _factDescription

    private lateinit var dbRef: DatabaseReference

    init {
        fetchAdvicesData()
        fetchRandomFact()
    }

    fun fetchAdvicesData() {
        dbRef = FirebaseDatabase.getInstance("https://recycle-app-5da3d-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("Advices")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val advicesList = mutableListOf<AdvicesModel>()
                if (snapshot.exists()) {
                    for (advicesSnap in snapshot.children) {
                        val advicesData = advicesSnap.getValue(AdvicesModel::class.java)
                        advicesData?.let { advicesList.add(it) }
                    }
                    advicesList.sortBy { it.adviceID }
                }
                _advicesData.value = advicesList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    fun fetchRandomFact() {
        dbRef = FirebaseDatabase.getInstance("https://recycle-app-5da3d-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("Facts")

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val factCount = snapshot.childrenCount.toInt()
                val randomFactID = (1..factCount).random().toString()

                for (factSnap in snapshot.children) {
                    val factData = factSnap.getValue(FactsModel::class.java)
                    val factID = factData?.factID
                    if (factID == randomFactID) {
                        _factDescription.value = factData?.factDescription
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}