package ru.samsung.itacademy.mdev.recycleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import ru.samsung.itacademy.mdev.recycleapp.models.SubCategoriesModel

class SubCategoriesViewModel : ViewModel() {
    private val _subCategoriesData = MutableLiveData<List<SubCategoriesModel>>()
    val subCategoriesData: LiveData<List<SubCategoriesModel>> get() = _subCategoriesData

    private lateinit var dbRef: DatabaseReference

    fun fetchSubCategoriesData(categoryName: String) {
        dbRef = FirebaseDatabase.getInstance("https://recycle-app-5da3d-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("SubCategories")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val subCategoriesList = mutableListOf<SubCategoriesModel>()
                if (snapshot.exists()) {
                    for (subCategoriesSnap in snapshot.children) {
                        val subCategoriesData = subCategoriesSnap.getValue(SubCategoriesModel::class.java)
                        if (subCategoriesData?.categoryName == categoryName) {
                            subCategoriesData.let { subCategoriesList.add(it) }
                        }
                    }
                    subCategoriesList.sortBy { it.categoryInfoID }
                }
                _subCategoriesData.value = subCategoriesList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}