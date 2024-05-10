package mx.integritas.challenge

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.integritas.challenge.remote.ApiClient
import mx.integritas.challenge.remote.model.request.ProductRequest
import mx.integritas.challenge.remote.model.response.ProductResponse
import mx.integritas.challenge.remote.model.response.ProductSimpleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        createProduct()
    }

    private fun createProduct() {
        val body = ProductRequest(
            category = "Electronic",
            description = "Apple Wash Red",
            image = "https://m.media-amazon.com/images/I/71i4wbm-D0L._AC_SL1500_.jpg",
            price = 20.30,
            title = "Apple Wash",
        )
        val call = ApiClient.apiService.createProduct(body)
        call.enqueue(object : Callback<ProductSimpleResponse> {
            override fun onResponse(
                call: Call<ProductSimpleResponse>,
                response: Response<ProductSimpleResponse>
            ) {
                if (response.isSuccessful) {
                    val post = response.body()
                    getProduct(post?.id!!)
                } else {
                    Toast.makeText(
                        this@ProductActivity,
                        "${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ProductSimpleResponse>, t: Throwable) {
                Toast.makeText(this@ProductActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getProduct(postId: Int = 1) {
        val call = ApiClient.apiService.getProductById(postId)
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    val post = response.body()
                    Toast.makeText(this@ProductActivity, post?.category, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this@ProductActivity,
                        "${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(this@ProductActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}
