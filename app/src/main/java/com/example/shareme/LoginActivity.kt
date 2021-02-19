package com.example.shareme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var loginEmailTxt : TextView
    lateinit var loginPasswordTxt : TextView
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginEmailTxt = findViewById(R.id.loginEmailTxt)
        loginPasswordTxt = findViewById(R.id.loginPaswordTxt)
        auth = FirebaseAuth.getInstance()
    }

    fun loginLoginClicked(view:View) {
        //perform login
        val email = loginEmailTxt.text.toString()
        val password = loginPasswordTxt.text.toString()

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    finish()
                }
                .addOnFailureListener { exception->
                    Log.e("LoginActivity", "Could not sign in user ${exception.localizedMessage}")
                }

    }

    fun loginCreateClicked(view:View) {
        val createIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createIntent)
    }
}