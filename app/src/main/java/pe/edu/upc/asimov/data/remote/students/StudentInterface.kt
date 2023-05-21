package pe.edu.upc.asimov.data.remote.students

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentInterface {
    @GET("students/{index}")
    fun getStudent(@Path("index") index: String): Call<Student>
}