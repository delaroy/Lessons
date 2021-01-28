package com.delaroystudios.lessons.ui.home

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.delaroystudios.lessons.AppExecutors
import com.delaroystudios.lessons.R
import com.delaroystudios.lessons.binding.FragmentDataBindingComponent
import com.delaroystudios.lessons.databinding.HomeFragmentBinding
import com.delaroystudios.lessons.di.Injectable
import com.delaroystudios.lessons.ui.common.RecentlyListAdapter
import com.delaroystudios.lessons.ui.common.SubjectListAdapter
import com.delaroystudios.lessons.util.autoCleared
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<HomeFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val params by navArgs<HomeFragmentArgs>()
    private var adapter by autoCleared<SubjectListAdapter>()
    private var madapter by autoCleared<RecentlyListAdapter>()

    private val subjectViewModel: SubjectViewModel by viewModels {
        viewModelFactory
    }

    private val recentlyWatchedViewModel: RecentlyWatchedViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<HomeFragmentBinding>(
            inflater,
            R.layout.home_fragment,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.title = "Hello SImbi"
        binding.args = params
        binding.lifecycleOwner = viewLifecycleOwner
        val rvAdapter = SubjectListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ) { lesson ->
            findNavController().navigate(HomeFragmentDirections.showLesson(lesson.subjectId, lesson.subjectName))
        }

        val rwAdapter = RecentlyListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ) { lesson ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPlayerFragment(lesson.lessonName, lesson.mediaUrl, lesson.subjectName, lesson.id, lesson.chapterName))
        }
        binding.subjectRecyclerview.adapter = rvAdapter
        this.adapter = rvAdapter

        binding.recentlyWatchedRecyclerview.adapter = rwAdapter
        this.madapter = rwAdapter
        initRepoList()
    }

    private fun initRepoList() {
        subjectViewModel.repositories.observe(viewLifecycleOwner, Observer { repos ->
            adapter.submitList(repos?.data)
        })

        recentlyWatchedViewModel.repositories.observe(viewLifecycleOwner, Observer { rep ->
            madapter.submitList(rep?.data)
            GlobalScope.launch(context = Dispatchers.Main) {
                Thread.sleep(4000)
                if (rep.data != null) {
                    if (rep?.data?.size!! < 1) {
                        binding.recent.visibility = View.VISIBLE
                    } else {
                        binding.recent.visibility = View.GONE
                    }
                } else {
                    binding.recent.visibility = View.VISIBLE
                }
            }
        })


        //run excute in background
       //appExecutors.diskIO().execute({ mDb.foodDao().insertFood(foodEntry) })
    }
}