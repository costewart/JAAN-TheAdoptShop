package com.example.jaanadoptapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import layout.AnimalModel

class AnimalsTwo: AppCompatActivity() {

    var db = FirebaseFirestore.getInstance()
    var animalRef = db.collection("Animals")
    lateinit var adapter: AnimAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animals)

        setUpRecyclerView()

        val mFab = findViewById<FloatingActionButton>(R.id.fab)
        mFab.setOnClickListener {
            startActivity(Intent(this, AddAnimal::class.java))
        }
    }

    private fun setUpRecyclerView() {
        val query = animalRef.orderBy("breed", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<AnimalModel>().setQuery(query, AnimalModel::class.java).build()

        adapter = AnimAdapter(options)

        val recyclerView = findViewById<RecyclerView>(R.id.animal_recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    adapter.deleteItem(viewHolder.adapterPosition)
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}