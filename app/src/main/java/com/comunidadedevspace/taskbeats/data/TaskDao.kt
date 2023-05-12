package com.comunidadedevspace.taskbeats.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface TaskDao {
    // Se já tiver a tarefa criada ele vai apenas mudar (editar)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)
}