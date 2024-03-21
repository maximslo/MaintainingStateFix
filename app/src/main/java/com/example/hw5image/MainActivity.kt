package com.example.hw5image

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private val images = arrayOf(R.drawable.zion, R.drawable.sanddunes, R.drawable.arches)
    private var currentImageResId: Int = -1 // Variable to keep track of the current image resource ID
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)
        val button: Button = findViewById(R.id.button)

        sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        // Check if there's a saved image resource ID, otherwise don't set any image
        val savedImageRes = sharedPreferences.getInt("imageRes", -1)
        if (savedImageRes != -1) {
            currentImageResId = savedImageRes
            imageView.setImageResource(savedImageRes)
        } else {
            // Optionally, you can set a default image or make the ImageView invisible
            imageView.setImageResource(android.R.color.transparent) // or imageView.visibility = View.GONE
        }

        editText.setText(sharedPreferences.getString("editTextContent", ""))

        button.setOnClickListener {
            currentImageResId = images.random()
            imageView.setImageResource(currentImageResId)
        }
    }


    override fun onDestroy() {
        Log.d("MainActivity", "onDestroy is called")
        super.onDestroy()
        val editor = sharedPreferences.edit()
        editor.putInt("imageRes", currentImageResId)
        editor.putString("editTextContent", editText.text.toString())
        editor.apply() // Using apply() to commit changes asynchronously
    }
}
