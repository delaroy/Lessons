package com.delaroystudios.lessons.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.delaroystudios.lessons.db.SubjectData
import com.delaroystudios.lessons.repository.LessonRepository
import com.delaroystudios.lessons.testing.OpenForTesting
import com.delaroystudios.lessons.vo.Resource
import javax.inject.Inject

@OpenForTesting
class SubjectViewModel
    @Inject
    constructor(lessonRepository: LessonRepository) : ViewModel() {
    val repositories: LiveData<Resource<List<SubjectData>>> = lessonRepository.loadLesson()

}