package dev.pitlor.smssync.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import dev.pitlor.smssync.R
import dev.pitlor.smssync.databinding.ListItemThreadDetailBinding
import dev.pitlor.smssync.datasources.MessageWithContact
import dev.pitlor.smssync.viewmodels.ListItemThreadDetailViewModel

class ConversationDetailAdapter(private val fragment: Fragment) : RecyclerView.Adapter<ConversationDetailAdapter.MessageViewHolder>() {
    private val layoutInflater: LayoutInflater = fragment
        .requireContext()
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var messages = emptyList<MessageWithContact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view: ListItemThreadDetailBinding = DataBindingUtil
            .inflate(layoutInflater, R.layout.list_item_thread_detail, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.setMessage(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun setMessages(messages: List<MessageWithContact>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    inner class MessageViewHolder(val binding: ListItemThreadDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel by fragment.viewModels<ListItemThreadDetailViewModel>()

        fun setMessage(message: MessageWithContact) {
            viewModel.message = message
            binding.executePendingBindings()
        }

        init {
            binding.viewModel = viewModel
        }
    }

}