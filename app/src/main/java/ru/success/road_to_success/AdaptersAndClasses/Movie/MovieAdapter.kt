package ru.success.road_to_success.AdaptersAndClasses.Movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.success.road_to_success.databinding.ItemMovieBinding

class MovieAdapter (private val movieList: List<MovieItems>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val expandedPositions = mutableSetOf<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        //Glide set Images to Albums
        Glide.with(holder.itemView.context).load(movie.movieAlbumImageUrl)
            .into(holder.binding.movieImageView)

        holder.binding.movieAlbumName.text = movie.movieAlbumName
        holder.binding.movieAlbumId.text = movie.movieAlbumId
        holder.binding.movieAlbumCount.text = "Количество: ${movie.movieItemsCount}"

        if (expandedPositions.contains(position)) {
            holder.binding.movieButtonContainer.visibility = View.VISIBLE
        } else {
            holder.binding.movieButtonContainer.visibility = View.GONE
        }


        holder.itemView.setOnClickListener {
            if (expandedPositions.contains(position)) {
                expandedPositions.remove(position)
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position)
        }


        holder.binding.buttonMovieSaveData.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Button Save Data " + movie.movieAlbumId,
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.binding.buttonMovieSaveUrl.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Button Save Url " + movie.movieAlbumId,
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun getItemCount(): Int = movieList.size

    inner class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
}