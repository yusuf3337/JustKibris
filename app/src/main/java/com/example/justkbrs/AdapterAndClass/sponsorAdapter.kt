package com.example.justkbrs.AdapterAndClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justkbrs.DataClassAndSingelton.Sponsor
import com.example.justkbrs.R
import com.squareup.picasso.Picasso

class sponsorAdapter(private val sponsorList: List<Sponsor>) : RecyclerView.Adapter<sponsorAdapter.SponsorViewHolder>() {

    class SponsorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val barIsimText: TextView = itemView.findViewById(R.id.barIsimText)
        val sanatciIsim: TextView = itemView.findViewById(R.id.sanatciIsim)
        val etkinlikTarihi: TextView = itemView.findViewById(R.id.etkinlikTarihi)
        val barFiyat: TextView = itemView.findViewById(R.id.barFiyat)
        val imageView: ImageView = itemView.findViewById(R.id.barImageText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bar_cell, parent, false)
        return SponsorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SponsorViewHolder, position: Int) {
        val currentItem = sponsorList[position]

        holder.barIsimText.text = currentItem.brandName
        holder.sanatciIsim.text = currentItem.brandType
        holder.etkinlikTarihi.text = currentItem.brandDate
        holder.barFiyat.text = currentItem.priceReceived
        Picasso.get().load(currentItem.photoURLArray).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return sponsorList.size
    }
}
