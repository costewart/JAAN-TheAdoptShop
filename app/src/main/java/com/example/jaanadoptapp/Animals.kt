package com.example.jaanadoptapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jaanadoptapp.adapters.AnimalAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.animals.*
import layout.AnimalModel


class Animals : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animals)

        val mFab = findViewById<FloatingActionButton>(R.id.fab)
        mFab.setOnClickListener {
            startActivity(Intent(this, AddAnimal::class.java))
        }

        // grab species name based on button pressed, filter data by species
        val speciesName = intent.getStringExtra("species")

        // read data from database, create AnimalModel with relevant info and add to list to display
        // in RecyclerView
        fun readData() {
            db.collection("Animals").whereEqualTo("species", speciesName)
                .addSnapshotListener { result, e ->

                    // if we failed
                    if (e != null) {
                        d("char:", "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    val list = ArrayList<AnimalModel>()
                    val idList = ArrayList<String>()
                    for (document in result!!) {
                        d("char:", "${document.id} => ${document.data}")

                        val name = document.data["name"].toString()
                        val age = document.data["age"].toString().toInt()
                        val breed = document.data["breed"].toString()
                        val sex = document.data["sex"].toString()
                        val species = document.data["species"].toString()
                        val dogfriendly = document.data["dogfriendly"].toString().toBoolean()
                        val catfriendly = document.data["catfriendly"].toString().toBoolean()
                        val vaccinated = document.data["vaccinated"].toString().toBoolean()
                        val sterilized = document.data["sterilized"].toString().toBoolean()
                        val uri = document.data["uri"].toString()

                        val animal = AnimalModel(
                            name,
                            age,
                            breed,
                            sex,
                            species,
                            dogfriendly,
                            catfriendly,
                            vaccinated,
                            sterilized,
                            uri
                        )

                        list.add(animal)
                        idList.add(document.id)
                    }
                    setUpAnimalRecyclerView(list, idList)
                }


        }
        readData()
    }


    private fun setUpAnimalRecyclerView(animals: List<AnimalModel>, ids: List<String>): AnimalAdapter {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val animalRecyclerView = findViewById<RecyclerView>(R.id.animal_recycler_view)
        val recyclerAdapter = AnimalAdapter(animals, ids, this)
        animalRecyclerView.layoutManager = layoutManager
        animalRecyclerView.adapter = recyclerAdapter
        return recyclerAdapter
    }

    }