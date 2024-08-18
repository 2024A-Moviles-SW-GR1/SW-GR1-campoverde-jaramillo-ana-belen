package com.example.netflix

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val premiereImages = listOf(
            R.drawable.portada, R.drawable.portada1, R.drawable.portada2,
            R.drawable.portada3,R.drawable.portada4
        )

        val horrorImages = listOf(
            R.drawable.portada5, R.drawable.portada6,
            R.drawable.portada7, R.drawable.portada8, R.drawable.portada9
        )

        val premiereAdapter = MovieAdapter(premiereImages)
        val horrorAdapter = MovieAdapter(horrorImages)

        findViewById<RecyclerView>(R.id.rv_premiere).apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = premiereAdapter
        }

        findViewById<RecyclerView>(R.id.rv_horror).apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = horrorAdapter
        }
    }
}
