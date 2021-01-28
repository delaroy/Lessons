package com.delaroystudios.lessons.ui.lessons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.delaroystudios.lessons.db.LessonsData
import com.delaroystudios.lessons.repository.LessonRepository
import com.delaroystudios.lessons.testing.OpenForTesting
import com.delaroystudios.lessons.util.AbsentLiveData
import com.delaroystudios.lessons.vo.Resource
import javax.inject.Inject

@OpenForTesting
class LessonViewModel
@Inject constructor(lessonRepository: LessonRepository) : ViewModel() {
    private val _subjectId = MutableLiveData<Int?>()
    val login: LiveData<Int?>
        get() = _subjectId
    val repositories: LiveData<Resource<List<LessonsData>>> = _subjectId.switchMap { login ->
        if (login == null) {
            AbsentLiveData.create()
        } else {
            lessonRepository.loadLessons(login)
        }
    }

    fun setSubjectId(subjectId: Int?) {
        if (_subjectId.value != subjectId) {
            _subjectId.value = subjectId
        }
    }
}