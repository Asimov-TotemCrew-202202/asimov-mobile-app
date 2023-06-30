package pe.edu.upc.asimov.data.remote.alternatives

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AlternativesClient {
    private const val API_BASE_URL = "http://18.220.75.221:8080/api/v1/alternatives/"
    private var alternativesInterface: AlternativesInterface? = null

    fun build(): AlternativesInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        alternativesInterface = retrofit.create(AlternativesInterface::class.java)
        return alternativesInterface as AlternativesInterface
    }
}