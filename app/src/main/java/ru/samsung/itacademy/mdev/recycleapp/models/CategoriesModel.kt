package ru.samsung.itacademy.mdev.recycleapp.models

data class CategoriesModel(
    val categoryID: String,
    val categoryName: String,
    val categoryDescription1: String,
    val categoryDescription2: String,
    val categoryDescription3: String,
    val categoryImagePath: String
){
    constructor(): this("","","","","","")
}
