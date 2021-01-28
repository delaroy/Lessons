package com.delaroystudios.lessons.util

import com.delaroystudios.lessons.db.LessonsData
import com.delaroystudios.lessons.db.RecentlyWatched
import com.delaroystudios.lessons.db.SubjectData

object TestUtil {

    fun createLesson(subjectId: Int, subjectName: String, subjectIcon: String,
                    chapterId: Int, chapterName : String, lessonId : Int,
                    lessonName: String, lessonIcon: String, lessonMediaUrl: String,
                    lessonSubjectId: Int, lessonChapterId: Int): List<LessonsData> {
        return (0 until subjectId).map {
            createEachLesson(
                subjectId = subjectId,
                subjectName = subjectName,
                subjectIcon = subjectIcon,
                chapterId = chapterId,
                chapterName = chapterName,
                lessonId = lessonId,
                lessonName = lessonName,
                lessonIcon = lessonIcon,
                lessonMediaUrl = lessonMediaUrl,
                lessonSubjectId = lessonSubjectId,
                lessonChapterId = lessonChapterId
            )
        }
    }

    fun createEachLesson(subjectId: Int, subjectName: String, subjectIcon: String,
                        chapterId: Int, chapterName : String, lessonId : Int,
                        lessonName: String, lessonIcon: String, lessonMediaUrl: String,
                        lessonSubjectId: Int, lessonChapterId: Int) = LessonsData(
        subjectId = subjectId,
        subjectName = subjectName,
        subjectIcon = subjectIcon,
        chapterId = chapterId,
        chapterName = chapterName,
        lessonId = lessonId,
        lessonName = lessonName,
        lessonIcon = lessonIcon,
        lessonMediaUrl = lessonMediaUrl,
        lessonSubjectId = lessonSubjectId,
        lessonChapterId = lessonChapterId
    )

    fun createSubject(subjectId: Int, subjectName: String, subjectIcon: String) = SubjectData(
        subjectId = subjectId,
        subjectName = subjectName,
        subjectIcon = subjectIcon
    )

    fun createRecentlyWatched(id: Int, subjectName: String, lessonName: String, mediaUrl: String, chapterName: String) = RecentlyWatched(
        id = id,
        subjectName = subjectName,
        lessonName = lessonName,
        mediaUrl = mediaUrl,
        chapterName = chapterName
    )
}
