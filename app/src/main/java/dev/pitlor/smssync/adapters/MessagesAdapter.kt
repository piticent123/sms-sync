package dev.pitlor.smssync.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dev.pitlor.sms.Message
import dev.pitlor.smssync.R
import dev.pitlor.smssync.adapters.MessagesAdapter.MessageViewHolder
import dev.pitlor.smssync.databinding.MessagesListItemBinding
import dev.pitlor.smssync.viewmodels.ConversationsListItemViewModel

class MessagesAdapter(private val fragment: Fragment, private val messages: List<Message>)
    : RecyclerView.Adapter<MessageViewHolder>()
{
    private val layoutInflater: LayoutInflater = fragment
        .requireContext()
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view: MessagesListItemBinding = DataBindingUtil
            .inflate(layoutInflater, R.layout.messages_list_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.setMessage(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class MessageViewHolder(val binding: MessagesListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setMessage(message: Message) {
            binding.viewModel.message = message
        }

        init {
            binding.viewModel = ViewModelProvider(fragment).get(ConversationsListItemViewModel::class.java)
        }
    }

}