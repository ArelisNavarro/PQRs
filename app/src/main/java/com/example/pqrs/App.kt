package com.example.pqrs

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.example.pqrs.service.Dbhelper

class App:Application() {

    lateinit var db: SQLiteDatabase

    override fun onCreate() {
        super.onCreate()

        var dbhelper= Dbhelper(this,"LogIn",2)
        db=dbhelper.writableDatabase
    }

    override fun onTerminate() {
        super.onTerminate()
        db.close()
    }
}