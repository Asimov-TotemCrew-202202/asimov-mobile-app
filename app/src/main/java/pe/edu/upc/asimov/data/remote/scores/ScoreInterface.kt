package pe.edu.upc.asimov.data.remote.scores

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ScoreInterface {
    @GET("students/{studentId}")
    fun getScores(@Path("studentId") studentId: String): Call<List<Score>>
}