package com.example.justkbrs.AdapterAndClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justkbrs.R

class ProfileAdapter (private val profileSayfasi: List<profileClass>) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cuzdan: TextView = itemView.findViewById(R.id.cuzdanT)
        val rezervasyonlarim: TextView = itemView.findViewById(R.id.rezervasyonlarimT)
        val hesabim: TextView = itemView.findViewById(R.id.hesabimT)
        val ayarlar: TextView = itemView.findViewById(R.id.ayarlarT)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.profile_cell, parent, false)
        return ProfileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val currentItem = profileSayfasi[position]
        holder.cuzdan.text = currentItem.cuzdan
        holder.rezervasyonlarim.text = currentItem.rezervasyonlarim
        holder.hesabim.text = currentItem.hesabim
        holder.ayarlar.text = currentItem.ayarlar
    }

    override fun getItemCount() = profileSayfasi.size
}