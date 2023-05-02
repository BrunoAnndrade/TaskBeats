package com.comunidadedevspace.taskbeats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.coroutines.launch
import java.io.Serializable

class TaskListActivity : AppCompatActivity() {
    //kotlin list of task
    //A lista fica aqui para ficar dentro do escopo do startForResult
    private var list = arrayListOf(
        Task(0, "Estudar", "Estudar programação kotlin"),
        Task(1, "Trabalhar", "Trabalhar na loja"),
        Task(2, "Atividade Física", "Praticar alguma atividade física"),
        Task(3, "Ler", "Ler 20 páginas por dia"),
        Task(4, "Linguagem", "Assistir algo em ingles"),
        Task(5, "Alimentação", "melhorar alimentação"),
    )

    //iniciar o layout depois para configurar quando não tiver nenhuma task
    private lateinit var ctnContent: LinearLayout

    //colocando a função de abrir o detalhe da task no adapter
    private val adapter = TaskListAdapter(::onListItemClicked)

    //usando API android para atualizar a página
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            //pegando resultado
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            //so vai acontecer ação de deletar se realmente for um actiontype delete
            if (taskAction.ActionType == ActionType.DELETE.name) {
                //criando nova lista para conseguir tirar um item da lista
                val newList = arrayListOf<Task>()
                    .apply { addAll(list) }

                newList.remove(task)
                showMessage(ctnContent, "Tarefa deletada: ${task.title}")

                //quando a lista estiver vazia aparece a imagem de vazio
                if (newList.size == 0) {
                    ctnContent.visibility = View.VISIBLE
                }

                //atualizando alterações feitas no adapter
                adapter.submitList(newList)
                //atualizando a lista anterior novamente
                list = newList

            } else if (taskAction.ActionType == ActionType.CREATE.name) {
                val newList = arrayListOf<Task>()
                    .apply { addAll(list) }

                newList.add(task)
                showMessage(ctnContent, "Tarefa criada: ${task.title}")
                if (newList.size != 0) {
                    ctnContent.visibility = View.GONE
                }
                adapter.submitList(newList)
                list = newList

            } else if(taskAction.ActionType == ActionType.UPDATE.name) {

                val tempEmptyList = arrayListOf<Task>()
                list.forEach {
                    if(it.id == task.id){
                       val newItem = Task(it.id,task.title,task.Description)
                        tempEmptyList.add(newItem)
                    } else {
                        tempEmptyList.add(it)
                    }
                }
                showMessage(ctnContent, "Tarefa atualizada: ${task.title}")
                adapter.submitList(tempEmptyList)
                list = tempEmptyList
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        val dataBase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "TaskBeats-DataBase"
        ).build()

        val dao = dataBase.taskDao()
        val task = Task(title = "Academia", Description = "treinar 1 hora")

        // isso é porque está na UI e o android não sabe quanto tempo vai levar carregar
        //sendo assim é necessário colocar o coroutineScope
        CoroutineScope(IO).launch {
            dao.insert(task)
        }

        //recuperando layout para quando não tiver nenhuma task
        ctnContent = findViewById(R.id.ctn_content)

        //atualizando lista adapter
        adapter.submitList(list)

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