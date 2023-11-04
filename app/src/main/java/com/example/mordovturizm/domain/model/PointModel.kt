package com.example.mordovturizm.domain.model

import com.yandex.mapkit.geometry.Point

data class PointModel(
    val coord: Point,
    val name: String,
    val address: String,
    val photoId: List<Int>,
    val description: String,
)
