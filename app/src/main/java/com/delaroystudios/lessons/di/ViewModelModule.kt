package com.delaroystudios.lessons.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.delaroystudios.lessons.ui.home.RecentlyWatchedViewModel
import com.delaroystudios.lessons.ui.home.SubjectViewModel
import com.delaroystudios.lessons.ui.lessons.LessonViewModel
import com.delaroystudios.lessons.viewmodel.LessonViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SubjectViewModel::class)
    abstract fun bindUserViewModel(subjectViewModel: SubjectViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LessonViewModel::class)
    abstract fun bindLessonViewModel(lessonViewModel: LessonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecentlyWatchedViewModel::class)
    abstract fun bindRecentlyWatchedViewModel(recentlyWatchedViewModel: RecentlyWatchedViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: LessonViewModelFactory): ViewModelProvider.Factory

}
