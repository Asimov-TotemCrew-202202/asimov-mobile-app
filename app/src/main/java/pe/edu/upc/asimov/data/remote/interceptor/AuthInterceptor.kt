package pe.edu.upc.asimov.data.remote.interceptor

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class AuthInterceptor(private val authToken: String?): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", this.authToken ?: "No token")
            .build()
        return chain.proceed(authenticatedRequest)
    }
}

fun okHttpClient(token: String?): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(AuthInterceptor(token)).build()
}

val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")

suspend fun saveAuthToken(dataStore: DataStore<Preferences>, token: String) {
    dataStore.edit { preferences ->
        preferences[AUTH_TOKEN_KEY] = token
    }
}

suspend fun clearAuthToken(dataStore: DataStore<Preferences>) {
    dataStore.edit { preferences ->
        preferences.remove(AUTH_TOKEN_KEY)
    }
}

suspend fun getAuthToken(dataStore: DataStore<Preferences>): String? {
    return dataStore.data.first()[AUTH_TOKEN_KEY]
}