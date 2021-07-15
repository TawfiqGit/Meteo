package com.tawfiq.meteo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.tawfiq.meteo.model.City


private const val DATABASE_NAME= "meteo.db"
private const val DATABASE_VERSION = 1

private const val CITY_TABLE_NAME= "city"
private const val CITY_COLUM_ID= "id"
private const val CITY_COLUM_NAME= "name"

private const val CITY_TABLE_CREATE=
        """CREATE TABLE $CITY_TABLE_NAME ($CITY_COLUM_ID INTEGER PRIMARY KEY, $CITY_COLUM_NAME TEXT)"""

class DatabaseHelper (context : Context) :
        SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VERSION ){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CITY_TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun createCity(city : City): Boolean{
        val value = ContentValues()
        value.put(CITY_COLUM_NAME, city.name)
        Log.i("city_create","value ${value.get(CITY_COLUM_NAME)}")

        val id = writableDatabase.insert(CITY_TABLE_NAME,null,value)
        city.id = id

        return id > 0
    }
}