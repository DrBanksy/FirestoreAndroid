package com.example.shareme.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shareme.Model.Thought
import com.example.shareme.NUM_LIKES
import com.example.shareme.R
import com.example.shareme.THOUGHTS_REF
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ThoughtsAdapter(val thoughts : ArrayList<Thought>, val itemClick: (Thought) -> Unit) : RecyclerView.Adapter<ThoughtsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View, val itemClick: (Thought) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val username = itemView.findViewById<TextView>(R.id.listViewUsername)
        val timestamp = itemView.findViewById<TextView>(R.id.listViewTimestamp)
        val numLikes = itemView.findViewById<TextView>(R.id.listViewNumLikesLbl)
        val thoughtTxt = itemView.findViewById<TextView>(R.id.listViewThoughtTxt)
        val likesImage = itemView.findViewById<ImageView>(R.id.listViewLikesImg)

        fun bindThought(thought: Thought) {
            username?.text = thought.username
            thoughtTxt?.text = thought.thoughtTxt
            numLikes?.text = thought.numLikes.toString()

            val dateFormatter = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
            val dateString = dateFormatter.format(thought.timestamp)
            timestamp?.text = thought.timestamp.toString()
            itemView.setOnClickListener { itemClick(thought) }
            likesImage?.setOnClickListener {
                FirebaseFirestore.getInstance().collection(THOUGHTS_REF).document(thought.documentId)
                        .update(NUM_LIKES, thought.numLikes + 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.thought_list_view, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindThought(thoughts[position])
    }

    override fun getItemCount(): Int {
        return thoughts.count()
    }
}