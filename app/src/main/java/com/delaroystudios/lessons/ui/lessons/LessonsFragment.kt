package com.delaroystudios.lessons.ui.lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delaroystudios.lessons.AppExecutors
import com.delaroystudios.lessons.R
import com.delaroystudios.lessons.binding.FragmentDataBindingComponent
import com.delaroystudios.lessons.databinding.LessonsFragmentBinding
import com.delaroystudios.lessons.db.LessonDao
import com.delaroystudios.lessons.db.LessonsData
import com.delaroystudios.lessons.di.Injectable
import com.delaroystudios.lessons.util.autoCleared
import javax.inject.Inject


class LessonsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var lessonDao: LessonDao

    val lessonViewModel: LessonViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var appExecutors: AppExecutors
    val item = mutableListOf<String>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<LessonsFragmentBinding>()

    private val params by navArgs<LessonsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<LessonsFragmentBinding>(
            inflater,
            R.layout.lessons_fragment,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.show()
        lessonViewModel.setSubjectId(params.id)
        binding.args = params
        (activity as AppCompatActivity).supportActionBar?.title = params.name

        initRepoList()
    }

    private fun toMap(charts: List<LessonsData>): Map<String, MutableList<LessonsData>?> {
        val map: MutableMap<String, MutableList<LessonsData>?> =
            LinkedHashMap()
        for (chart in charts) {
            var value: MutableList<LessonsData>? =
                map.get(chart.chapterName)
            if (value == null) {
                value = ArrayList()
                map.put(chart.chapterName, value)
            }
            value!!.add(chart)
        }
        return map
    }

    private fun initRepoList() {

        lessonViewModel.repositories.observe(viewLifecycleOwner, Observer { repos ->
            val subjData = repos?.data
            
            if (subjData != null) {
                val charts: Map<String, MutableList<LessonsData>?> =
                    toMap(subjData)
                item.clear()
                for (value in charts.keys) {
                    item.add(value)
                }
                val adapter = LessonParentAdapter(item, lessonDao)
                binding.lessonRv.adapter = adapter
            }

            binding.lessonRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        })
    }
}