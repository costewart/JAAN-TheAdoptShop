package com.example.jaanadoptapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log.d
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.add_animal.*
import java.io.IOException
import java.util.*


class AddAnimal : AppCompatActivity(), View.OnClickListener {

    private val PICK_IMAGE_REQUEST = 1234

    private var filePath: Uri? = null
    internal var storage: FirebaseStorage?=null
    internal var storageReference:StorageReference?=null

    val db = FirebaseFirestore.getInstance()
    var inputSpecies = ""
    var inputSex = ""
    var inputDogFriendly = false
    var inputCatFriendly = false
    var inputVaccinated = false
    var inputSterilized = false

    override fun onClick(p0: View) {


        if(p0 === chooseButton)
            showFileChooser()
        else if (p0 === submit_new_dog) {
            val inputName = findViewById<View>(R.id.inputName) as EditText
            val inputBreed = findViewById<View>(R.id.inputBreed) as EditText
            // eventually should change this to number input
            val inputAge = findViewById<View>(R.id.inputAge) as EditText

            val name = inputName.text.toString().trim()
            val breed = inputBreed.text.toString().trim()
            val age = inputAge.text.toString().trim().toInt()

            d("char:", "name = ${name}")

            val uri = uploadFile()

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
                    "sterilized" to inputSterilized,
                    "uri" to uri
                )

                db.collection("Animals").add(data).addOnSuccessListener { documentReference ->
                    d("char:", "DocumentSnapshot written with ID: ${documentReference.id}")

                    Toast.makeText(
                        this@AddAnimal,
                        "Successfully added ${name}.",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener { e ->
                    d("char:", "Error adding document", e)
                }

                finish()

            } catch (e: Exception) {
                //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE_REQUEST&&
                resultCode== Activity.RESULT_OK &&
                data != null && data.data !=null) {
            filePath = data.data
            try{
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView!!.setImageBitmap(bitmap)
            } catch (e:IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun uploadFile(): String {

        if(filePath!= null)
        {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val pathString = UUID.randomUUID().toString()

            val imageRef = storageReference!!.child("images/$pathString")

            imageRef.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener{
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0*taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount
                    progressDialog.setMessage("Uploaded" + progress.toInt() + "%...")
                }
            return pathString
        } else {
            return ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_animal)

        // TODO: not working yet
//        backButton.setOnClickListener {
//            startActivity(Intent(this, Dogs::class.java))
//        }

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        //Setup button

        chooseButton.setOnClickListener (this)
        submit_new_dog.setOnClickListener (this)

    }


    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/+"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), PICK_IMAGE_REQUEST)
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



