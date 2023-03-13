package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskList_Adapter (private val task:Array<String>)
    :RecyclerView.Adapter<TaskList_Adapter.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val task_Title: TextView
        val task_Description: TextView

        init {
            task_Title = view.findViewById(R.id.TaskTitle)
            task_Description = view.findViewById(R.id.TaskDescription)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_task, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        viewHolder.textView.text = dataSet[position]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}


