package com.example.jaanadoptapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jaanadoptapp.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import layout.AnimalModel


class AnimalAdapter(private val animalList: List<AnimalModel>, private val idList: List<String>, private val context: Context) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    override fun onBindViewHolder(animalViewHolder: AnimalViewHolder, index: Int) {
        animalViewHolder.nameTextView.text = animalList[index].name
        animalViewHolder.breedTextView.text = animalList[index].breed
        animalViewHolder.speciesTextView.text = animalList[index].species
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
        val speciesTextView: TextView = view.findViewById(R.id.animal_species)

    }

    fun getId(position: Int): String {
        return idList[position]
    }


}