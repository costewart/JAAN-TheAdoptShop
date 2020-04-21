package com.example.jaanadoptapp

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_dog.*


class NewDog : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    var inputSpecies = ""
    var inputSex = ""
    var inputDogFriendly = false
    var inputCatFriendly = false
    var inputVaccinated = false
    var inputSterilized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_dog)

        // TODO: not working yet
//        backButton.setOnClickListener {
//            startActivity(Intent(this, Dogs::class.java))
//        }

        val submit = findViewById<Button>(R.id.submit_new_dog)

        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val inputName = findViewById<View>(R.id.inputName) as EditText
                val inputBreed = findViewById<View>(R.id.inputBreed) as EditText
                // eventually should change this to number input
                val inputAge = findViewById<View>(R.id.inputAge) as EditText


                var name = inputName.text.toString().trim()
                var breed = inputBreed.text.toString().trim()
                var age = inputAge.text.toString().trim().toInt()

                d("char:", "name = ${name}")

                try {
                    val data = hashMapOf(
                        "name" to name,
                        "breed" to breed,
                        "species" to inputSpecies,
                        "age" to age,
                        "sex" to inputSex,
                        "dogfriendly" to inputDogFriendly,
                        "catfriendly" to inputCatFriendly,
                        "vaccinated" to inputVaccinated,
                        "sterilized" to inputSterilized
                    )

                    db.collection("Animals").add(data).addOnSuccessListener { documentReference ->
                        d("char:", "DocumentSnapshot written with ID: ${documentReference.id}")
                        Toast.makeText(
                            this@NewDog,
                            "Successfully added ${name}.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener { e ->
                        d("char:", "Error adding document", e)
                    }

                } catch (e: Exception) {
                    //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }



    fun onSpeciesRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.canineRadio ->
                    if (checked) {
                        inputSpecies = "Canine"
                    }
                R.id.felineRadio ->
                    if (checked) {
                        inputSpecies = "Feline"
                    }
                R.id.otherRadio ->
                    if (checked) {
                        inputSpecies = "Other"
                    }
            }
        }
    }

    fun onSexRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.maleRadio ->
                    if (checked) {
                        inputSex = "Male"
                    }
                R.id.femaleRadio ->
                    if (checked) {
                        inputSex = "Female"
                    }
            }
        }
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox_dogfriendly -> {
                    if (checked) {
                        inputDogFriendly = true
                    }
                }
                R.id.checkbox_catfriendly -> {
                    if (checked) {
                        inputCatFriendly = true
                    }
                }
                R.id.checkbox_vaccinated -> {
                    if (checked) {
                        inputVaccinated = true
                    }
                }
                R.id.checkbox_sterilized -> {
                    if (checked) {
                        inputSterilized = true
                    }
                }
            }
        }
    }
}



