package com.delaroystudios.lessons.ui.lessons

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.delaroystudios.lessons.R
import com.delaroystudios.lessons.db.LessonsData
import kotlinx.android.synthetic.main.child_lesson.view.*

class LessonChildAdapter(private val children : List<LessonsData>, private val context :  Context) : RecyclerView.Adapter<LessonChildAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.child_lesson,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = children[position]
        holder.textView.text = child.lessonName
        Glide.with(context)
            .load(child.lessonIcon)
            .into(holder.imageView)
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView
        val imageView: ImageView
        var navController: NavController? = null
        init {
            textView  = itemView.child_textview
            imageView = itemView.thumbnail
            itemView.setOnClickListener {
                Toast.makeText(context,  children.get(adapterPosition).lessonName, Toast.LENGTH_LONG).show()

                navController = findNavController(itemView)
                navController!!.navigate(LessonsFragmentDirections.actionLessonsFragmentToPlayerFragment(children.get(adapterPosition).lessonName, children.get(adapterPosition).lessonMediaUrl, children.get(adapterPosition).subjectName, children.get(adapterPosition).lessonId, children.get(adapterPosition).chapterName))
            }
        }
    }
}