package com.example.justkbrs

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.justkbrs.AdapterAndClass.sponsorAdapter
import com.example.justkbrs.DataClassAndSingelton.Etkinlik
import com.example.justkbrs.DataClassAndSingelton.Sponsor
import com.example.justkbrs.cellPackage.detayCell
import com.example.justkbrs.databinding.FragmentEtkinliklerBinding
import com.example.myapplication.adapter.barAdapter
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


val ilanlarKarma = mutableListOf<Etkinlik>()
val sendBar = mutableListOf<Etkinlik>()
val sendNight = mutableListOf<Etkinlik>()
val sendKonser = mutableListOf<Etkinlik>()
val sendCafe = mutableListOf<Etkinlik>()
val sendMeyhane = mutableListOf<Etkinlik>()
val sendParti = mutableListOf<Etkinlik>()
val sendSponsor = mutableListOf<Sponsor>()

class Etkinlikler : Fragment() {
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }


    private lateinit var binding: FragmentEtkinliklerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEtkinliklerBinding.inflate(inflater, container, false)


        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {
                ara(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                ara(query)
                return true
            }
        })

        return binding.root
    }

    fun ara(aramaKelimesi:String){
        Log.e("Etkinlik ara",aramaKelimesi)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       fetchAllActivities()
       fetchAllSponsors()

        // Bar RecyclerView
        val recyclerViewBar: RecyclerView = view.findViewById(R.id.barRecycler)
        recyclerViewBar.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewBar.adapter = barAdapter(sendBar) { etkinlik ->
            val intent = Intent(context, detayCell::class.java)
            intent.putExtra("etkinlikID", etkinlik.activityDocumentID)
            intent.putExtra("photoURL", etkinlik.photoURLArray)
            startActivity(intent)
        }

        // Night Club RecyclerView
        val recyclerViewNights: RecyclerView = view.findViewById(R.id.nightClubRecycler)
        recyclerViewNights.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewNights.adapter = barAdapter(sendNight) { etkinlik ->
            val intent = Intent(context, detayCell::class.java)
            intent.putExtra("etkinlikID", etkinlik.activityDocumentID)
            intent.putExtra("photoURL", etkinlik.photoURLArray)
            startActivity(intent)
        }

        // Konser RecyclerView
        val recyclerViewKonser: RecyclerView = view.findViewById(R.id.konserRecycler)
        recyclerViewKonser.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewKonser.adapter = barAdapter(sendKonser) { etkinlik ->
            val intent = Intent(context, detayCell::class.java)
            intent.putExtra("etkinlikID", etkinlik.activityDocumentID)
            intent.putExtra("photoURL", etkinlik.photoURLArray)

            startActivity(intent)
        }

        // Cafe RecyclerView
        val recyclerViewCafe: RecyclerView = view.findViewById(R.id.cafeRecycler)
        recyclerViewCafe.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCafe.adapter = barAdapter(sendCafe) { etkinlik ->
            val intent = Intent(context, detayCell::class.java)
            intent.putExtra("etkinlikID", etkinlik.activityDocumentID)
            intent.putExtra("photoURL", etkinlik.photoURLArray)

            startActivity(intent)
        }

        // Meyhane RecyclerView
        val recyclerViewMeyhane: RecyclerView = view.findViewById(R.id.meyhaneRecycler)
        recyclerViewMeyhane.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewMeyhane.adapter = barAdapter(sendMeyhane) { etkinlik ->
            val intent = Intent(context, detayCell::class.java)
            intent.putExtra("etkinlikID", etkinlik.activityDocumentID)
            intent.putExtra("photoURL", etkinlik.photoURLArray)

            startActivity(intent)
        }

        // Parti RecyclerView
        val recyclerViewParti: RecyclerView = view.findViewById(R.id.partiRecycler)
        recyclerViewParti.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewParti.adapter = barAdapter(sendParti) { etkinlik ->
            val intent = Intent(context, detayCell::class.java)
            intent.putExtra("etkinlikID", etkinlik.activityDocumentID)
            intent.putExtra("photoURL", etkinlik.photoURLArray)
            Log.e("photo1", "${etkinlik.photoURLArray}")
            startActivity(intent)
        }

        // Sponsor RecyclerView
        val recyclerViewSponsor: RecyclerView = view.findViewById(R.id.sponsorRecycler)
        recyclerViewSponsor.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewSponsor.adapter = sponsorAdapter(sendSponsor)

        // Item Decoration
        class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.right = space
                outRect.bottom = space
            }
        }

        val spaceInPixels = dpToPx(22)
        recyclerViewBar.addItemDecoration(SpacesItemDecoration(spaceInPixels))
        recyclerViewNights.addItemDecoration(SpacesItemDecoration(spaceInPixels))
        recyclerViewParti.addItemDecoration(SpacesItemDecoration(spaceInPixels))
        recyclerViewMeyhane.addItemDecoration(SpacesItemDecoration(spaceInPixels))
        recyclerViewCafe.addItemDecoration(SpacesItemDecoration(spaceInPixels))
        recyclerViewKonser.addItemDecoration(SpacesItemDecoration(spaceInPixels))
        recyclerViewSponsor.addItemDecoration(SpacesItemDecoration(spaceInPixels))
    }


    private fun fetchAllActivities() {
        val database = FirebaseFirestore.getInstance()
        val ilanlarCollection = database.collection("etkinlikler")

        // Verileri bir kez çekmek için get metodu kullanılıyor
        ilanlarCollection.get()
            .addOnSuccessListener { querySnapshot ->
                // Listeleri temizle
                sendBar.clear()
                sendNight.clear()
                sendKonser.clear()
                sendCafe.clear()
                sendMeyhane.clear()
                sendParti.clear()
                sendSponsor.clear()

                val uniqueBarEvents = mutableSetOf<String>()
                val uniqueNightClubEvents = mutableSetOf<String>()
                val uniqueKonserEvents = mutableSetOf<String>()
                val uniqueMeyhaneEvents = mutableSetOf<String>()
                val uniquePartiEvents = mutableSetOf<String>()
                val uniqueCafeEvents = mutableListOf<String>()

                for (document in querySnapshot.documents) {
                    val docData = document.data
                    if (docData != null) {
                        val ilanBilgileri = createActivityInfo(docData)
                        if (ilanBilgileri != null && ilanBilgileri.isActive == 1) {
                            when (ilanBilgileri.activityCategory) {
                                "Bar" -> if (uniqueBarEvents.add(ilanBilgileri.activityName)) {
                                    sendBar.add(ilanBilgileri)
                                }
                                "Night Club" -> if (uniqueNightClubEvents.add(ilanBilgileri.activityName)) {
                                    sendNight.add(ilanBilgileri)
                                    Log.e("resim", "${ilanBilgileri.photoURLArray}")
                                }
                                "Konser" -> if (uniqueKonserEvents.add(ilanBilgileri.activityName)) {
                                    sendKonser.add(ilanBilgileri)
                                }
                                "Cafe" -> if (uniqueCafeEvents.add(ilanBilgileri.activityName)) {
                                    sendCafe.add(ilanBilgileri)
                                }
                                "Party" -> if (uniquePartiEvents.add(ilanBilgileri.activityName)) {
                                    sendParti.add(ilanBilgileri)
                                }
                                "Meyhane" -> if (uniqueMeyhaneEvents.add(ilanBilgileri.activityName)) {
                                    sendMeyhane.add(ilanBilgileri)
                                }
                            }
                        }
                    }
                }

                // RecyclerView adapterlerini güncelle
                view?.findViewById<RecyclerView>(R.id.barRecycler)?.adapter?.notifyDataSetChanged()
                view?.findViewById<RecyclerView>(R.id.nightClubRecycler)?.adapter?.notifyDataSetChanged()
                view?.findViewById<RecyclerView>(R.id.konserRecycler)?.adapter?.notifyDataSetChanged()
                view?.findViewById<RecyclerView>(R.id.cafeRecycler)?.adapter?.notifyDataSetChanged()
                view?.findViewById<RecyclerView>(R.id.partiRecycler)?.adapter?.notifyDataSetChanged()
                view?.findViewById<RecyclerView>(R.id.meyhaneRecycler)?.adapter?.notifyDataSetChanged()
                view?.findViewById<RecyclerView>(R.id.sponsorRecycler)?.adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("Hata", "Veri çekme hatası: ${exception.localizedMessage}")
            }
    }


    private fun createActivityInfo(docData: Map<String, Any>?): Etkinlik? {
        return try {
            docData?.let {
                val mekanName = it["mekanName"] as? String ?: return null
                val activityName = it["activityName"] as? String ?: return null
                val activityDate = it["activityDate"] as? String ?: return null
                val activityPrice = it["activityPrice"] as? String ?: return null
                val activityDescription =
                    it["activityDescription"] as? String ?: return null
                val photoURLArray = it["photoURL"] as? String ?: return null
                val isActive = (it["isActive"] as? Long)?.toInt() ?: return null
                val etkinlikEklenisTarihi =
                    (it["etkinlikEklenisTarihi"] as? com.google.firebase.Timestamp)?.toDate()
                        ?: return null
                val activityCategory = it["activityCategory"] as? String ?: return null
                val activityDocumentID =
                    it["activityDocumentID"] as? String ?: return null
                val activityPhoneNumber =
                    it["activityPhoneNumber"] as? String ?: return null
                val activityBarCodeNo =
                    it["activityBarCodeNo"] as? String ?: return null

                // Etkinlik nesnesini oluşturup döndür
                Etkinlik(
                    mekanName = mekanName,
                    activityName = activityName,
                    activityDate = activityDate,
                    activityPrice = activityPrice,
                    activityDescription = activityDescription,
                    photoURLArray = photoURLArray, // Null kontrolü eklendi
                    isActive = isActive,
                    etkinlikEklenisTarihi = etkinlikEklenisTarihi,
                    activityCategory = activityCategory,
                    activityDocumentID = activityDocumentID,
                    activityPhoneNumber = activityPhoneNumber,
                    activityBarCodeNo = activityBarCodeNo
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    private fun fetchAllSponsors() {
        val database = FirebaseFirestore.getInstance()
        val ilanlarCollection = database.collection("sponsors")

        ilanlarCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Hata", "Hata oluştu: ${error.localizedMessage}")
            } else {
                if (snapshot != null) {
                    val uniqueSponsorEvents = mutableSetOf<String>() // Sponsorlar için unique set

                    for (document in snapshot.documents) {
                        val docData = document.data
                        if (docData != null) {
                            sendSponsor.clear()
                            val sponsorBilgileri = createSponsorInfo(docData)
                            if (sponsorBilgileri != null && sponsorBilgileri.isActive == 1) {
                                // Sponsor ismini unique set'e ekleyip kontrol ediyoruz
                                if (uniqueSponsorEvents.add(sponsorBilgileri.brandName)) {
                                    sendSponsor.add(sponsorBilgileri)
                                    Log.e("sponsorlarpo","${sponsorBilgileri.brandName}")
                                    view?.findViewById<RecyclerView>(R.id.sponsorRecycler)?.adapter?.notifyDataSetChanged()

                                }
                            }
                        }
                    }

                    sendSponsor.shuffle()
                    view?.findViewById<RecyclerView>(R.id.sponsorRecycler)?.adapter?.notifyDataSetChanged()
                } else {
                    Log.e("Hata", "Hiç doküman bulunamadı.")
                }
            }
        }
    }


    private fun createSponsorInfo(docData: Map<String, Any>): Sponsor? {
        try {
            return Sponsor(
                activityDocumentID = docData["activityDocumentID"] as String,
                brandName = docData["brandName"] as String,
                brandType = docData["brandType"] as String,
                brandDescription = docData["brandDescription"] as String,
                brandPhoneNumber = docData["brandPhoneNumber"] as String,
                photoURLArray = docData["photoURL"] as String,
                isActive = (docData["isActive"] as Long).toInt(),
                etkinlikEklenisTarihi = (docData["etkinlikEklenisTarihi"] as com.google.firebase.Timestamp).toDate(),
                brandDiscound = docData["brandDiscound"] as String,
                brandDate = docData["brandDate"] as String,
                priceReceived = docData["priceReceived"] as String
            )
        } catch (e: Exception) {
            Log.e("Hata", "Bir hata oluştu: ${e.localizedMessage}")
            return null
        }
    }

    private fun dpToPx(dp: Int): Int {
        return if (context != null) {
            (dp * requireContext().resources.displayMetrics.density).toInt()
        } else {
            // Varsayılan değer veya hata durumu için bir işlem yapın
            dp // veya 0 dönebilirsiniz.
        }
    }
}


