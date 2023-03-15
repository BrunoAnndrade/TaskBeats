package com.comunidadedevspace.taskbeats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskList:RecyclerView = findViewById(R.id.RecycleView_task_List)

        val list = listOf<String>("title 1", "title 2", "title 3", "title 4")
        val adapter = TaskList_Adapter(list)

        taskList.adapter = adapter



    }
}