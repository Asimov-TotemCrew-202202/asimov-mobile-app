package pe.edu.upc.asimov.data.remote.alternatives

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AlternativesInterface {
    @POST("students/{studentId}/examDetail/{question}")
    fun postAlternative(@Path("studentId") studentId: String, @Path("question") question: String, @Body alternative: AlternativePost): Call<Alternative>
}