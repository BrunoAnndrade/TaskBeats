package com.comunidadedevspace.taskbeats

import android.app.Application
import androidx.room.Room
import com.comunidadedevspace.taskbeats.data.AppDataBase


/*manter uma única instância do banco de dados durante toda
a execução da aplicação e fornecer acesso a ele de maneira centralizada.*/
class TaskBeatsApplication : Application() {

    private lateinit var dataBase: AppDataBase
    override fun onCreate() {
        super.onCreate()

        //biblioteca api
        dataBase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "TaskBeats-DataBase"
        ).build()
    }
    fun getAppDataBase(): AppDataBase {
        return dataBase
    }
}