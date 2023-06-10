package pe.edu.upc.asimov.data.remote.user

data class UserLoginResource(
    val username: String,
    val password: String
)

data class UserLoginResponse(
    val username: String,
    val password: String,
    val accessToken: String
)