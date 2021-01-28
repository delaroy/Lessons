package com.delaroystudios.lessons.di

import android.app.Application
import androidx.room.Room
import com.delaroystudios.lessons.api.LessonService
import com.delaroystudios.lessons.db.LessonDao
import com.delaroystudios.lessons.db.LessonDb
import com.delaroystudios.lessons.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): LessonService {
        return Retrofit.Builder()
            .baseUrl("https://jackiechanbruteforce.ulesson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(LessonService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application):  LessonDb{
        return Room
            .databaseBuilder(app, LessonDb::class.java, "lesson.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideLessonDao(db: LessonDb): LessonDao {
        return db.lessonDao()
    }
}
