package dev.pitlor.smssync.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.pitlor.smssync.databinding.FragmentSyncBinding
import dev.pitlor.smssync.viewmodels.SyncEmptyStateViewModel
import dev.pitlor.smssync.viewmodels.SyncFragmentViewModel
import dev.pitlor.smssync.viewmodels.SyncRegularViewModel

@AndroidEntryPoint
class SyncFragment : Fragment() {
    private val viewModel by viewModels<SyncFragmentViewModel>()
    private val emptyStateViewModel by viewModels<SyncEmptyStateViewModel>()
    private val regularStateViewModel by viewModels<SyncRegularViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = FragmentSyncBinding.inflate(inflater)
        view.viewModel = viewModel
        view.emptyState.viewModel = emptyStateViewModel
        view.regularState.viewModel = regularStateViewModel
        return view.root
    }
}