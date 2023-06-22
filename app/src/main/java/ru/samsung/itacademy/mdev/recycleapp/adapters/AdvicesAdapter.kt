package ru.samsung.itacademy.mdev.recycleapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.samsung.itacademy.mdev.recycleapp.R
import ru.samsung.itacademy.mdev.recycleapp.models.AdvicesModel

class AdvicesAdapter : RecyclerView.Adapter<AdvicesAdapter.ViewHolder>() {

    private val advicesList = mutableListOf<AdvicesModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_advice, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAdvice = advicesList[position]
        holder.adviceName.text = currentAdvice.adviceName
        holder.adviceDescription.text = currentAdvice.adviceDescription
    }

    override fun getItemCount(): Int {
        return advicesList.size
    }

    fun setData(data: List<AdvicesModel>) {
        advicesList.clear()
        advicesList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adviceName: TextView = itemView.findViewById(R.id.title_advice)
        val adviceDescription: TextView = itemView.findViewById(R.id.text_advice)
    }
}