package com.example.ashtanga1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ashtanga1.R
import com.example.ashtanga1.data.DataSource
import com.example.ashtanga1.model.Asana

// Based on google developer tutorial Dogglers App. https://github.com/google-developer-training/android-basics-kotlin-dogglers-app/tree/main
/* Adapts datasource to cards that will be displayed in Review mode.
*  Receives the list of postures and context about the object that will be passed.
*/
class CardAdapter(private val dataset: List<Asana>, private val context: Context)
    :RecyclerView.Adapter<CardAdapter.CardViewHolder>()
{
    // Load full series
    private var series1: List<Asana> = DataSource.completeSequence

    // Nested class that creates a viewholder for each card view. Derived from RecyclerView.ViewHolder
    class CardViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val imageViewPosture: ImageView = view.findViewById(R.id.postureR)
        val textViewName: TextView = view.findViewById(R.id.nameR)
        val textViewDrishti: TextView = view.findViewById(R.id.drishtiR)
        val textViewBandha: TextView = view.findViewById(R.id.detailsR)
    }

    // Create new view holders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        // Type of layout
        var layout = R.layout.asana_card
        // Reference to the view of the new item
        val adapterLayout = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return CardViewHolder(adapterLayout)
    }

    // Necessary abstract method overrides for implementation

    // Size of dataset
    override fun getItemCount(): Int {
        return series1.size
    }

    // Replace content of a view
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentAsana = series1[position]
        val resources = context.resources
        holder.imageViewPosture.setImageResource(currentAsana.postureImageResourceId)
        holder.textViewName.text = currentAsana.name
        holder.textViewDrishti.text = currentAsana.drishti
        holder.textViewBandha.text = currentAsana.bandha

    }
}