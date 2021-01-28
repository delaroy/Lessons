package com.delaroystudios.lessons.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.delaroystudios.lessons.AppExecutors
import com.delaroystudios.lessons.R
import com.delaroystudios.lessons.db.RecentlyWatched
import com.delaroystudios.lessons.db.SubjectData
import com.delaroystudios.lessons.databinding.RecentlyItemBinding

class RecentlyListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val repoClickCallback: ((RecentlyWatched) -> Unit)?
) : DataBoundListAdapter<RecentlyWatched, RecentlyItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<RecentlyWatched>() {
        override fun areItemsTheSame(oldItem: RecentlyWatched, newItem: RecentlyWatched): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.lessonName == newItem.lessonName
        }

        override fun areContentsTheSame(oldItem: RecentlyWatched, newItem: RecentlyWatched): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.lessonName == newItem.lessonName
        }
    }
) {

    override fun createBinding(parent: ViewGroup): RecentlyItemBinding {
        val binding = DataBindingUtil.inflate<RecentlyItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recently_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.subject?.let {
                repoClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: RecentlyItemBinding, item: RecentlyWatched) {
        binding.subject = item
    }
}