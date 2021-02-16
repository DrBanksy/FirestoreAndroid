package com.example.shareme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginLoginClicked(view:View) {
        //perform login
    }

    fun loginCreateClicked(view:View) {
        val createIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createIntent)
    }
}