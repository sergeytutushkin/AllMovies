package dev.tutushkin.lesson3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val textView: TextView = findViewById(R.id.tvHelloWorld)
//        textView.setOnClickListener { moveToDetailsScreen() }
    }

//    private fun moveToDetailsScreen() {
//        val intent = Intent(this, MovieDetailsActivity::class.java)
//        startActivity(intent)
//    }
}