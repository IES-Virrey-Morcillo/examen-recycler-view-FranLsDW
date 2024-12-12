package com.di.recyclerviewmontes.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.di.recyclerviewmontes.Monte
import com.di.recyclerviewmontes.databinding.ItemMonteBinding

class MonteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemMonteBinding.bind(view)

    fun render(monte: Monte,
               onClickListener: (Monte)->Unit,
               onClickDelete: (Int)->Unit
    ){
        binding.tvNombre.text = monte.nombre
        binding.tvAltura.text  = "Altura: "+ monte.altura
        binding.tvContinente.text ="Contiente: "+ monte.continente
        Glide.with(binding.ivImagen.context)
            .load(monte.foto)
            .into(binding.ivImagen)

        binding.ivImagen.scaleType = ImageView.ScaleType.CENTER_CROP
        
        //Eventos
        binding.tvVerMas.setOnClickListener{onClickListener(monte)}
        binding.btnBorrar.setOnClickListener{onClickDelete(adapterPosition)}
    }
}