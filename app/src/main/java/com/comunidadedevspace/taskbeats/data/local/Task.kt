package com.comunidadedevspace.taskbeats.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Task(

    //primary key é pra deixar id unico
    @PrimaryKey(autoGenerate = true)
    //objetivos são mais fáceis de trabalhar com id
    val id: Int = 0,
    val title: String,
    val Description: String
) : Serializable {

}
