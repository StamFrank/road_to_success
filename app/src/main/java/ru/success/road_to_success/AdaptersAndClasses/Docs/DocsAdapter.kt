package ru.success.road_to_success.AdaptersAndClasses.Docs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.success.road_to_success.databinding.ItemDocsBinding

class DocsAdapter(private val docsList: List<DocsItems>) : RecyclerView.Adapter<DocsAdapter.DocsViewHolder>() {
    private val expandedPositions = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocsViewHolder {
        val binding = ItemDocsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DocsViewHolder(binding)
    }
    //from Glide to localfiles optimizer
    override fun onBindViewHolder(holder: DocsViewHolder, position: Int) {
        val docs = docsList[position]

        val context = holder.itemView.context
        val resourceId = context.resources.getIdentifier(
            docs.docsImage,
            "drawable",
            context.packageName
        )

        if (resourceId != 0) {
            holder.binding.docsImageView.setImageResource(resourceId)
        } else {

            holder.binding.docsImageView.setImageResource(android.R.drawable.ic_menu_report_image)
        }

        holder.binding.docsTypeName.text = docs.docsType
        holder.binding.docsTypeSize.text = docs.docsSize


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
            Toast.makeText(holder.itemView.context, "Button Save Data " + docs.docsSize, Toast.LENGTH_SHORT).show()
        }

        holder.binding.buttonDocsSaveUrl.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Button Save Url " + docs.docsSize, Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int = docsList.size

    inner class DocsViewHolder(val binding: ItemDocsBinding) : RecyclerView.ViewHolder(binding.root)
}