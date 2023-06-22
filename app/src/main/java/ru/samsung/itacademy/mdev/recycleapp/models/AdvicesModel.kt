package ru.samsung.itacademy.mdev.recycleapp.models

data class AdvicesModel (
    val adviceID: String,
    val adviceName: String,
    val adviceDescription: String
    ){
    constructor(): this("","","")
}