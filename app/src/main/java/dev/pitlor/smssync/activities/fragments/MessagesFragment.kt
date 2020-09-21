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
import dev.pitlor.smssync.datasources.AppRepository
import dev.pitlor.smssync.viewmodels.MessageFragmentViewModel
import dev.pitlor.smssync.viewmodels.MessageRegularViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MessagesFragment : Fragment() {
    private val fragmentViewModel by viewModels<MessageFragmentViewModel>()
    private val regularViewModel by viewModels<MessageRegularViewModel>()

    @Inject
    lateinit var appRepository: AppRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val messagesAdapter = MessagesAdapter(this)
        val binding = FragmentMessagesBinding.inflate(inflater).apply {
            lifecycleOwner = this@MessagesFragment

            viewModel = fragmentViewModel
            regularState.viewModel = regularViewModel
            regularState.messagesList.adapter = messagesAdapter
        }

        appRepository.getAllMessages().observe(viewLifecycleOwner, messagesAdapter::setMessages)

        return binding.root
    }
}