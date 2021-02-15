package com.example.shareme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    lateinit var fab: View
    lateinit var mainSeriousBtn : ToggleButton
    lateinit var mainCrazyBtn : ToggleButton
    lateinit var mainFunnyBtn : ToggleButton
    lateinit var mainPopularBtn : ToggleButton
    lateinit var thoughtsAdapter : ThoughtsAdapter
    lateinit var thoughtListView : RecyclerView
    val thoughts = arrayListOf<Thought>()
    val thoughtsCollectionRef = FirebaseFirestore.getInstance().collection(THOUGHTS_REF)

    var selectedCategory = FUNNY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainSeriousBtn = findViewById(R.id.mainSeriousBtn)
        mainCrazyBtn = findViewById(R.id.mainCrazyBtn)
        mainFunnyBtn = findViewById(R.id.mainFunnyBtn)
        mainPopularBtn = findViewById(R.id.mainPopularBtn)
        thoughtListView = findViewById(R.id.thoughtListView)

        fab = findViewById(R.id.fab)

        fab.setOnClickListener {
            val addThoughtIntent = Intent(this, AddThoughtActivity::class.java)
            startActivity(addThoughtIntent)
        }

        thoughtsAdapter = ThoughtsAdapter(thoughts)
        thoughtListView. adapter = thoughtsAdapter
        val layoutManager = LinearLayoutManager(this)
        thoughtListView.layoutManager = layoutManager

        thoughtsCollectionRef.get()
                .addOnSuccessListener { snapshot ->
                    for (document in snapshot.documents) {
                        val data = document.data
                        val name = data?.get(USERNAME) as String
                        val date = data[TIMESTAMP] as Timestamp
                        val timestamp = date.toDate()
                        val thoughtTxt = data[THOUGHT_TXT] as String
                        val numLikes = data[NUM_LIKES] as Long
                        val numComments = data[NUM_COMMENTS] as Long
                        val documentId = document.id

                        val newThought = Thought(name, timestamp, thoughtTxt,
                                numLikes.toInt(), numComments.toInt(), documentId )

                        thoughts.add(newThought)
                    }

                    // signals the thoughtsadapter to refresh its views
                    thoughtsAdapter.notifyDataSetChanged()

                } . addOnFailureListener { exception ->
                    Log.e("Exception", "Could not add post: $exception")
                }


    }

    fun mainFunnyClicked(view: View) {
        if(selectedCategory == FUNNY) {
            mainFunnyBtn.isChecked = true
            return
        }
        mainSeriousBtn.isChecked = false
        mainCrazyBtn.isChecked = false
        mainPopularBtn.isChecked = false
        selectedCategory = FUNNY
    }

    fun mainSeriousClicked(view: View) {
        if(selectedCategory == SERIOUS) {
            mainSeriousBtn.isChecked = true
            return
        }
        mainFunnyBtn.isChecked = false
        mainCrazyBtn.isChecked = false
        mainPopularBtn.isChecked = false
        selectedCategory = SERIOUS
    }

    fun mainCrazyClicked(view:View) {
        if(selectedCategory == CRAZY) {
            mainCrazyBtn.isChecked = true
            return
        }
        mainFunnyBtn.isChecked = false
        mainSeriousBtn.isChecked = false
        mainPopularBtn.isChecked = false
        selectedCategory = CRAZY
    }

    fun mainPopularClicked(view:View) {
        if(selectedCategory == POPULAR) {
            mainPopularBtn.isChecked = true
            return
        }
        mainFunnyBtn.isChecked = false
        mainSeriousBtn.isChecked = false
        mainCrazyBtn.isChecked = false
        selectedCategory = POPULAR
    }
}