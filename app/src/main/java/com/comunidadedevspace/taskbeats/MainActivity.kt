package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
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

    private lateinit var ctnContent: LinearLayout

    private val adapter = TaskList_Adapter(::openTaskDetailView)


    //toda vez que chegar nessa tela, ele vai dar rodar esse resultado
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

            //quando a lista estiver vazia aparece a imagem de vazio
            if(list.size ==0){
                ctnContent.visibility = View.VISIBLE
            }

            //atualizando adapter
            adapter.submit(list)

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recuperando layout para quando não tiver nenhuma task
        ctnContent = findViewById(R.id.ctn_content)

        //adapter
        adapter.submit(list)

        //recyclerview
        val taskList: RecyclerView = findViewById(R.id.RecycleView_task_List)
        taskList.adapter = adapter
    }

    //Open new page
    private fun openTaskDetailView(task: Task) {
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

//a ação variavel vai retornar o valor pra essa pagina
const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"


