package dev.pitlor.smssync.activities.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.pitlor.smssync.adapters.ConversationListAdapter
import dev.pitlor.smssync.databinding.FragmentMessagesBinding
import dev.pitlor.smssync.datasources.AppRepository
import dev.pitlor.smssync.datasources.MessageWithContact
import dev.pitlor.smssync.viewmodels.MessageFragmentViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MessagesFragment : Fragment() {
    private val fragmentViewModel by viewModels<MessageFragmentViewModel>()

    @Inject
    lateinit var appRepository: AppRepository

    fun <T> List<LiveData<T>>.combine(): MutableLiveData<MutableList<T?>> {
        val list = MutableList<T?>(size) { null }
        val liveData = MutableLiveData(list)

        forEachIndexed { index, data ->
            data.observe(this@MessagesFragment) {
                list[index] = it
                liveData.postValue(list)
            }
        }

        return liveData
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val context = requireContext()
        val conversationListAdapter = ConversationListAdapter(this)

        val binding = FragmentMessagesBinding.inflate(inflater).apply {
            lifecycleOwner = this@MessagesFragment
            viewModel = fragmentViewModel

            regularState.messagesList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = conversationListAdapter
            }
        }

        lifecycleScope.launch {
            val l =
                Transformations
                .map(appRepository.getAllThreads())
                { it.map(appRepository::peakThread) }


            appRepository.getAllThreads().observe(viewLifecycleOwner) { threadIds ->
                Log.d("fragment", threadIds.toString())
                val messages: List<MessageWithContact> = threadIds
                    .map(appRepository::peakThread)
                    .mapNotNull { it.value }
                conversationListAdapter.setMessages(messages)
            }
        }

        return binding.root
    }
}