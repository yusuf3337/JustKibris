package com.example.justkbrs.cellPackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.justkbrs.DataClassAndSingelton.Etkinlik
import com.example.justkbrs.R
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class detayCell : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detay_cell)

        val etkinlikID = intent.getStringExtra("etkinlikID")
        val photoURL = intent.getStringExtra("photoURL")
        Log.e("gelenPh", "${photoURL}")
        if (etkinlikID != null && photoURL != null) {
            fetchEtkinlikDetails(etkinlikID, photoURL)
        }
    }

    private fun fetchEtkinlikDetails(etkinlikID: String, photoURL: String) {
        // Firebase Firestore'dan etkinlikID'ye göre veri çekme işlemi
        val database = FirebaseFirestore.getInstance()
        val etkinlikRef = database.collection("etkinlikler").document(etkinlikID)

        etkinlikRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val etkinlik = document.toObject(Etkinlik::class.java)
                updateUI(etkinlik, photoURL)
                Log.e("resim1", "Photo URL: ${etkinlik?.photoURLArray ?: "null"}")
                Log.e("mekan1", "Mekan Adı: ${etkinlik?.mekanName ?: "null"}")
            } else {
                Log.d("detayCell", "Belge bulunamadı")
            }
        }.addOnFailureListener { e ->
            Log.d("detayCell", "Hata: ", e)
        }
    }

    private fun updateUI(etkinlik: Etkinlik?, photoURL: String) {
        etkinlik?.let {
            findViewById<TextView>(R.id.detayTextView).text = it.mekanName
            findViewById<TextView>(R.id.detaytextView2).text = it.activityName
            findViewById<TextView>(R.id.detaytextView5).text = it.activityDate
            findViewById<TextView>(R.id.detaytextView7).text = it.activityPrice
            findViewById<TextView>(R.id.detaytextView9).text = it.activityDescription
            findViewById<TextView>(R.id.detayEtkinlikID).text = it.activityBarCodeNo

            // ImageView ID'sini doğru şekilde ayarlayın
            val imageView = findViewById<ImageView>(R.id.detayImageView)
            Picasso.get()
                .load(photoURL)
                .into(imageView)
        }
    }

}

