package com.example.shareme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class AddThoughtActivity : AppCompatActivity() {

    var selectedCategory = FUNNY
    lateinit var addSeriousBtn : ToggleButton
    lateinit var addCrazyBtn : ToggleButton
    lateinit var addFunnyBtn : ToggleButton
    private lateinit var addThoughtTxt : TextView
    private lateinit var addUsernameTxt : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_thought)
        addSeriousBtn = findViewById(R.id.addSeriousBtn)
        addCrazyBtn = findViewById(R.id.addCrazyBtn)
        addFunnyBtn = findViewById(R.id.addFunnyBtn)
        addThoughtTxt = findViewById(R.id.addThoughtTxt)
        addUsernameTxt = findViewById(R.id.addUsernameTxt)
    }

    fun addPostClicked(view:View) {
        //add post to firestore
        val data = HashMap<String, Any>()
        data.put("category", selectedCategory)
        data.put("numComments", 0)
        data.put("numLikes", 0 )
        data.put("thoughtTxt", addThoughtTxt.text.toString())
        data.put("timestamp", FieldValue.serverTimestamp())
        data.put("username", addUsernameTxt.text.toString())
        FirebaseFirestore.getInstance().collection(THOUGHTS_REF)
            .add(data)
            .addOnCompleteListener {
                finish()
            }
            .addOnFailureListener { exception ->
                Log.e("Exception", "Could not add post: $exception")

            }
    }

    fun addFunnyClicked(view: View) {
        if(selectedCategory == FUNNY) {
            addFunnyBtn.isChecked = true
            return
        }
        addSeriousBtn.isChecked = false
        addCrazyBtn.isChecked = false
        selectedCategory = FUNNY
    }

    fun addSeriousClicked(view: View) {
        if(selectedCategory == SERIOUS) {
            addSeriousBtn.isChecked = true
            return
        }
        addFunnyBtn.isChecked = false
        addCrazyBtn.isChecked = false
        selectedCategory = SERIOUS
    }

    fun addCrazyClicked(view:View) {
        if(selectedCategory == CRAZY) {
            addCrazyBtn.isChecked = true
            return
        }
        addFunnyBtn.isChecked = false
        addSeriousBtn.isChecked = false
        selectedCategory = CRAZY
    }
}