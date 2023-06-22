package ru.samsung.itacademy.mdev.recycleapp.models

data class FactsModel(
    val factID: String,
    val factDescription: String
){
    constructor(): this("","")
}
