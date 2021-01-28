package com.delaroystudios.lessons.vo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class Lessons (

    @field:SerializedName("data")
    var data: Data? = null
){

    data class Data(
        @field:SerializedName("status")
        var status: String,
        @field:SerializedName("message")
        var message: String?,
        @field:SerializedName("subjects")
        var subjects: List<Subject>
    )
}