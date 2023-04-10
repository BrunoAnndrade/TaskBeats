package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    //kotlin list of task
    //A lista fica aqui para ficar dentro do escopo do startForResult
    private val list = arrayListOf(
        Task(0,"Estudar", "Estudar programação kotlin"),
        Task(1,"Trabalhar", "trabalhando como vendedor"),
        Task(2,"Fun", "Jogando um pouco"),
        Task(3,"Ler", "Ler 20 páginas por dia"),
        Task(4,"Linguagem", "Assistir algo em ingles"),
        Task(5,"meditar", "meditar na playlist"),
    )


    //toda vez que chegar nessa tela, ele vai dar esse resultado
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result: ActivityResult ->
        if (result.resultCode == RESULT_OK){
            //pegando resultado
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task:Task = taskAction.task

            //Removendo item de lista Kotlin
            list.remove(task)

            //atualizando adapter
            
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //adapter
        val adapter = TaskList_Adapter(list,::openTaskDetailView)

        //recyclerview
        val taskList: RecyclerView = findViewById(R.id.RecycleView_task_List)
        taskList.adapter = adapter
    }

    //Open new page
    fun openTaskDetailView(task: Task) {
        val intent = Activity_Detail.start(this, task)

        startForResult.launch(intent)
    }

}
//Ações serializada (porque vai de uma tela para outra)
sealed class ActionType:Serializable {
    object DELETE:ActionType()
    object UPDATE:ActionType()
    object CREATE:ActionType()
}

data class TaskAction(
    val task: Task,
    val ActionType:ActionType
): Serializable

//a ação vai retornar o valor pra essa pagina
const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"


