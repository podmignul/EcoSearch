package ru.samsung.itacademy.mdev.recycleapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.samsung.itacademy.mdev.recycleapp.R
import ru.samsung.itacademy.mdev.recycleapp.models.SubCategoriesModel

class SubCategoriesAdapter(private var subCategoriesList: List<SubCategoriesModel>) :
    RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subcategory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSubcategory = subCategoriesList[position]

        holder.bind(currentSubcategory)
    }

    override fun getItemCount(): Int {
        return subCategoriesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SubCategoriesModel>) {
        subCategoriesList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryInfoNumber: TextView = itemView.findViewById(R.id.object_number)
        private val categoryInfoLabel: TextView = itemView.findViewById(R.id.object_label)
        private val categoryInfoName: TextView = itemView.findViewById(R.id.object_name)
        private val categoryInfoItems: TextView = itemView.findViewById(R.id.object_items)

        fun bind(subcategory: SubCategoriesModel) {
            categoryInfoNumber.text = subcategory.categoryInfoNumber
            categoryInfoLabel.text = subcategory.categoryInfoLabel
            categoryInfoName.text = subcategory.categoryInfoName
            categoryInfoItems.text = subcategory.categoryInfoItems
        }
    }
}