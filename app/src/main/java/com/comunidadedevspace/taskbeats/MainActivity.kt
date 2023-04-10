package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    //toda vez que chegar nessa tela, ele vai dar um resultado
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result: ActivityResult ->
        if (result.resultCode == RESULT_OK){
            println("resultado aqui")
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //kotlin list of task
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

        //recyclerview
        val taskList: RecyclerView = findViewById(R.id.RecycleView_task_List)
        taskList.adapter = adapter
    }

    //Open new page
    fun openTaskDetailView(task: Task) {
        val intent = Activity_Detail.start(this, task)

        startForResult.launch(intent)
    }

}




