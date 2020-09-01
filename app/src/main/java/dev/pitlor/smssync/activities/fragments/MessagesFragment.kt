package dev.pitlor.smssync.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.pitlor.smssync.adapters.MessagesAdapter
import dev.pitlor.smssync.databinding.FragmentMessagesBinding
import dev.pitlor.smssync.viewmodels.MessageFragmentViewModel
import dev.pitlor.smssync.viewmodels.MessageRegularViewModel

@AndroidEntryPoint
class MessagesFragment : Fragment() {
    private val viewModel by viewModels<MessageFragmentViewModel>()
    private val regularViewModel by viewModels<MessageRegularViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMessagesBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.regularState.viewModel = regularViewModel

        val messagesAdapter = MessagesAdapter(this)
        binding.regularState.messagesList.adapter = messagesAdapter
        regularViewModel.messages.observe(viewLifecycleOwner, messagesAdapter::setMessages)

        return binding.root
    }
}