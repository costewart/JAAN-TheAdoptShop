package com.example.jaanadoptapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_dog.*

class NewDog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_dog)

        back_button.setOnClickListener {
            startActivity(Intent(this, Dogs::class.java))
        }

    }

}