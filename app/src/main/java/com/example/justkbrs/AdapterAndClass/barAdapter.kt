package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justkbrs.DataClassAndSingelton.Etkinlik
import com.example.justkbrs.R
import com.squareup.picasso.Picasso

class barAdapter(private val barList: List<Etkinlik>, private val onItemClicked: (Etkinlik) -> Unit) : RecyclerView.Adapter<barAdapter.BarViewHolder>() {

    class BarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val barIsimText: TextView = itemView.findViewById(R.id.barIsimText)
        val sanatciIsim: TextView = itemView.findViewById(R.id.sanatciIsim)
        val etkinlikTarihi: TextView = itemView.findViewById(R.id.etkinlikTarihi)
        val barFiyat: TextView = itemView.findViewById(R.id.barFiyat)
        val im : ImageView = itemView.findViewById(R.id.barImageText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bar_cell, parent, false)
        return BarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BarViewHolder, position: Int) {
        val currentItem = barList[position]
        holder.itemView.setOnClickListener {
            onItemClicked(currentItem)
        }

        //holder.mekanIsimText.text = "Başlık : ${currentItem.ClubTitle}"
        //  holder.etkinlikTarih.text = "Fiyat : ${currentItem.ilanFiyat}"
        //  holder.etkinlikSaat.text = "Minimum Kiralama : ${currentItem.ilanKiraMinSuresi}"
        //  holder.ticketFiyat.text = "Oda : ${currentItem.evOdaSayisi}"
        //  Picasso.get().load(currentItem.photoURLArray[0]).into(holder.im)


        holder.barIsimText.text = currentItem.mekanName
        holder.barFiyat.text = currentItem.activityPrice
        holder.sanatciIsim.text = currentItem.activityName
        holder.etkinlikTarihi.text = currentItem.activityDate

        Picasso.get().load(currentItem.photoURLArray).into(holder.im)
        // Diğer özellikleri de buraya ekleyebilirsiniz.
    }

    override fun getItemCount(): Int {
        return barList.size
    }
}