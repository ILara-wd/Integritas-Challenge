package mx.integritas.challenge.remote.model.response

import mx.integritas.challenge.remote.model.Rating

data class ProductResponse(
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val rating: Rating,
    val id: Int,
    val title: String
)

data class ProductSimpleResponse(val id: Int)
