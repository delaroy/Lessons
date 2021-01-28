package com.delaroystudios.lessons.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.delaroystudios.lessons.AppExecutors
import com.delaroystudios.lessons.R
import com.delaroystudios.lessons.databinding.SubjectItemBinding
import com.delaroystudios.lessons.db.SubjectData

class SubjectListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val repoClickCallback: ((SubjectData) -> Unit)?
) : DataBoundListAdapter<SubjectData, SubjectItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<SubjectData>() {
        override fun areItemsTheSame(oldItem: SubjectData, newItem: SubjectData): Boolean {
            return oldItem.subjectId == newItem.subjectId
                    && oldItem.subjectName == newItem.subjectName
        }

        override fun areContentsTheSame(oldItem: SubjectData, newItem: SubjectData): Boolean {
            return oldItem.subjectId == newItem.subjectId
                    && oldItem.subjectName == newItem.subjectName
        }
    }
) {

    override fun createBinding(parent: ViewGroup): SubjectItemBinding {
        val binding = DataBindingUtil.inflate<SubjectItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.subject_item,
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

    override fun bind(binding: SubjectItemBinding, item: SubjectData) {
        binding.subject = item
    }
}