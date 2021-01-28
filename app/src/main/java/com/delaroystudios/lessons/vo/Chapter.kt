package com.delaroystudios.lessons.vo

import com.google.gson.annotations.SerializedName

data class Chapter (
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("name")
    var name: String?,
    @field:SerializedName("lessons")
    var lessons: List<Lesson>
)
