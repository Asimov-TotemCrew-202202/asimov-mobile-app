package pe.edu.upc.asimov.data.remote.user

import pe.edu.upc.asimov.data.remote.interceptor.okHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserClient {
    private const val API_BASE_URL = "http://18.220.75.221:8080/api/v1/auth/"
    private var userInterface: UserInterface? = null

    fun build(): UserInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        userInterface = retrofit.create(UserInterface::class.java)
        return userInterface as UserInterface
    }
}