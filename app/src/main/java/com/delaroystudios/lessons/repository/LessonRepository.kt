package com.delaroystudios.lessons.repository

import androidx.lifecycle.LiveData
import com.delaroystudios.lessons.AppExecutors
import com.delaroystudios.lessons.api.LessonService
import com.delaroystudios.lessons.db.LessonDao
import com.delaroystudios.lessons.db.LessonsData
import com.delaroystudios.lessons.db.RecentlyWatched
import com.delaroystudios.lessons.db.SubjectData
import com.delaroystudios.lessons.testing.OpenForTesting
import com.delaroystudios.lessons.vo.Lessons
import com.delaroystudios.lessons.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
open class LessonRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val lessonDao: LessonDao,
    private val lessonService: LessonService
)  {
    fun loadLesson(): LiveData<Resource<List<SubjectData>>> {
        return object : NetworkBoundResource<List<SubjectData>, Lessons>(appExecutors) {
            override fun saveCallResult(item: Lessons) {
                val lessonData = item.data?.subjects

                var lessonList: MutableList<LessonsData> = ArrayList()

                for(lesson in lessonData!!) {
                    val subjectId = lesson.id
                    val subjectName = lesson.name
                    val subjectIcon = lesson.icon
                    val subjectData = SubjectData(subjectId, subjectName!!, subjectIcon!!)

                    lessonDao.insertSubject(subjectData)

                    val chapter = lesson.chapters
                    for (mchapter in chapter) {
                        val chapterId = mchapter.id
                        val chapterName = mchapter.name
                        val lesson = mchapter.lessons

                        for (mlesson in lesson) {
                            val lessonId = mlesson.id
                            val lessonName = mlesson.name
                            val lessonIcon = mlesson.icon
                            val lessonMedia = mlesson.media_url
                            val lessonSubjectId = mlesson.subject_id
                            val lessonChapterId = mlesson.chapter_id
                            val lessonsData = LessonsData(subjectId, subjectName!!, subjectIcon!!, chapterId, chapterName!!, lessonId, lessonName!!, lessonIcon!!, lessonMedia!!, lessonSubjectId!!, lessonChapterId!!)

                            lessonList.add(lessonsData)
                        }
                    }
                }
                lessonDao.insertLesson(lessonList)
            }

            override fun shouldFetch(data: List<SubjectData>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = lessonDao.fetchAllSubjects()

            override fun createCall() = lessonService.getLesson()
        }.asLiveData()
    }

    fun loadLessons(subjectId: Int): LiveData<Resource<List<LessonsData>>> {
        return object : NetworkBoundResource<List<LessonsData>, Lessons>(appExecutors) {
            override fun saveCallResult(item: Lessons) {

            }

            override fun shouldFetch(data: List<LessonsData>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = lessonDao.fetchLessons(subjectId)

            override fun createCall() = lessonService.getLesson()
        }.asLiveData()
    }

    fun recentlyWatched(): LiveData<Resource<List<RecentlyWatched>>> {
        return object : NetworkBoundResource<List<RecentlyWatched>, Lessons>(appExecutors) {
            override fun saveCallResult(item: Lessons) {

            }

            override fun shouldFetch(data: List<RecentlyWatched>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = lessonDao.fetchRecentlyWatched()

            override fun createCall() = lessonService.getLesson()
        }.asLiveData()
    }
}


