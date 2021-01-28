package com.delaroystudios.lessons.vo

import com.google.gson.annotations.SerializedName

data class Lesson (
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("name")
    var name: String?,
    @field:SerializedName("icon")
    var icon: String?,
    @field:SerializedName("media_url")
    var media_url: String?,
    @field:SerializedName("subject_id")
    var subject_id: Int?,
    @field:SerializedName("chapter_id")
    var chapter_id: Int?
)
