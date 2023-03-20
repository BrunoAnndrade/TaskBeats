package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskList_Adapter (private val listTask:List<Task>):
    RecyclerView.Adapter<TaskListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return TaskListViewHolder(view)
    }
    // atrelar meu viewholder com o recycleview
    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = listTask[position]

        holder.bind(task)
    }
    //tamanho da minha lista
    override fun getItemCount(): Int {
        return listTask.size
    }
}

class TaskListViewHolder (view: View) : RecyclerView.ViewHolder(view){

    val Task_Title = view.findViewById<TextView>(R.id.TaskTitle)
    val Task_Description = view.findViewById<TextView>(R.id.TaskDescription)

    fun bind (task:Task){
        Task_Title.text = task.title
        Task_Description.text = task.Description

    }


}


