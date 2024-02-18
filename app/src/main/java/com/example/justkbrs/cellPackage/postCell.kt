package com.example.justkbrs.cellPackage

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.justkbrs.DataClassAndSingelton.Etkinlik
import com.example.justkbrs.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.squareup.picasso.Picasso

class postCell : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.ada_post_posts_cell)

        val etkinlikID = intent.getStringExtra("etkinlikID")
        val photoURL = intent.getStringExtra("photoURL")
        Log.e("gelenPhoto","${photoURL}")
        if (etkinlikID != null && photoURL != null){
            fetchEtkinlikDetails(etkinlikID, photoURL)
        }
    }

    private fun fetchEtkinlikDetails(etkinlikID: String, photoURL: String) {
        val database = FirebaseFirestore.getInstance()
        val etkinlikRef = database.collection("etkinlikler").document(etkinlikID)

        etkinlikRef.get().addOnSuccessListener { document ->
            if (document != null){
                val etkinlik = document.toObject(Etkinlik::class.java)
                updateUI(etkinlik, photoURL)
                Log.e("resim1", "Photo URL: ${etkinlik?.photoURLArray ?: "null"}")
                Log.e("mekan1", "Mekan Adı: ${etkinlik?.mekanName ?: "null"}")
            } else{
                Log.d("detayCell","Belge bulunamadi")
            }
        }.addOnSuccessListener { e ->
           Log.d("detayCell", "Hata: ")
        }
    }

    private fun updateUI(etkinlik: Etkinlik?, photoURL: String){
        etkinlik?.let {
            findViewById<TextView>(R.id.postMekanAdi).text = it.mekanName
            findViewById<TextView>(R.id.postAciklama).text = it.activityDescription
            findViewById<TextView>(R.id.postTarih).text = it.activityDate

            val imageView = findViewById<ImageView>(R.id.postMekanImage)
            Picasso.get()
                .load(photoURL)
                .into(imageView)
        }
    }
}