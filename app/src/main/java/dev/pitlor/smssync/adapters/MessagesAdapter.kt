package dev.pitlor.smssync.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import dev.pitlor.smssync.datasources.Message
import dev.pitlor.smssync.R
import dev.pitlor.smssync.adapters.MessagesAdapter.MessageViewHolder
import dev.pitlor.smssync.databinding.ListItemMessagesBinding
import dev.pitlor.smssync.viewmodels.ConversationsListItemViewModel

class MessagesAdapter(private val fragment: Fragment) : RecyclerView.Adapter<MessageViewHolder>() {
    private val layoutInflater: LayoutInflater = fragment
        .requireContext()
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var messages = emptyList<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view: ListItemMessagesBinding = DataBindingUtil
            .inflate(layoutInflater, R.layout.list_item_messages, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.setMessage(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun setMessages(messages: List<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    inner class MessageViewHolder(val binding: ListItemMessagesBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel by fragment.viewModels<ConversationsListItemViewModel>()

        fun setMessage(message: Message) {
            viewModel.message = message
        }

        init {
            binding.viewModel = viewModel
        }
    }

}