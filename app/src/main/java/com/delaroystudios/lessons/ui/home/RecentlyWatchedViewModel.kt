package com.delaroystudios.lessons.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.delaroystudios.lessons.db.RecentlyWatched
import com.delaroystudios.lessons.db.SubjectData
import com.delaroystudios.lessons.repository.LessonRepository
import com.delaroystudios.lessons.testing.OpenForTesting
import com.delaroystudios.lessons.vo.Resource
import javax.inject.Inject

@OpenForTesting
class RecentlyWatchedViewModel
@Inject
constructor(lessonRepository: LessonRepository) : ViewModel() {
    val repositories: LiveData<Resource<List<RecentlyWatched>>> = lessonRepository.recentlyWatched()

}