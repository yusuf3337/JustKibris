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

class nightClubAdapter(private val clubList: List<Etkinlik>) : RecyclerView.Adapter<nightClubAdapter.ClubViewHolder>() {

    class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mekanIsimText: TextView = itemView.findViewById(R.id.mekanIsimText)
        val etkinlikTarih: TextView = itemView.findViewById(R.id.etkinlikTarih)
        val etkinlikSaat: TextView = itemView.findViewById(R.id.etkinlikSaat)
        val ticketFiyat: TextView = itemView.findViewById(R.id.ticketFiyat)
        val im : ImageView = itemView.findViewById(R.id.ilanImageText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.night_club_cell, parent, false)
        return ClubViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val currentItem = clubList[position]

     //holder.mekanIsimText.text = "Başlık : ${currentItem.ClubTitle}"
      //  holder.etkinlikTarih.text = "Fiyat : ${currentItem.ilanFiyat}"
      //  holder.etkinlikSaat.text = "Minimum Kiralama : ${currentItem.ilanKiraMinSuresi}"
      //  holder.ticketFiyat.text = "Oda : ${currentItem.evOdaSayisi}"
      //  Picasso.get().load(currentItem.photoURLArray[0]).into(holder.im)

        holder.mekanIsimText.text = currentItem.mekanName
        holder.ticketFiyat.text = currentItem.activityPrice
        holder.etkinlikSaat.text = currentItem.activityDescription
        holder.etkinlikTarih.text = currentItem.activityDate
        Picasso.get().load(currentItem.photoURLArray).into(holder.im)



        // Diğer özellikleri de buraya ekleyebilirsiniz.
    }

    override fun getItemCount(): Int {
        return clubList.size
    }
}