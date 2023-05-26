package pe.edu.upc.asimov.data.remote.students

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StudentClient {
    private const val API_BASE_URL = "https://asimov-mockend-production-8a10.up.railway.app/"
    private var studentInterface: StudentInterface? = null

    fun build(): StudentInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        studentInterface = retrofit.create(StudentInterface::class.java)
        return studentInterface as StudentInterface
    }
}