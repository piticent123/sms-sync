package dev.pitlor.smssync.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import dev.pitlor.smssync.adapters.SyncProgressAdapter
import dev.pitlor.smssync.databinding.FragmentSyncBinding
import dev.pitlor.smssync.datasources.AppRepository
import dev.pitlor.smssync.tasks.SmsSync.Companion.Finished
import dev.pitlor.smssync.tasks.SmsSync.Companion.Progress
import dev.pitlor.smssync.viewmodels.SyncEmptyStateViewModel
import dev.pitlor.smssync.viewmodels.SyncFragmentViewModel
import dev.pitlor.smssync.viewmodels.SyncRegularViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SyncFragment : Fragment() {
    private val fragmentViewModel by viewModels<SyncFragmentViewModel>()
    private val emptyStateViewModel by viewModels<SyncEmptyStateViewModel>()
    private val regularStateViewModel by viewModels<SyncRegularViewModel>()

    @Inject
    lateinit var appRepository: AppRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val context = requireContext()
        val workManager = WorkManager.getInstance(context)
        val syncProgressAdapter = SyncProgressAdapter(this)

        val view = FragmentSyncBinding.inflate(inflater).apply {
            lifecycleOwner = this@SyncFragment
            emptyState.lifecycleOwner = this@SyncFragment
            regularState.lifecycleOwner = this@SyncFragment

            viewModel = fragmentViewModel
            emptyState.viewModel = emptyStateViewModel
            regularState.viewModel = regularStateViewModel

            regularState.recyclerViewSyncProgress.layoutManager = LinearLayoutManager(context)
            regularState.recyclerViewSyncProgress.adapter = syncProgressAdapter
        }

        appRepository.getLastSync().observe(viewLifecycleOwner, {
            if (it == null) return@observe

            syncProgressAdapter.clear()
            workManager
                .getWorkInfoByIdLiveData(it.workRequestId)
                .observe(viewLifecycleOwner, fun(workInfo) {
                    val progressItem = workInfo.progress.getString(Progress) ?: return
                    syncProgressAdapter.addProgress(progressItem)

                    regularStateViewModel.loading = progressItem != Finished
                    view.executePendingBindings()
                })
        })

        return view.root
    }
}