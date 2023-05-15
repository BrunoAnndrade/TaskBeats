package com.comunidadedevspace.taskbeats.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.Task
import com.google.android.material.snackbar.Snackbar

class TaskDetailActivity : AppCompatActivity() {
    //Essa task pode existir ou não, por isso o "?" e null
    private var task: Task? = null

    //usando essa view para aparecer uma msg na tela
    private lateinit var btnDone: Button

    //companium pra definir uma compartilhamento entre todas instâncias
    companion object {
        private const val TASK_DETAIL_EXTRA = "task.title.extra.detail"

        //Abrir activity detail quando for chamada essa função
        fun start(context: Context, task: Task?): Intent {
            val intent = Intent(context, TaskDetailActivity::class.java)
                .apply { putExtra(TASK_DETAIL_EXTRA, task) }

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        //para aparecer o toolbar no taskDetail
        setSupportActionBar(findViewById(R.id.toolbar))

        // Recuperando xml
        // se não passar nenhuma tarefa o app vai dar um crash por isso requireNoteNull
        task = intent.getSerializableExtra(TASK_DETAIL_EXTRA) as Task?

        // Recuperando campo xml
        val edtTitle = findViewById<EditText>(R.id.edt_task_title)
        val edtDescription = findViewById<EditText>(R.id.edt_task_description)
        btnDone = findViewById(R.id.btn_done)

        //se tiver um tarefa criada, ao clicar ele vai aparecer a tarefa
        if (task != null) {
            edtTitle.setText(task!!.title)
            edtDescription.setText(task!!.Description)
        }

        btnDone.setOnClickListener {
            val title = edtTitle.text.toString()
            val desc = edtDescription.text.toString()

            if (title.isNotEmpty() && desc.isNotEmpty()) {
                if (task == null) {
                    addOrUpdateTask(0, title, desc, ActionType.CREATE)
                } else {
                    addOrUpdateTask(task!!.id, title, desc, ActionType.UPDATE)
                }
            } else {
                showMessage(it, "Fields are required")
            }
        }


        //  tvTitle = findViewById(R.id.tv_TaskTitleDetail)

        // Setar a nova página na tela
        // esse "?:" se não tiver o titilo da tarefa, vai a msg
        // tvTitle.text = task?.title ?: "Adicione uma tarefa"

    }

    private fun addOrUpdateTask(
        id: Int,
        title: String,
        description: String,
        actionType: ActionType
    ) {
        val newTask = Task(id, title, description)
        returnAction(newTask, actionType)
    }


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
                //So vai passar por esse código se existir uma tarefa
                if (task != null) {
                    //return main activity com o resultado de delete
                    returnAction(task!!, ActionType.DELETE)
                } else {
                    showMessage(btnDone, "Item not found")
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun returnAction(task: Task, actionType: ActionType) {
        val intent = Intent()
            .apply {
                val taskAction = TaskAction(task, actionType.name)
                putExtra("TASK_ACTION_RESULT", taskAction)
            }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    //função para mostrar uma messagem na tela
    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }
}