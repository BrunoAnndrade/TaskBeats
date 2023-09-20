package com.comunidadedevspace.taskbeats.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.Task

class TaskListAdapter(private val openTaskListDetail: (task: Task) -> Unit) :
    ListAdapter<Task, TaskListViewHolder>(TaskListAdapter) {

    //criando uma view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskListViewHolder(view)
    }

    //atrelando view-holder com recyclerview e a função taskDetail
    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, openTaskListDetail)
    }

    //classe auxiliar da biblioteca android para comparar dois objetos equivalentes
    companion object : DiffUtil.ItemCallback<Task>() {

        //comparando todas as tasks
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

        //comparando conteudo para analisar alterações, se algo mudar, ele vai alterar
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.Description == newItem.Description
        }

    }
}

//recuperando o modelo de view criado no XML
class TaskListViewHolder(
    private val TaskView: View
) : RecyclerView.ViewHolder(TaskView) {

    val TaskTitle: TextView = TaskView.findViewById(R.id.tv_TaskTitle)
    val TaskDescription: TextView = TaskView.findViewById(R.id.tv_TaskDescription)

    fun bind(
        task: Task,
        openTaskListDetail: (task: Task) -> Unit
    ) {
        TaskTitle.text = task.title
        TaskDescription.text = task.Description

        //quando clicar em uma view vai abrir o Activity detail
        TaskView.setOnClickListener {
            openTaskListDetail.invoke(task)
        }

    }


}


