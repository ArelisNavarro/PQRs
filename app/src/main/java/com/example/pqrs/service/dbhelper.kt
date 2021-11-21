package com.example.pqrs.service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

 class Dbhelper(
    context: Context,
    name:String,
    version:Int
): SQLiteOpenHelper(
    context,name,null,version
) {
     override fun onCreate(db: SQLiteDatabase) {

         db.execSQL("CREATE TABLE IF NOT EXISTS Usuario(id INTEGER PRIMARY KEY,idRemoto INTEGER, nombre TEXT, apellido TEXT, username TEXT, contrasena INTEGER, token TEXT,rol INTEGER)")

     }

     override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {



     }


 }