package ru.samsung.itacademy.mdev.recycleapp.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import ru.samsung.itacademy.mdev.recycleapp.R
import ru.samsung.itacademy.mdev.recycleapp.adapters.SubCategoriesAdapter
import ru.samsung.itacademy.mdev.recycleapp.viewmodels.SubCategoriesViewModel
import androidx.recyclerview.widget.LinearSnapHelper

class SubCategoriesActivity : AppCompatActivity() {

    private lateinit var recViewSubCategories: RecyclerView
    private lateinit var subCategoriesAdapter: SubCategoriesAdapter
    private val subCategoriesViewModel: SubCategoriesViewModel by viewModels()
    private lateinit var categoryName: String

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val descriptionTextView1: TextView = findViewById(R.id.advice_description_1)
        val categoryDescription1 = intent.getStringExtra("categoryDescription1")
        descriptionTextView1.text = categoryDescription1

        val descriptionTextView2: TextView = findViewById(R.id.advice_description_2)
        val categoryDescription2 = intent.getStringExtra("categoryDescription2")
        descriptionTextView2.text = categoryDescription2

        val descriptionTextView3: TextView = findViewById(R.id.advice_description_3)
        val categoryDescription3 = intent.getStringExtra("categoryDescription3")
        descriptionTextView3.text = categoryDescription3

        categoryName = intent.getStringExtra("categoryName")!!
        val collapsingToolbarLayout: CollapsingToolbarLayout = findViewById(R.id.collapsing_tool_bar)
        collapsingToolbarLayout.title = categoryName

        val imageView: ImageView = findViewById(R.id.img_category)
        val imageName = intent.getStringExtra("categoryImageName")
        val resourceId = resources.getIdentifier(
            imageName, "drawable", packageName
        )
        if (resourceId != 0) {
            imageView.setImageResource(resourceId)
        } else {
            imageView.setImageResource(R.drawable.category)
        }

        recViewSubCategories = findViewById(R.id.rec_view_subcategories)
        recViewSubCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recViewSubCategories.setHasFixedSize(true)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recViewSubCategories)

        subCategoriesAdapter = SubCategoriesAdapter(arrayListOf())
        recViewSubCategories.adapter = subCategoriesAdapter

        subCategoriesViewModel.subCategoriesData.observe(this) { subCategoriesList ->
            subCategoriesAdapter.setData(subCategoriesList)
            recViewSubCategories.visibility = View.VISIBLE
        }

        subCategoriesViewModel.fetchSubCategoriesData(categoryName)
    }
}