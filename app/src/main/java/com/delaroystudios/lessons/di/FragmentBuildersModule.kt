package com.delaroystudios.lessons.di


import com.delaroystudios.lessons.ui.home.HomeFragment
import com.delaroystudios.lessons.ui.lessons.LessonsFragment
import com.delaroystudios.lessons.ui.player.PlayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeLessonsFragment(): LessonsFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoFragment(): PlayerFragment

}
