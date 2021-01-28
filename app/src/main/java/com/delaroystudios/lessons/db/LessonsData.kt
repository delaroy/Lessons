package com.delaroystudios.lessons.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonsData (
    var subjectId: Int,
    var subjectName: String,
    var subjectIcon: String,
    var chapterId: Int,
    var chapterName: String,
    @PrimaryKey var lessonId: Int,
    var lessonName: String,
    var lessonIcon: String,
    var lessonMediaUrl: String,
    var lessonSubjectId: Int,
    var lessonChapterId: Int
)
