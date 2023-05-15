package com.comunidadedevspace.taskbeats.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class TaskListActivity : AppCompatActivity() {

    private lateinit var ctnContent: LinearLayout

    //colocando a função de abrir o detalhe da task no adapter
    private val adapter: TaskListAdapter by lazy {
        TaskListAdapter(::onListItemClicked)
    }

    private val viewModel: TaskListViewModel by lazy {
        TaskListViewModel.create(application)
    }

    //usando API android para pegar resultado de outra página
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            //pegando resultado
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction

            viewModel.execute(taskAction)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        //recuperando layout
        ctnContent = findViewById(R.id.ctn_content)

        //recyclerview
        val taskList: RecyclerView = findViewById(R.id.RecycleView_task_List)
        //atrelando xml recycleView + adapter
        taskList.adapter = adapter

        //botão pra add nova task
        val fab: FloatingActionButton = findViewById(R.id.fab_add)
        fab.setOnClickListener {
            //null porque ainda não tem a tarefa criada
            openTaskListDetail(null)
        }
    }

    /* Colocar a inicialização do banco de dados no onStart() ajuda a evitar a exibição de uma
     tela vazia ou com dados ausentes quando a atividade é retomada após estar em segundo plano.*/
    override fun onStart() {
        super.onStart()
        listFromDateBase()
    }

    private fun deleteAll() {
        val taskAction = TaskAction(null, ActionType.DELETE_ALL.name)
        viewModel.execute(taskAction)
    }

    private fun listFromDateBase() {
        //Observer
        val listObserver = Observer<List<Task>> { listTasks ->
            if (listTasks.isEmpty()) {
                ctnContent.visibility = View.VISIBLE
            } else {
                ctnContent.visibility = View.GONE
            }
            adapter.submitList(listTasks)
        }
        //Live Data
        //toda vez que tiver uma alteração na lista , ele vai observar e alterar
        viewModel.taskListLiveData.observe(this@TaskListActivity, listObserver)
    }

    //função para mostrar uma messagem na tela
    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    //Open new page
    private fun onListItemClicked(task: Task) {
        openTaskListDetail(task)
    }

    // essa tarefa pode existir. Então coloquei o null
    // esse Task?=null é um default argument
    private fun openTaskListDetail(task: Task?) {
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_task -> {
                //deletar todas as tarefas
                deleteAll()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}

//CRUD
enum class ActionType {
    DELETE,
    DELETE_ALL,
    UPDATE,
    CREATE,
}

//criando ações para implementar na task
data class TaskAction(
    val task: Task?,
    val ActionType: String
) : Serializable

//a ação variavel vai retornar o valor pra essa pagina
const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"