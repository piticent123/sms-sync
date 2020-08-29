package dev.pitlor.smssync.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import dev.pitlor.smssync.databinding.FragmentSyncBinding
import dev.pitlor.smssync.viewmodels.SyncEmptyStateViewModel
import dev.pitlor.smssync.viewmodels.SyncFragmentViewModel
import dev.pitlor.smssync.viewmodels.SyncRegularViewModel

@AndroidEntryPoint
class SyncFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = FragmentSyncBinding.inflate(inflater)
        val viewModelProvider = ViewModelProvider(this)
        view.viewModel = viewModelProvider.get(SyncFragmentViewModel::class.java)
        view.emptyState.viewModel = viewModelProvider.get(SyncEmptyStateViewModel::class.java)
        view.regularState.viewModel = viewModelProvider.get(SyncRegularViewModel::class.java)
        return view.root
    }
}