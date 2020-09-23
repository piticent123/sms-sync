package dev.pitlor.smssync.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.pitlor.smssync.adapters.ConversationListAdapter
import dev.pitlor.smssync.databinding.FragmentMessagesBinding
import dev.pitlor.smssync.datasources.AppRepository
import dev.pitlor.smssync.viewmodels.MessageFragmentViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MessagesFragment : Fragment() {
    private val fragmentViewModel by viewModels<MessageFragmentViewModel>()

    @Inject
    lateinit var appRepository: AppRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conversationListAdapter = ConversationListAdapter(this)
        val context = requireContext()

        val binding = FragmentMessagesBinding.inflate(inflater).apply {
            lifecycleOwner = this@MessagesFragment
            viewModel = fragmentViewModel

            regularState.messagesList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = conversationListAdapter
            }
        }

        appRepository
            .getAllThreads()
            .observe(viewLifecycleOwner, conversationListAdapter::setMessages)

        return binding.root
    }
}