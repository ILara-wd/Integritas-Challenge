package mx.integritas.challenge.remote.model.request


data class ProductRequest(
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String
)
