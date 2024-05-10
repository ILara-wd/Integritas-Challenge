package mx.integritas.challenge.remote

import mx.integritas.challenge.remote.model.request.ProductRequest
import mx.integritas.challenge.remote.model.response.ProductResponse
import mx.integritas.challenge.remote.model.response.ProductSimpleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("products/{id}")
    fun getProductById(@Path("id") id: Int): Call<ProductResponse>

    @POST("products/")
    fun createProduct(@Body body: ProductRequest): Call<ProductSimpleResponse>

}