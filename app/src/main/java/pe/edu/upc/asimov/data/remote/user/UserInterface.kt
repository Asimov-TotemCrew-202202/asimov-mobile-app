package pe.edu.upc.asimov.data.remote.user

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInterface {
    @POST("signin")
    fun login(@Body user: UserLoginResource): Call<UserLoginResponse>
}