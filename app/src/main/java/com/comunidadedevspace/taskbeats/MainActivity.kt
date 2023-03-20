package com.comunidadedevspace.taskbeats

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
            Task("Estudar", "Estudar programação kotlin"),
            Task("Estudar", "Estudar programação kotlin"),
            Task("Estudar", "Estudar programação kotlin"),
            Task("Estudar", "Estudar programação kotlin"),
            Task("Estudar", "Estudar programação kotlin"),
        )

        //adapter
        val adapter = TaskList_Adapter(list)

        //recycleview
        val taskList:RecyclerView = findViewById(R.id.RecycleView_task_List)
        taskList.adapter = adapter







    }
}