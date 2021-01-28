package com.delaroystudios.lessons.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubjectData (
    @PrimaryKey var subjectId: Int,
    var subjectName: String,
    var subjectIcon: String
)
