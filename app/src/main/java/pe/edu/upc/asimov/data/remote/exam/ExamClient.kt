package pe.edu.upc.asimov.data.remote.exam

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExamClient {
    private const val API_BASE_URL = "http://10.0.2.2:8080/api/v1/topics/"
    private var examInterface: ExamInterface? = null

    fun build(): ExamInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        examInterface = retrofit.create(ExamInterface::class.java)
        return examInterface as ExamInterface
    }
}