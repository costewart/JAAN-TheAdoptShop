package com.example.jaanadoptapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jaanadoptapp.R
import com.example.jaanadoptapp.modules.GlideApp
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import layout.AnimalModel

class AnimAdapter : FirestoreRecyclerAdapter<AnimalModel, AnimAdapter.AnimalHolder> {

    constructor(options: FirestoreRecyclerOptions<AnimalModel>) : super(options)

    private val storageReference = FirebaseStorage.getInstance()

    inner class AnimalHolder: RecyclerView.ViewHolder {
        var textViewTitle: TextView = itemView.findViewById(R.id.text_view_name)
        var textViewDescription: TextView = itemView.findViewById(R.id.text_view_breed)
        var textViewPriority: TextView = itemView.findViewById(R.id.text_view_sex)
        val speciesImageView: ImageView = itemView.findViewById(R.id.profilePicture)

        constructor(
            itemView: View
        ) : super(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.animal_item, parent, false)
        return AnimalHolder(v)
    }

    fun deleteItem(position: Int) {
        snapshots.getSnapshot(position).reference.delete()

    }

    override fun onBindViewHolder(holder: AnimalHolder, position: Int, model: AnimalModel) {
        holder.textViewTitle.setText(model.name)
        holder.textViewDescription.setText(model.breed)
        holder.textViewPriority.setText(model.sex)

        val pathString = model.uri
        bindImage(holder.speciesImageView, pathString)
    }

    private fun bindImage(imageView: ImageView, path: String) {
        val imageRef = storageReference.reference.child("images/$path")

        val gsReference = storageReference.getReferenceFromUrl(imageRef.toString())

        Log.d("char", imageRef.toString())

        GlideApp.with(imageView.context)
            .load(gsReference)
            .into(imageView)
    }
}