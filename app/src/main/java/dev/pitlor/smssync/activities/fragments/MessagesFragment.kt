package dev.pitlor.smssync.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import dev.pitlor.smssync.databinding.FragmentMessagesBinding
import dev.pitlor.smssync.viewmodels.MessageFragmentViewModel
import dev.pitlor.smssync.viewmodels.MessageRegularViewModel

@AndroidEntryPoint
class MessagesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMessagesBinding.inflate(inflater)
        val viewModelProvider = ViewModelProvider(this)
        binding.viewModel = viewModelProvider.get(MessageFragmentViewModel::class.java)
        binding.regularState.viewModel = viewModelProvider.get(MessageRegularViewModel::class.java)
        return binding.root
    }
}