package com.example.shareme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class CreateUserActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var createEmailTxt : TextView
    lateinit var createPasswordTxt : TextView
    lateinit var createUsernameTxt : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        auth = FirebaseAuth.getInstance()
        createEmailTxt = findViewById(R.id.createEmailTxt)
        createPasswordTxt = findViewById(R.id.createPasswordTxt)
        createUsernameTxt = findViewById(R.id.createUsernameTxt)

    }

    fun createCreateClicked(view: View) {
        val email = createEmailTxt.text.toString()
        val password = createPasswordTxt.text.toString()
        val username = createUsernameTxt.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result->
                val changeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()
                result.user?.updateProfile(changeRequest)
                    ?.addOnFailureListener { exception ->
                        Log.e("Exception", "Could not update display name: ${exception.localizedMessage}")
                    }

                val data = HashMap<String, Any>()
                data.put(USERNAME, username)
                data.put(DATE_CREATED, FieldValue.serverTimestamp())
                Log.e("here", "logged")
                result.user?.let { FirebaseFirestore.getInstance().collection(USERS_REF).document(it.uid)

                    .set(data)
                    .addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener {exception->
                        Log.e("Exception", "Could not add user document: ${exception.localizedMessage}")

                    }}
            }
            .addOnFailureListener { exception->
                Log.e("Exception", "Could not create user: ${exception.localizedMessage}")

            }

    }

    fun createCancelClicked(view:View) {
        finish()
    }
}