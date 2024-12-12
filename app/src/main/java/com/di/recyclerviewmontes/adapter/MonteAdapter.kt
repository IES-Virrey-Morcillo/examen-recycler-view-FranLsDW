package com.di.recyclerviewmontes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.di.recyclerviewmontes.Monte
import com.di.recyclerviewmontes.R

class MonteAdapter (
    private val monteList: List<Monte>,
    private val onClickListener: (Monte) -> Unit,
    private val onDeleteListener: (Int) -> Unit
) : RecyclerView.Adapter<MonteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MonteViewHolder(layoutInflater.inflate(R.layout.item_monte, parent, false))
    }

    override fun getItemCount(): Int = monteList.size

    override fun onBindViewHolder(holder: MonteViewHolder, position: Int) {
        val item = monteList[position]
        //Para pasarle al ViewHolder lo que hacer
        holder.render(item, onClickListener, onDeleteListener)
    }
}