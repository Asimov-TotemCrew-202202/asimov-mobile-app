package pe.edu.upc.asimov.data.remote.scores

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ScoreClient {
    private const val API_BASE_URL = "http://10.0.2.2:8080/api/v1/scores/"
    private var scoreInterface: ScoreInterface? = null

    fun build(): ScoreInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        scoreInterface = retrofit.create(ScoreInterface::class.java)
        return scoreInterface as ScoreInterface
    }
}