package com.comunidadedevspace.taskbeats

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.TextView

class Activity_Detail : AppCompatActivity() {
    companion object{
        private const val TASK_DETAIL_EXTRA = "task.title.extra.detail"

        fun start(context:Context, task: Task):Intent{
            val intent = Intent(context, Activity_Detail::class.java )
                .apply { putExtra(TASK_DETAIL_EXTRA, task)}

            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        // Recuperate task
        // Essa interrogação significa que a task pode ou não estar ali
        val task:Task? = intent.getSerializableExtra(TASK_DETAIL_EXTRA) as Task?
        // se não passar nenhuma tarefa o app vai dar um crash
        requireNotNull(task)

        // Recuperate textView
        val tvtitle:TextView = findViewById(R.id.tv_TaskTitle_Detail)

        // Setar a nova página na tela
        tvtitle.text = title
    }

    //Menu delete
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_detail, menu)
        return true
    }


}