package com.delaroystudios.lessons.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.delaroystudios.lessons.api.LessonService
import com.delaroystudios.lessons.db.LessonDao
import com.delaroystudios.lessons.db.LessonsData
import com.delaroystudios.lessons.db.SubjectData
import com.delaroystudios.lessons.util.ApiUtil
import com.delaroystudios.lessons.util.InstantAppExecutors
import com.delaroystudios.lessons.util.TestUtil
import com.delaroystudios.lessons.util.mock
import com.delaroystudios.lessons.vo.Resource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class LessonRepositoryTest {
    private val lessonDao = Mockito.mock(LessonDao::class.java)
    private val lessonService = Mockito.mock(LessonService::class.java)
    private val lesson = LessonRepository(InstantAppExecutors(), lessonDao, lessonService)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadLesson() {
        lesson.loadLesson()
        Mockito.verify(lessonDao).fetchAllLessons()
    }
}