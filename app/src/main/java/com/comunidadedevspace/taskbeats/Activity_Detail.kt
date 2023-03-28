package com.comunidadedevspace.taskbeats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Activity_Detail : AppCompatActivity() {

    companion object{
        val TASK_DETAIL_EXTRA = "task.title.extra.detail"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Recuperar textView
        val tvtitle = findViewById<TextView>(R.id.tv_TaskTitle_Detail)
        // Recuperar string da nova página
        val title:String = intent.getStringExtra(TASK_DETAIL_EXTRA)?: ""
        // Setar a nova página na tela
        tvtitle.text = title
    }
}