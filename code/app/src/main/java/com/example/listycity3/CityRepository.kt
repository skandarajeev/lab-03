package com.example.listycity3
import androidx.compose.runtime.mutableStateListOf

class CityRepository {
    private val _cities = mutableStateListOf(
        City("Edmonton", "AB"),
        City("Vancouver", "BC"),
        City("Toronto", "ON")
    )

    val cities: List<City>
        get() = _cities

    fun add_city(city: City){
        _cities.add(city)

    }

    fun edit_city(index: Int, city:City){
        _cities[index] = city
    }
}