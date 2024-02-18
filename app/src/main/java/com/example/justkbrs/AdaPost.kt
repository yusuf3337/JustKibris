package com.example.justkbrs

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.firestore.DocumentReference
import com.google.zxing.integration.android.IntentIntegrator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdaPost.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdaPost : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ada_post, container, false)
        // Butona tıklama olayını burada tanımlayın, örneğin:
        view.findViewById<Button>(R.id.QRcamOpen).setOnClickListener {
            openQRCamera()
        }
        return view
    }

    private fun openQRCamera() {
        // Fragment'tan QR kamera açmak için
        IntentIntegrator.forSupportFragment(this).initiateScan()
    }

    // QR kod okuma sonucunu işlemek için
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // Kullanıcı kamerayı kapattı veya okuma iptal edildi
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                // QR kod okundu ve içeriği 'result.contents' değişkeninde
                Toast.makeText(context, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun QRcamOpen(view: View){

    }

    fun updateReservationAsUsed(docRef: DocumentReference) {
        docRef.update("used", true)
            .addOnSuccessListener {
                println("Document successfully updated")
                // Başarıyla güncellendiğine dair bir mesaj veya işlem yapabilirsiniz.
            }
            .addOnFailureListener { e ->
                println("Error updating document: $e")
            }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdaPost.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdaPost().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}