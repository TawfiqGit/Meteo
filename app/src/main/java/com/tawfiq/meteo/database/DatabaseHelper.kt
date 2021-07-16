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

private const val CITY_TABLE_CREATE= """CREATE TABLE $CITY_TABLE_NAME($CITY_COLUM_ID INTEGER PRIMARY KEY,$CITY_COLUM_NAME TEXT)"""

class DatabaseHelper (context : Context) :
        SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VERSION ){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CITY_TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Update version database
    }

    fun createCity(city : City): Boolean{
        val value = ContentValues()
        value.put(CITY_COLUM_NAME, city.name)
        val id = writableDatabase.insert(CITY_TABLE_NAME,null,value)
        city.id = id
        return id > 0
    }

    fun getAllCity(): MutableList<City> {
        val listCity = mutableListOf<City>()
        val query = "SELECT * FROM $CITY_TABLE_NAME"
        readableDatabase.rawQuery(query, null).use {
            cursor ->
            while (cursor.moveToNext()){
                val city = City(
                        cursor.getLong(cursor.getColumnIndex(CITY_COLUM_ID)) ,
                        cursor.getString(cursor.getColumnIndex(CITY_COLUM_NAME)))
                listCity.add(city)
            }
        }
        return listCity
    }

    fun deleteCity(city : City): Boolean{
        val deleteCount = writableDatabase.delete(CITY_TABLE_NAME,"${CITY_COLUM_ID}=?", arrayOf("${city.id}"))
        Log.i("deleteCount","deleteCount $deleteCount")
        return deleteCount == 1
    }
}