package ru.samsung.itacademy.mdev.recycleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import ru.samsung.itacademy.mdev.recycleapp.models.CategoriesModel

class CategoriesViewModel : ViewModel() {
    private val _categoriesData = MutableLiveData<List<CategoriesModel>>()
    val categoriesData: LiveData<List<CategoriesModel>> get() = _categoriesData

    private lateinit var dbRef: DatabaseReference

    init {
        fetchCategoriesData()
    }

    private fun fetchCategoriesData() {
        dbRef = FirebaseDatabase.getInstance("https://recycle-app-5da3d-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("Categories")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categoriesList = mutableListOf<CategoriesModel>()
                if (snapshot.exists()) {
                    for (categoriesSnap in snapshot.children) {
                        val categoriesData = categoriesSnap.getValue(CategoriesModel::class.java)
                        categoriesData?.let { categoriesList.add(it) }
                    }
                    categoriesList.sortBy { it.categoryID }
                }
                _categoriesData.value = categoriesList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}