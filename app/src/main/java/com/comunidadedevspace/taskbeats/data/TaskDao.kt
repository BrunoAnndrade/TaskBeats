package com.comunidadedevspace.taskbeats.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    // Se já tiver a tarefa criada ele vai apenas mudar (editar)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Query("Select * from task")
    fun getAllLiveData(): LiveData<List<Task>>

    //UPDATE NECESSARIO ENCONTRAR A TAREFA

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)

    //deleta todos
    @Query("delete from task")
    fun deleteAll()

    //deleta um item
    @Query("delete from task WHERE id = :id")
    fun deleteById(id: Int)


}