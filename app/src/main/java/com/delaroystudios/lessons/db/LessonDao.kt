package com.delaroystudios.lessons.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.delaroystudios.lessons.vo.Chapter
import com.delaroystudios.lessons.vo.Lesson

/**
 * Interface for database access on Repo related operations.
 */
@Dao
abstract class LessonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertLesson(subject: MutableList<LessonsData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSubject(subject: SubjectData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRecentlyWatched(subject: RecentlyWatched)

    @Query("SELECT * FROM lessonsdata WHERE subjectId = :subjectId ")
    abstract fun fetchLessons(subjectId: Int): LiveData<List<LessonsData>>

    @Query("SELECT * FROM lessonsdata WHERE chapterName = :chapterName ")
    abstract fun fetch(chapterName: String): List<LessonsData>

    @Query("SELECT * FROM lessonsdata")
    abstract fun fetchAllLessons(): LiveData<LessonsData>

    @Query("SELECT * FROM subjectdata")
    abstract fun fetchAllSubjects(): LiveData<List<SubjectData>>

    @Query("SELECT * FROM recentlywatched")
    abstract fun fetchRecentlyWatched(): LiveData<List<RecentlyWatched>>
}
