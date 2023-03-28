package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskList_Adapter (
    private val listTask:List<Task>,
    private val openNewTaskDetailView:(task:Task) -> Unit
):
    RecyclerView.Adapter<TaskListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return TaskListViewHolder(view)
    }
    // atrelar meu viewholder com o recycleview
    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {

        holder.bind(listTask[position], openNewTaskDetailView)
    }
    //tamanho da minha lista
    override fun getItemCount(): Int {
        return listTask.size
    }
}
class TaskListViewHolder (
    private val TaskView: View
) : RecyclerView.ViewHolder(TaskView){

    val TaskTitle:TextView= TaskView.findViewById(R.id.tv_TaskTitle)
    val TaskDescription:TextView= TaskView.findViewById(R.id.tv_TaskDescription)

    fun bind (
        task:Task,
        openNewTaskDetailView:(task:Task) -> Unit
    ){
        TaskTitle.text = task.title
        TaskDescription.text = task.Description

        TaskView.setOnClickListener{
            openNewTaskDetailView.invoke(task)
        }

    }


}


