package com.example.justkbrs.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.justkbrs.R
import com.example.justkbrs.Register.RegisterPage
import com.example.justkbrs.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

//MACBOOK COMMIT DENEME

private lateinit var binding: ActivityMainBinding
private lateinit var auth: FirebaseAuth
private lateinit var firebaseDB: FirebaseFirestore
private lateinit var firebaseStorage: FirebaseStorage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            loginPage()
        }
    }

    fun loginPage (){
        val intent = Intent (this, LoginPage::class.java)
        startActivity(intent )
        finish()
    }

    fun registerPage(view: View){
        val intent = Intent(this, RegisterPage::class.java)
        startActivity(intent)
    }

    fun onLoginClick(view: View) {
        val email = binding.emailTextF.text.toString()
        val password = binding.passwordTextF.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Firebase ile e-posta ve şifre ile giriş yapma
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Giriş başarılı
                        loginPage()
                    } else {
                        // Giriş başarısız, kullanıcıya uyarı göster
                        showError("Giriş başarısız. E-posta veya şifrenizi kontrol ediniz.")
                    }
                }
        } else {
            // E-posta veya şifre boşsa kullanıcıya uyarı göster
            showError("E-posta ve şifre boş olmamalıdır.")
        }
    }

    private fun showError(message: String) {
        // Hata mesajını kullanıcıya göstermek için bir AlertDialog kullanabilirsiniz.
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Hata")
        builder.setMessage(message)
        builder.setPositiveButton("Tamam") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}