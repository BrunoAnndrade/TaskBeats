package com.comunidadedevspace.taskbeats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.io.Serializable

class TaskListActivity : AppCompatActivity() {

    //iniciar o layout depois para configurar quando não tiver nenhuma task
    private lateinit var ctnContent: LinearLayout

    //colocando a função de abrir o detalhe da task no adapter
    private val adapter:TaskListAdapter by lazy {
        TaskListAdapter (::onListItemClicked)
    }

    private val dataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "TaskBeats-DataBase"
        ).build()
    }

    private val dao by lazy {
        dataBase.taskDao()
    }



    //usando API android para atualizar a página
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            //pegando resultado
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            //ação do actiontype
            when (taskAction.ActionType){
                ActionType.DELETE.name -> deleteById(task.id)
                ActionType.CREATE.name -> insertIntoDataBase(task)
                ActionType.UPDATE.name -> updateIntoDataBase(task)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        //para aparecer o toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        listFromDateBase()

        //recuperando layout para quando não tiver nenhuma task
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

    private fun insertIntoDataBase(task: Task){
        CoroutineScope(IO).launch {
            dao.insert(task)
            listFromDateBase()
        }
    }

    private fun updateIntoDataBase(task: Task){
        CoroutineScope(IO).launch {
            dao.update(task)
            listFromDateBase()
        }
    }

    private fun deleteAll(){
        CoroutineScope(IO).launch {
            dao.deleteAll()
            listFromDateBase()
        }
    }

    private fun deleteById(id:Int){
        CoroutineScope(IO).launch {
            dao.deleteById(id)
            listFromDateBase()
        }
    }

    private fun listFromDateBase(){
        // isso é porque está na UI e o android não sabe quanto tempo vai levar carregar
        //sendo assim é necessário colocar o coroutineScope
        CoroutineScope(IO).launch {
            val myDataBaseList: List<Task> = dao.getAll()
            adapter.submitList(myDataBaseList)
        }
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
    UPDATE,
    CREATE,
}

//criando ações para implementar na task
data class TaskAction(
    val task: Task,
    val ActionType: String
) : Serializable

//a ação variavel vai retornar o valor pra essa pagina
const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"