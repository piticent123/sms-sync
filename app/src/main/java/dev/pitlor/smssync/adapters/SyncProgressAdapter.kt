package dev.pitlor.smssync.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import dev.pitlor.smssync.R
import dev.pitlor.smssync.adapters.SyncProgressAdapter.SyncProgressViewHolder
import dev.pitlor.smssync.databinding.ListItemSyncProgressBinding
import dev.pitlor.smssync.viewmodels.ListItemSyncProgressViewModel

class SyncProgressAdapter(private val fragment: Fragment) : RecyclerView.Adapter<SyncProgressViewHolder>() {
    private val layoutInflater: LayoutInflater = fragment
        .requireContext()
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val progressItems = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyncProgressViewHolder {
        val view: ListItemSyncProgressBinding = DataBindingUtil
            .inflate(layoutInflater, R.layout.list_item_sync_progress, parent, false)
        return SyncProgressViewHolder(view)
    }

    override fun onBindViewHolder(holder: SyncProgressViewHolder, position: Int) {
        holder.setProgressItem(progressItems[position])
    }

    override fun getItemCount(): Int {
        return progressItems.size
    }

    fun addProgress(progressItem: String) {
        progressItems.add(progressItem)
        notifyDataSetChanged()
    }

    fun clear() {
        progressItems.clear()
        notifyDataSetChanged()
    }

    inner class SyncProgressViewHolder(val binding: ListItemSyncProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel by fragment.viewModels<ListItemSyncProgressViewModel>()

        fun setProgressItem(progressItem: String) {
            viewModel.progressItem = progressItem
        }

        init {
            binding.viewModel = viewModel
        }
    }
}