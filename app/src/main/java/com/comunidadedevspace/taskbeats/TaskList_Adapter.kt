package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskList_Adapter (private val openNewTaskDetailView:(task:Task) -> Unit)
    :RecyclerView.Adapter<TaskListViewHolder>() {

    //Essa lista não existe mas vai existir e algum momento
    private var listTask:List<Task> = emptyList()

    fun submit(list:List<Task>){
        listTask = list
        notifyDataSetChanged()
    }

    //criando uma view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return TaskListViewHolder(view)
    }
    //atrelando view-holder com recyclerview
    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {

        holder.bind(listTask[position], openNewTaskDetailView)
    }
    //tamanho da lista
    override fun getItemCount(): Int {
        return listTask.size
    }
}

//recuperando o modelo de view criado no XML
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

        //quando clicar em uma view vai abrir o Activity detail
        TaskView.setOnClickListener{
            openNewTaskDetailView.invoke(task)
        }

    }


}


