package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView

class Activity_Detail : AppCompatActivity() {
    //declarei a task aqui avisando que a tarefa vai ser iniciada depois
    private lateinit var task: Task

    //companium pra definir uma compartilhamento entre todas instâncias
    companion object{
        private const val TASK_DETAIL_EXTRA = "task.title.extra.detail"

        //Abrir activity detail quando for chamada essa função
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
        // se não passar nenhuma tarefa o app vai dar um crash por isso requireNoteNull
        task = requireNotNull(intent.getSerializableExtra(TASK_DETAIL_EXTRA) as Task?)

        // Recuperate textView
        val tvtitle:TextView = findViewById(R.id.tv_TaskTitle_Detail)

        // Setar a nova página na tela
        tvtitle.text = task.title
    }

    //Ciclo de vida da activity
    // inflar meu XML(menu)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_detail, menu)
        return true
    }

    //ação ao clicar em uma opção do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_task -> {
                //return main activity com o resultado de delete
                val intent = Intent()
                    .apply {
                        val actionType = ActionType.DELETE
                        val taskAction = TaskAction(task, actionType)
                        putExtra("TASK_ACTION_RESULT", taskAction)
                    }
                setResult(Activity.RESULT_OK, intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




}