package com.delaroystudios.lessons.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.delaroystudios.lessons.util.LiveDataCallAdapterFactory
import com.delaroystudios.lessons.util.getOrAwaitValue
import com.delaroystudios.lessons.vo.Lesson
import com.delaroystudios.lessons.vo.Lessons
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNull
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class LessonServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: LessonService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(LessonService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getLesson() {
        enqueueResponse("lesson.json")
        val value = (service.getLesson().getOrAwaitValue() as ApiSuccessResponse).body


        val request = mockWebServer.takeRequest()
        Assert.assertThat(request.path, CoreMatchers.`is`("/3p/api/content/grade"))

        Assert.assertThat<Lessons>(value, IsNull.notNullValue())
        Assert.assertThat(
            value.data?.subjects!![0].name,
            CoreMatchers.`is`("Mathematics")
        )
        Assert.assertThat(value.data?.subjects!![0].id, CoreMatchers.`is`(2))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}