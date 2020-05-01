package com.example.jaanadoptapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        dog_page.setOnClickListener {
            val myIntent = Intent(this, AnimalsTwo::class.java)
            myIntent.putExtra("species", "Canine")
            startActivity(myIntent)
        }

        cat_page.setOnClickListener {
            val myIntent = Intent(this, Animals::class.java)
            myIntent.putExtra("species", "Feline")
            startActivity(myIntent)
        }

        other_page.setOnClickListener {
            val myIntent = Intent(this, Animals::class.java)
            myIntent.putExtra("species", "Other")
            startActivity(myIntent)
        }
    }

}
