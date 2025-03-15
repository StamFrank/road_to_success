package ru.success.road_to_success.AdaptersAndClasses.PhotoAlbums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.success.road_to_success.databinding.ItemAlbumBinding

class AlbumAdapter(private val albumList: List<AlbumItems>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {
    private val expandedPositions = mutableSetOf<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]

        //Glide set Images to Albums
        Glide.with(holder.itemView.context)
            .load(album.profilePhotoUrl)
            .into(holder.binding.profileImageView)

        holder.binding.nameTextView.text = album.name
        holder.binding.idTextView.text = album.id
        holder.binding.itemCountTextView.text = "Количество: ${album.itemCount}"

        if (expandedPositions.contains(position)) {
            holder.binding.buttonContainer.visibility = View.VISIBLE
        } else {
            holder.binding.buttonContainer.visibility = View.GONE
        }


        holder.itemView.setOnClickListener {
            if (expandedPositions.contains(position)) {
                expandedPositions.remove(position)
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position)
        }


        holder.binding.buttonAlbumSaveData.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Button Save Data " + album.id, Toast.LENGTH_SHORT).show()
        }

        holder.binding.buttonAlbumSaveUrl.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Button Save Url " + album.id, Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int = albumList.size

    inner class AlbumViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root)
}