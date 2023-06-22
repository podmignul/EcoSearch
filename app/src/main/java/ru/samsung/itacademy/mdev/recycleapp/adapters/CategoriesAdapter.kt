package ru.samsung.itacademy.mdev.recycleapp.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import ru.samsung.itacademy.mdev.recycleapp.R
import ru.samsung.itacademy.mdev.recycleapp.models.CategoriesModel

class CategoriesAdapter(var categoriesList: List<CategoriesModel>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCategory = categoriesList[position]
        holder.categoryText.text = currentCategory.categoryName

        val imageName = currentCategory.categoryImagePath
        val resourceId = holder.itemView.context.resources.getIdentifier(
            imageName, "drawable", holder.itemView.context.packageName
        )
        if (resourceId != 0) {
            holder.categoryImage.setImageResource(resourceId)
        } else {
            holder.categoryImage.setImageResource(R.drawable.category)
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    fun updateData(newList: List<CategoriesModel>) {
        categoriesList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val categoryText: TextView = itemView.findViewById(R.id.text_category)
        val categoryImage: ImageView = itemView.findViewById(R.id.img_category)
        val categoryCardView: CardView = itemView.findViewById(R.id.card_view_category)

        init {
            categoryCardView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}