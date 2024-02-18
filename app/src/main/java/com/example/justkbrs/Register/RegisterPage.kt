package com.example.justkbrs.Register

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.justkbrs.R
import com.example.justkbrs.databinding.ActivityRegisterPageBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import java.util.jar.Manifest
import kotlin.random.Random


class RegisterPage : AppCompatActivity() {



    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedImageUri: Uri? = null
    private var userProfileImageUrl: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        FirebaseApp.initializeApp(this)

        registerLauncher()
        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()


    }

    private fun uploadProfileImage(imageUri: Uri) {
        val storageRef = firebaseStorage.reference.child("profile_photos/${binding.usernameTextF.text.toString()}.jpeg")
        storageRef.putFile(imageUri).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                Log.d("UploadImage", "Image uploaded successfully: $downloadUrl")
                updateUserProfile(downloadUrl)
                userProfileImageUrl = downloadUrl // Update userProfileImageUrl here
            }
        }.addOnFailureListener {
            // Error handling
            Log.e("UploadImage", "Image upload failed", it)
        }
    }


    private fun updateUserProfile(photoUrl: String) {
        val user = auth.currentUser
        val profileUpdates = userProfileChangeRequest {
            this.photoUri = Uri.parse(photoUrl)
        }
        user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("ProfileUpdate", "User profile updated with URL: $photoUrl")
                userProfileImageUrl = photoUrl // Bu satırı ekleyin
                // Profil güncelleme başarılı, ek işlemler yapılabilir
            } else {
                Log.e("ProfileUpdate", "Profile update failed", task.exception)
            }
        }
    }


    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data != null && data.data != null) {
                        selectedImageUri = data.data
                        // Seçilen resmi ImageView'da göster
                        binding.imageViewProfile.setImageURI(selectedImageUri)
                        // Seçilen resmi yükleme fonksiyonuna gönder
                        selectedImageUri?.let { uploadProfileImage(it) }
                    }
                }
            }
        )

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(this, "Galeri erişim izni gereklidir", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun openGallery() {
        val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityResultLauncher.launch(intentToGallery)
    }

    fun selectImage(view: View) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // İzin henüz verilmemişse, izin iste
            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            openGallery()
        } else {
            // İzin zaten verilmişse, galeriyi aç
            openGallery()
        }
    }



    //butTON EKLE
    fun registerClick(view: View) {
        val name = binding.nameTextF.text.toString()
        val surname = binding.surnameTextF.text.toString()
        val age = binding.ageTextF.text.toString()
        val username = binding.usernameTextF.text.toString()
        val email = binding.emailTextF.text.toString()
        val password = binding.passwordTextF.text.toString()
        val phone = binding.phoneTextF.text.toString()

        if (name.isEmpty() || surname.isEmpty() || age.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            showAlertDialog("Hata!", "Lütfen Alanları Doldurunuz", "Tekrar Dene")
        } else if (selectedImageUri == null) {
            showAlertDialog("Hata!", "Profil fotoğrafı seçimi zorunludur.", "Tamam")
        } else {
            createUser() // Kullanıcıyı oluştur
        }
    }


    fun firebaseRegister() {

        val uuid = UUID.randomUUID().toString()
        val userIDNumberGenerate = String.format("%05d", Random.nextInt(10000))
        val customDocumentName = "${binding.usernameTextF.text}$uuid"

        // FCM Token al
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("RegisterPage", "FCM token alınamadı", task.exception)
                return@addOnCompleteListener
            }

            val fcmToken = task.result ?: "FCM Token alınamadı"
            // Firestore'a kaydedilecek kullanıcı bilgileri
            val userMap = hashMapOf(
                "userID" to (userIDNumberGenerate + binding.usernameTextF.text.toString()),
                "documentID" to customDocumentName,
                "FCM_TOKEN" to fcmToken,
                "email" to binding.emailTextF.text.toString(),
                "name" to binding.nameTextF.text.toString(),
                "password" to binding.passwordTextF.text.toString(),
                "surname" to binding.surnameTextF.text.toString(),
                "age" to binding.ageTextF.text.toString(),
                "username" to binding.usernameTextF.text.toString(),
                // Kullanıcı şifresinin açık bir şekilde saklanması önerilmez
                "phoneNumber" to binding.phoneTextF.text.toString(),
                "userWallet" to 20,
                "userWalletID" to userIDNumberGenerate,
                "Kayit_Cihazi" to "Android",
                "userActive" to 0,
                "userWalletQRCodeData" to "userID:$customDocumentName,walletID:$userIDNumberGenerate",
                "KayitTarihi" to FieldValue.serverTimestamp(),
                "photoURL" to userProfileImageUrl
            )

            // Firestore'a kullanıcı bilgilerini kaydet
            firebaseDB.collection("userInformation").document(customDocumentName)
                .set(userMap)
                .addOnSuccessListener {
                    Log.d("RegisterPage", "Firestore'a kullanıcı başarıyla kaydedildi")
                    showAlertDialog("Başarılı!", "Kullanıcı başarıyla kaydedildi.", "Tamam")
                }
                .addOnFailureListener { e ->
                    Log.e("RegisterPage", "Firestore kaydı başarısız", e)
                    showAlertDialog("Hata!", "Firestore'a kayıt başarısız: ${e.localizedMessage}", "Tekrar Dene")
                }
        }
    }



    private fun showAlertDialog(title: String, message: String,buttonTitle:String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonTitle) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun createUser() {
        val email = binding.emailTextF.text.toString()
        val password = binding.passwordTextF.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Kullanıcı başarıyla oluşturuldu, profil fotoğrafını yükle
                selectedImageUri?.let { uri ->
                    uploadProfileImage(uri)
                }
                firebaseRegister() // Diğer kayıt işlemlerini yap
            }
            .addOnFailureListener { error ->
                showAlertDialog("Uyarı!", "Bir sorun oluştu: ${error.localizedMessage}", "Tekrar Dene")
            }
    }

}