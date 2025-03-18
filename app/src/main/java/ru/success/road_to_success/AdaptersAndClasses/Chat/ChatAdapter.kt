package ru.success.road_to_success.AdaptersAndClasses.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.success.road_to_success.databinding.ItemChatBinding

class ChatAdapter(private val chatList: List<ChatItems>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    private val expandedPositions = mutableSetOf<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]

        //Glide set Images to Albums
        Glide.with(holder.itemView.context)
            .load(chat.profilePhotoUrl)
            .circleCrop()
            .into(holder.binding.chatImageView)

        holder.binding.chatNameView.text = chat.profileName
        holder.binding.chatCmidView.text = chat.cmid
        holder.binding.chatIdView.text = chat.id

        if (expandedPositions.contains(position)) {
            holder.binding.chatButtonContainer.visibility = View.VISIBLE
        } else {
            holder.binding.chatButtonContainer.visibility = View.GONE
        }


        holder.itemView.setOnClickListener {
            if (expandedPositions.contains(position)) {
                expandedPositions.remove(position)
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position)
        }


        holder.binding.buttonChatSaveData.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Button Save Data " + chat.id, Toast.LENGTH_SHORT).show()
        }

        holder.binding.buttonChatSaveUrl.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Button Save Url " + chat.id, Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int = chatList.size

    inner class ChatViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root)
}