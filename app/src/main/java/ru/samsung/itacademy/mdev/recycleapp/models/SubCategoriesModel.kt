package ru.samsung.itacademy.mdev.recycleapp.models

data class SubCategoriesModel(
    val categoryInfoID: String,
    val categoryInfoNumber: String,
    val categoryInfoLabel: String,
    val categoryInfoName: String,
    val categoryInfoDescription: String,
    val categoryInfoItems: String,
    val categoryName: String
){
    constructor(): this("","","","","","","")
}
