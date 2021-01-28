package com.delaroystudios.lessons.ui.lessons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delaroystudios.lessons.AppExecutors
import com.delaroystudios.lessons.R
import com.delaroystudios.lessons.db.LessonDao
import com.delaroystudios.lessons.db.LessonsData
import kotlinx.android.synthetic.main.parent_lesson.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LessonParentAdapter(private val parents : List<String>, private val lessonDao: LessonDao) : RecyclerView.Adapter<LessonParentAdapter.ViewHolder>() {
    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_lesson,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return parents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = parents[position]
        holder.textView.text = parent
        val childLayoutManager = LinearLayoutManager(holder.recyclerView.context, RecyclerView.HORIZONTAL, false)
        childLayoutManager.initialPrefetchItemCount = 4
        val items: MutableList<LessonsData> = ArrayList()
        holder.recyclerView.apply {
            GlobalScope.launch(context = Dispatchers.IO) {
                items.addAll(lessonDao.fetch(parent))
            }

            GlobalScope.launch(context = Dispatchers.Main) {
                delay(1000)
                layoutManager = childLayoutManager
                adapter = LessonChildAdapter(
                    items,
                    context
                )
            }

            setRecycledViewPool(viewPool)

        }
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val recyclerView : RecyclerView = itemView.rv_child
        val textView:TextView = itemView.textView
    }
}