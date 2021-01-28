package com.delaroystudios.lessons.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.delaroystudios.lessons.util.TestUtil
import com.delaroystudios.lessons.util.getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LessonDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertLessonAndFetch() {
        val subject = TestUtil.createLesson(1, "foo", "foo",
            3, "foo", 4,
            "foo", "bar", "bar",
            4, 5 )
        db.lessonDao().insertLesson(subject as MutableList<LessonsData>)

        val loaded = db.lessonDao().fetchLessons(subject[0].subjectId).getOrAwaitValue()
        MatcherAssert.assertThat(loaded[0].subjectId, CoreMatchers.`is`(1))
    }

    @Test
    fun insertSubjectAndFetch() {
        val subject = TestUtil.createSubject(1, "foo", "bar")
        db.lessonDao().insertSubject(subject)

        val loaded = db.lessonDao().fetchAllSubjects().getOrAwaitValue()
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded[0].subjectName, CoreMatchers.`is`("foo"))
        MatcherAssert.assertThat(loaded[0].subjectIcon, CoreMatchers.`is`("bar"))
    }

    @Test
    fun insertRecentlyWatchedAndFetch() {
        val subject = TestUtil.createRecentlyWatched(2, "foo", "foo", "bar", "bar")
        db.lessonDao().insertRecentlyWatched(subject)

        val loaded = db.lessonDao().fetchRecentlyWatched().getOrAwaitValue()
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loaded[0].subjectName, CoreMatchers.`is`("foo"))
        MatcherAssert.assertThat(loaded[0].lessonName, CoreMatchers.`is`("foo"))
        MatcherAssert.assertThat(loaded[0].mediaUrl, CoreMatchers.`is`("bar"))
        MatcherAssert.assertThat(loaded[0].chapterName, CoreMatchers.`is`("bar"))
    }
}