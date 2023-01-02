package com.example.proj.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.R
import com.example.proj.database.models.Position
import com.example.proj.databinding.FragmentPositionItemBinding

class PositionAdapter(
    private var onUpdateClicked: (Position) -> Unit,
    private var onDeleteClicked: (Position) -> Unit
): ListAdapter<Position, PositionAdapter.PositionViewHolder>(DiffCallback) {

    class PositionViewHolder(
        binding: FragmentPositionItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        val cardView = binding.cardView
        val name = binding.name
        val status = binding.status
        val statusImg = binding.statusImg
        val updateButton = binding.updateButton
        val deleteButton = binding.deleteButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        return PositionViewHolder(
            FragmentPositionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.status.text = item.status

        when (item.status) {
            "steady" -> {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#79DE79"))
                holder.statusImg.setImageResource(R.drawable.steady)
            }
            "tilted" -> {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FCFC99"))
                holder.statusImg.setImageResource(R.drawable.tilted)
            }
            "fall" -> {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FB6962"))
                holder.statusImg.setImageResource(R.drawable.fall)
            }
        }

        holder.updateButton.setOnClickListener(){
            onUpdateClicked(item)
        }

        holder.deleteButton.setOnClickListener(){
            onDeleteClicked(item)
        }
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Position>() {
            override fun areItemsTheSame(oldItem: Position, newItem: Position): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Position, newItem: Position): Boolean {
                return oldItem == newItem
            }
        }
    }
}