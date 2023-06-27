package pe.edu.upc.asimov.data.remote.students

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentInterface {
    @GET("getByUser/{userId}")
    fun getStudentId(@Path("userId") userId: String): Call<Student>
}