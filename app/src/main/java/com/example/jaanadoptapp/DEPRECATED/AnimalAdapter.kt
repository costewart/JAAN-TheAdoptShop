package com.example.jaanadoptapp.DEPRECATED

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jaanadoptapp.R
import com.example.jaanadoptapp.modules.GlideApp
import com.google.firebase.storage.FirebaseStorage
import layout.AnimalModel


class AnimalAdapter(private val animalList: List<AnimalModel>, private val idList: List<String>, private val context: Context) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    private val storageReference = FirebaseStorage.getInstance()

    override fun onBindViewHolder(animalViewHolder: AnimalViewHolder, index: Int) {
        animalViewHolder.nameTextView.text = animalList[index].name
        animalViewHolder.breedTextView.text = animalList[index].breed
        val pathString = animalList[index].uri

        bindImage(animalViewHolder.speciesImageView, pathString)
    }

    private fun bindImage(imageView: ImageView, path: String) {
        val imageRef = storageReference.reference.child("images/$path")

        val gsReference = storageReference.getReferenceFromUrl(imageRef.toString())

        Log.d("char", imageRef.toString())

        GlideApp.with(context)
            .load(gsReference)
            .into(imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return AnimalViewHolder(LayoutInflater.from(context).inflate(R.layout.animal_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    inner class AnimalViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameTextView: TextView = view.findViewById(R.id.animal_name)
        val breedTextView: TextView = view.findViewById(R.id.animal_breed)
        val speciesImageView: ImageView = view.findViewById(R.id.profilePicture)

    }

    fun getId(position: Int): String {
        return idList[position]
    }



}