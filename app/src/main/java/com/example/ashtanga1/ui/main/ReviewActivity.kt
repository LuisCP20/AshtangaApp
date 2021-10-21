package com.example.ashtanga1.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ashtanga1.R
import com.example.ashtanga1.adapter.CardAdapter
import com.example.ashtanga1.data.DataSource
import com.example.ashtanga1.databinding.ReviewActivityBinding


class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ReviewActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize data.
        val series1 = DataSource.completeSequence

        binding = ReviewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        binding.recyclerView.adapter = CardAdapter(series1, this)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = Intent(this.applicationContext, MainFragment::class.java)
        binding.button.setOnClickListener { supportNavigateUpTo(intent) }
        //supportNavigateUpTo(intent)
    }

}