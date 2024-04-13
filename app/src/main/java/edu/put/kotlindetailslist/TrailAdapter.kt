package edu.put.kotlindetailslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.put.kotlindetailslist.database.Trail
import edu.put.kotlindetailslist.databinding.TrailRowLayoutBinding

class TrailAdapter(
    private val trails: List<Trail>,
    private val onTrailClick: (Trail) -> Unit
) : RecyclerView.Adapter<TrailAdapter.MyViewHolder>() {


    inner class MyViewHolder(binding: TrailRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.nameRow
        val image = binding.imageViewRow

        init {
            binding.root.setOnClickListener {
                onTrailClick(trails[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            binding = TrailRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return trails.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = trails[position].name
        holder.image.setImageResource(trails[position].image)
    }
}
