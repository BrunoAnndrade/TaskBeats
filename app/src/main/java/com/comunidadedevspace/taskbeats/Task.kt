package com.comunidadedevspace.taskbeats

import java.io.Serializable

data class Task (

    //objetivos são mais fáceis de trabalhar com id
    val id: Int,
    val title:String,
    val Description:String): Serializable {

}
