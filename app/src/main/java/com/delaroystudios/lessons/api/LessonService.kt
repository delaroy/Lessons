package com.delaroystudios.lessons.api

import androidx.lifecycle.LiveData
import com.delaroystudios.lessons.vo.Lessons
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * REST API access points
 */
interface LessonService {
    @GET("3p/api/content/grade")
    fun getLesson(): LiveData<ApiResponse<Lessons>>
}
