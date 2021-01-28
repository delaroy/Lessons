package com.delaroystudios.lessons.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentlyWatched (
    @PrimaryKey var id: Int,
    var subjectName : String,
    var lessonName: String,
    var mediaUrl: String,
    var chapterName: String
)

