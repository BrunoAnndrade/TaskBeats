package com.comunidadedevspace.taskbeats

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
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class TaskDetailActivity : AppCompatActivity() {
    //Essa task pode existir ou não, por isso o "?" e null
    private var task: Task? = null


    //usando essa view para aparecer uma msg na tela
    private lateinit var tvTitle:TextView

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

        // Recuperate task
        // Essa interrogação significa que a task pode ou não estar ali
        // se não passar nenhuma tarefa o app vai dar um crash por isso requireNoteNull
        task = intent.getSerializableExtra(TASK_DETAIL_EXTRA) as Task?

        // Recuperando campo xml
        val edtTitle = findViewById<EditText>(R.id.edt_task_title)
        val edtDescription = findViewById<EditText>(R.id.edt_task_description)
        val btnDone = findViewById<Button>(R.id.btn_done)


        tvTitle = findViewById(R.id.tv_TaskTitleDetail)

        // Setar a nova página na tela
        // esse "?:" se não tiver o titilo da tarefa, vai a msg
        tvTitle.text = task?.title ?: "Adicione uma tarefa"

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

                //So vai passar por esse código se existir uma tarefa
                if(task != null) {
                    //return main activity com o resultado de delete
                    val intent = Intent()
                        .apply {
                            val actionType = ActionType.DELETE
                            val taskAction = TaskAction(task!!, actionType)
                            putExtra("TASK_ACTION_RESULT", taskAction)
                        }
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                }else {
                    showMessage(tvTitle, "Item not found")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //função para mostrar uma messagem na tela
    private fun showMessage(view: View, message:String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }
}