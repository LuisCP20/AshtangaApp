package com.example.ashtanga1

import android.content.Intent
import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.ashtanga1.adapter.CardAdapter
import com.example.ashtanga1.data.DataSource
import com.example.ashtanga1.databinding.ReviewActivityBinding
import com.example.ashtanga1.ui.main.MainFragment
import android.R
import android.view.View


class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ReviewActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val series1 = DataSource.completeSequence

        binding = ReviewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = CardAdapter(series1, this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = Intent(this.applicationContext, MainFragment::class.java)
        binding.button.setOnClickListener {supportNavigateUpTo(intent)}
        supportActionBar?.show()
        //supportNavigateUpTo(intent)
    }

}