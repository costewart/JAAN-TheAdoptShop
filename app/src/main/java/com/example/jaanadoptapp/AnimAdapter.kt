package com.example.jaanadoptapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import layout.AnimalModel

class AnimAdapter : FirestoreRecyclerAdapter<AnimalModel, AnimAdapter.AnimalHolder> {

    constructor(options: FirestoreRecyclerOptions<AnimalModel>) : super(options)


    inner class AnimalHolder: RecyclerView.ViewHolder {
        var textViewTitle: TextView = itemView.findViewById(R.id.text_view_name)
        var textViewDescription: TextView = itemView.findViewById(R.id.text_view_breed)
        var textViewPriority: TextView = itemView.findViewById(R.id.text_view_sex)

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
    }
}