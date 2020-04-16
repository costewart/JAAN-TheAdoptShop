package com.example.jaanadoptapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }

//        val preferences = getSharedPreferences("database", Context.MODE_PRIVATE) //grabs something from phone's hardware
//        val savedName = preferences.getString("savedProductName", "This value doesn't exits.")
//        d("charlotte", "saved message is: $savedName")
//
//        lastSavedProduct.text = savedName

        // fetching the URL can be slow, so need asynchronous programming - on a background thread

//        lifecycleScope.launch(Dispatchers.Default) {
//            val specialMessage = URL("https://finepointmobile.com/api/inventory/v1/message").readText()
//            d("charlotte", "the message is: $specialMessage")
//            lastSavedProduct.text = specialMessage
//        }

    }
}
