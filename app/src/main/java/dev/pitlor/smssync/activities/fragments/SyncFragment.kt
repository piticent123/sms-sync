package dev.pitlor.smssync.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import dev.pitlor.smssync.adapters.SyncProgressAdapter
import dev.pitlor.smssync.databinding.FragmentSyncBinding
import dev.pitlor.smssync.datasources.AppRepository
import dev.pitlor.smssync.tasks.SmsSync.Companion.Progress
import dev.pitlor.smssync.viewmodels.SyncEmptyStateViewModel
import dev.pitlor.smssync.viewmodels.SyncFragmentViewModel
import dev.pitlor.smssync.viewmodels.SyncRegularViewModel
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SyncFragment : Fragment() {
    private val fragmentViewModel by viewModels<SyncFragmentViewModel>()
    private val emptyStateViewModel by viewModels<SyncEmptyStateViewModel>()
    private val regularStateViewModel by viewModels<SyncRegularViewModel>()

    @Inject
    lateinit var appRepository: AppRepository

    private var lastId: UUID? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val context = requireContext()
        val workManager = WorkManager.getInstance(context)
        val syncProgressAdapter = SyncProgressAdapter(this)

        val view = FragmentSyncBinding.inflate(inflater).apply {
            lifecycleOwner = this@SyncFragment
            viewModel = fragmentViewModel

            emptyState.apply {
                viewModel = emptyStateViewModel
                lifecycleOwner = this@SyncFragment
            }

            regularState.apply {
                viewModel = regularStateViewModel
                lifecycleOwner = this@SyncFragment

                recyclerViewSyncProgress.apply {
                    layoutManager = LinearLayoutManager(context).apply {
                        stackFromEnd = true
                    }

                    adapter = syncProgressAdapter
                }
            }
        }

        appRepository.getLastSync().observe(viewLifecycleOwner, fun(sync) {
            if (sync == null) return

            if (sync.workRequestId != lastId) {
                syncProgressAdapter.clear()
                lastId = sync.workRequestId
            }

            var lastProgressItem = ""
            workManager
                .getWorkInfoByIdLiveData(sync.workRequestId)
                .observe(viewLifecycleOwner, fun(workInfo) {
                    if (workInfo == null) return
                    val progress = workInfo.progress.getString(Progress) ?: return
                    if (progress == lastProgressItem)
                        return
                    else
                        lastProgressItem = progress
                    syncProgressAdapter.addProgress(progress)
                })
        })

        return view.root
    }
}