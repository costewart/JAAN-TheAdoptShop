package com.example.jaanadoptapp

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dogs.*
import layout.AnimalModel

class Dogs : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    private inner class AnimalViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setAnimalName(productName: String) {
            val textView = view.findViewById<TextView>(R.id.text_view)
            textView.text = productName
        }
    }

    private inner class ProductFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<AnimalModel>) : FirestoreRecyclerAdapter<AnimalModel, AnimalViewHolder>(options) {
        override fun onBindViewHolder(animalViewHolder: AnimalViewHolder, position: Int, productModel: AnimalModel) {
            animalViewHolder.setAnimalName(productModel.name)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.dogs, parent, false)
            return AnimalViewHolder(view)
        }
    }

    private var adapter: ProductFirestoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dogs)

        db.collection("Animals")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    d("exist", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("noexist", "Error getting documents.", exception)
            }

        recycler_view.layoutManager = LinearLayoutManager(this)
        val query = db.collection("Animals").orderBy("name", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<AnimalModel>().setQuery(query, AnimalModel::class.java).build()

        this.adapter = ProductFirestoreRecyclerAdapter(options)
        recycler_view.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }
    }

}