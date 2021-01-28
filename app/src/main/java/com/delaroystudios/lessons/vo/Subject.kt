package com.delaroystudios.lessons.vo

import com.google.gson.annotations.SerializedName

data class Subject (
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("name")
    var name: String?,
    @field:SerializedName("icon")
    var icon: String?,
    @field:SerializedName("chapters")
    var chapters: List<Chapter>
)
