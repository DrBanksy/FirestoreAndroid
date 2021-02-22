package com.example.shareme.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.shareme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class CommentsActivity : AppCompatActivity() {

    lateinit var thoughtDocumentId : String
    lateinit var enterCommentTxt : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        thoughtDocumentId = intent.getStringExtra(DOCUMENT_KEY).toString()
        enterCommentTxt = findViewById(R.id.enterCommentTxt)

    }

    fun addCommentClicked(view : View) {
        val commentTxt = enterCommentTxt.text.toString()
        val thoughtRef = FirebaseFirestore.getInstance().collection(THOUGHTS_REF).document(thoughtDocumentId)

        FirebaseFirestore.getInstance().runTransaction { transaction->
            val thought = transaction.get(thoughtRef)
            val numComments = thought.getLong(NUM_COMMENTS)!! + 1
            transaction.update(thoughtRef, NUM_COMMENTS, numComments)

            //create empty docuement, with auto generated Id
            val newCommentRef = FirebaseFirestore.getInstance().collection(THOUGHTS_REF)
                .document(thoughtDocumentId).collection(COMMENTS_REF).document() // will give us an auto generated Id

            val data = HashMap<String, Any>()
            data.put(COMMENT_TXT, commentTxt)
            data.put(TIMESTAMP, FieldValue.serverTimestamp())
            data.put(USERNAME, FirebaseAuth.getInstance().currentUser?.displayName.toString())

            transaction.set(newCommentRef, data)
        }
            .addOnSuccessListener {
                enterCommentTxt.text = ""
            }
            .addOnFailureListener {
                Log.e("Exception", "Could not post comment {${it.localizedMessage}}")
            }

    }
}