package com.example.jaanadoptapp

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.add_dog.*

class NewDog : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_dog)

//        back_button.setOnClickListener {
//            startActivity(Intent(this, Dogs::class.java))
//        }

        val submit = findViewById<Button>(R.id.submit_new_dog)


        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val inputName = findViewById<View>(R.id.inputName) as EditText
                val inputBreed = findViewById<View>(R.id.inputBreed) as EditText
                //val inputSpecies = findViewById<View>(R.id.inputSpecies) as EditText
                // eventually should change this to number input
                val inputAge = findViewById<View>(R.id.inputAge) as EditText


                var name = inputName.text.toString().trim()
                var breed = inputBreed.text.toString().trim()
                //var species = inputSpecies.text.toString().trim()
                var age = inputAge.text.toString().trim().toInt()

                d("char:", "name = ${name}")

                try {
                    val data = hashMapOf(
                        "name" to name,
                        "breed" to breed,
                        //"species" to species,
                        "age" to age
                    )

                    db.collection("Animals").add(data).addOnSuccessListener { documentReference ->
                        d("char:", "DocumentSnapshot written with ID: ${documentReference.id}")
                        Toast.makeText(this@NewDog, "Successfully added ${name}.", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener { e ->
                        d("char:", "Error adding document", e)
                    }

                } catch (e: Exception) {
                    //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}




//        private fun store() {
//            val inputName = findViewById<View>(R.id.inputName) as EditText
//            val inputBreed = findViewById<View>(R.id.inputBreed) as EditText
//            val inputSpecies = findViewById<View>(R.id.inputSpecies) as EditText
//            val inputAge = findViewById<View>(R.id.inputAge) as EditText
//
//            var name = inputName.text.toString().trim()
//            var breed = inputBreed.text.toString().trim()
//            var species = inputSpecies.text.toString().trim()
//            var age = inputAge.text.toString().trim()
//
//            try {
//                val data = hashMapOf(
//                    "name" to name,
//                    "breed" to breed,
//                    "species" to species,
//                    "age" to age
//                )
//
//                db.collection("Animals").add(data).addOnSuccessListener { documentReference ->
//                    d("char:", "DocumentSnapshot written with ID: ${documentReference.id}")
//                }.addOnFailureListener { e ->
//                    d("char:", "Error adding document", e)
//                }
//
//            } catch (e: Exception) {
//                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
//            }
//        }



