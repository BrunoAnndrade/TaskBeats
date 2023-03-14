package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskList_Adapter (private val Title:Array<String>):
    RecyclerView.Adapter<TaskListViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Recycle_Adapter.> {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val item = Title[position]
    }

    override fun getItemCount(): Int {
        return Title.size
    }

}

class TaskListViewHolder (view: View) : RecyclerView.ViewHolder(view){

    val Task_Title = view.findViewById<TextView>(R.id.TaskTitle)
    val Task_Description = view.findViewById<TextView>(R.id.TaskDescription)


}


