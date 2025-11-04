package com.pampang.nav.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pampang.nav.databinding.ItemGroupChatMessageBinding
import com.pampang.nav.models.GroupChatMessage

class GroupChatAdapter : ListAdapter<GroupChatMessage, GroupChatAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemGroupChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageViewHolder(private val binding: ItemGroupChatMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: GroupChatMessage) {
            binding.chatMessage = chatMessage
            binding.executePendingBindings()
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<GroupChatMessage>() {
        override fun areItemsTheSame(oldItem: GroupChatMessage, newItem: GroupChatMessage): Boolean {
            return oldItem.timestamp == newItem.timestamp && oldItem.senderId == newItem.senderId
        }

        override fun areContentsTheSame(oldItem: GroupChatMessage, newItem: GroupChatMessage): Boolean {
            return oldItem == newItem
        }
    }
}