package com.tawfiq.meteo.model

data class City(var id: Long, val name : String){

    constructor(name: String) : this(-1,name)
}