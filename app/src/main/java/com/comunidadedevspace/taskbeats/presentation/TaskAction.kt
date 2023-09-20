package com.comunidadedevspace.taskbeats.presentation

import com.comunidadedevspace.taskbeats.data.local.Task
import java.io.Serializable

enum class ActionType {
    DELETE,
    UPDATE,
    CREATE,
}

//criando ações para implementar na task
data class TaskAction(
    val task: Task?,
    val ActionType: String
) : Serializable