package com.mydigitalschoolendrick.myapplicationmuseum.data

data class Records(
    val records: ArrayList<Fields>,
)

data class Fields(
    val fields: FieldContent,
)

data class FieldContent(
    val latitude: Float,
    val longitude: Float,
    val nomoff: String,
    val adrl1_m: String,
    val hist: String,
)
