package ru.success.road_to_success.AdaptersAndClasses.Docs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.success.road_to_success.databinding.ItemDocsBinding

class ChatAdapter(private val docsList: List<DocsItems>) : RecyclerView.Adapter<ChatAdapter.DocsViewHolder>() {
    private val expandedPositions = mutableSetOf<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocsViewHolder {
        val binding = ItemDocsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DocsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DocsViewHolder, position: Int) {
        val chat = docsList[position]

        //Glide set Images to Albums
        Glide.with(holder.itemView.context)
            .load(chat.docsImage)
            .circleCrop()
            .into(holder.binding.docsImageView)

        holder.binding.docsTypeName.text = chat.docsType.toString()
        holder.binding.docsTypeSize.text = chat.docsSize

        if (expandedPositions.contains(position)) {
            holder.binding.docsButtonContainer.visibility = View.VISIBLE
        } else {
            holder.binding.docsButtonContainer.visibility = View.GONE
        }


        holder.itemView.setOnClickListener {
            if (expandedPositions.contains(position)) {
                expandedPositions.remove(position)
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position)
        }


        holder.binding.buttonDocsSaveData.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Button Save Data " + chat.docsSize, Toast.LENGTH_SHORT).show()
        }

        holder.binding.buttonDocsSaveUrl.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Button Save Url " + chat.docsSize, Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int = docsList.size

    inner class DocsViewHolder(val binding: ItemDocsBinding) : RecyclerView.ViewHolder(binding.root)
}