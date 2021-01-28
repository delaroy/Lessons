package com.delaroystudios.lessons.db


import androidx.room.Database
import androidx.room.RoomDatabase


/**
 * Main database description.
 */
@Database(
    entities = [LessonsData::class, SubjectData::class, RecentlyWatched::class],
    version = 4,
    exportSchema = false
)
abstract class LessonDb : RoomDatabase() {

    abstract fun lessonDao(): LessonDao

}
