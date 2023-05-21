package pe.edu.upc.asimov.data.remote.exam

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ExamInterface {
    @GET("exams/{index}")
    fun getExam(@Path("index") index: String): Call<Exam>
}