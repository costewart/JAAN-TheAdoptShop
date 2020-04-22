package com.example.jaanadoptapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jaanadoptapp.adapters.AnimalAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.animals.*
import layout.AnimalModel


class Animals : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animals)

        add_dog.setOnClickListener {
            startActivity(Intent(this, AddAnimal::class.java))
        }

        // grab species name based on button pressed, filter data by species
        val speciesName = intent.getStringExtra("species")

        fun readData() {
            db.collection("Animals").whereEqualTo("species", speciesName)
                .get()
                .addOnSuccessListener { result ->
                    val list = ArrayList<AnimalModel>()
                    for (document in result) {
                        d("exist", "${document.id} => ${document.data}")

                        val name = document.data["name"].toString()
                        val age = document.data["age"].toString().toInt()
                        val breed = document.data["breed"].toString()
                        val sex = document.data["sex"].toString()
                        val species = document.data["species"].toString()
                        val dogfriendly = document.data["dogfriendly"].toString().toBoolean()
                        val catfriendly = document.data["catfriendly"].toString().toBoolean()
                        val vaccinated = document.data["vaccinated"].toString().toBoolean()
                        val sterilized = document.data["sterilized"].toString().toBoolean()

                        val animal = AnimalModel(
                            name,
                            age,
                            breed,
                            sex,
                            species,
                            dogfriendly,
                            catfriendly,
                            vaccinated,
                            sterilized
                        )

                        list.add(animal)
                    }

                    setUpAnimalRecyclerView(list)
                }
                .addOnFailureListener { exception ->
                    Log.w("noexist", "Error getting documents.", exception)
                }
        }
        readData()
    }


    private fun setUpAnimalRecyclerView(emails: List<AnimalModel>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val animalRecyclerView = findViewById<RecyclerView>(R.id.email_recycler_view)
        val recyclerAdapter = AnimalAdapter(emails, this)
        animalRecyclerView.layoutManager = layoutManager
        animalRecyclerView.adapter = recyclerAdapter
    }
}