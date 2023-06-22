package ru.samsung.itacademy.mdev.recycleapp.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.samsung.itacademy.mdev.recycleapp.R
import ru.samsung.itacademy.mdev.recycleapp.activities.SubCategoriesActivity
import ru.samsung.itacademy.mdev.recycleapp.adapters.CategoriesAdapter
import ru.samsung.itacademy.mdev.recycleapp.viewmodels.CategoriesViewModel

class CategoriesFragment : Fragment() {
    private lateinit var recViewCategories: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var imgInfo: ImageView
    private lateinit var titleCategories: TextView


    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        recViewCategories = view.findViewById(R.id.rec_view_categories)
        titleCategories = view.findViewById(R.id.title_categories)
        recViewCategories.layoutManager = LinearLayoutManager(requireContext())
        recViewCategories.setHasFixedSize(true)

        categoriesAdapter = CategoriesAdapter(ArrayList())
        recViewCategories.adapter = categoriesAdapter

        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        titleCategories.visibility = View.GONE
        recViewCategories.visibility = View.GONE

        categoriesAdapter.setOnItemClickListener(object : CategoriesAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedCategory = categoriesAdapter.categoriesList[position]
                val intent = Intent(requireContext(), SubCategoriesActivity::class.java).apply {
                    putExtra("categoryName", selectedCategory.categoryName)
                    putExtra("categoryImageName", selectedCategory.categoryImagePath)
                    putExtra("categoryDescription1", selectedCategory.categoryDescription1)
                    putExtra("categoryDescription2", selectedCategory.categoryDescription2)
                    putExtra("categoryDescription3", selectedCategory.categoryDescription3)
                }
                startActivity(intent)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgInfo = view.findViewById(R.id.img_info)
        imgInfo.setOnClickListener {
            showPopupMenu()
        }

        viewModel.categoriesData.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.updateData(categories)
            progressBar.visibility = View.GONE
            titleCategories.visibility = View.VISIBLE
            recViewCategories.visibility = View.VISIBLE
        }
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), imgInfo, Gravity.END, 0, R.style.CustomPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.about_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_about -> {
                    showAboutDialog()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }


    private fun showAboutDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_about, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton("Закрыть", null)
        val dialog = dialogBuilder.create()

        dialog.show()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
    }
}