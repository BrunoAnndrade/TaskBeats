package com.comunidadedevspace.taskbeats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //kotlin
        val list = listOf<Task>(
            Task("Estudar", "Estudar programação kotlin"),
            Task("Trabalhar", "trabalhando como vendedor"),
            Task("Fun", "Jogando um pouco"),
            Task("Ler", "Ler 20 páginas por dia"),
            Task("Linguagem", "Assistir algo em ingles"),
            Task("meditar", "meditar na playlist"),
        )

        //adapter
        val adapter = TaskList_Adapter(list,::openTaskDetailView)

        //recycleview
        val taskList: RecyclerView = findViewById(R.id.RecycleView_task_List)
        taskList.adapter = adapter
    }

    //Abrir nova página
    fun openTaskDetailView(task: Task) {
        val intent = Intent(this, Activity_Detail::class.java )
            .apply { putExtra(Activity_Detail.TASK_DETAIL_EXTRA, task.title)}
        startActivity(intent)
    }

}




