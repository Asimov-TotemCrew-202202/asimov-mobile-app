package pe.edu.upc.asimov.ui.screens.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.upc.asimov.data.remote.exam.Exam
import pe.edu.upc.asimov.data.remote.exam.ExamClient
import pe.edu.upc.asimov.data.remote.user.UserClient
import pe.edu.upc.asimov.data.remote.user.UserLoginResponse
import pe.edu.upc.asimov.ui.screens.getScores.GetScores
import pe.edu.upc.asimov.ui.screens.login.Login
import pe.edu.upc.asimov.ui.screens.score.Score
import pe.edu.upc.asimov.ui.screens.studentHome.StudentHome
import pe.edu.upc.asimov.ui.screens.test.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            val userInterface = UserClient.build()


            Login(goTest = { user ->
                val login = userInterface.login(user)
                login.enqueue(object : Callback<UserLoginResponse> {
                    override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                        Log.d("Debug",response.toString())
                        if (response.isSuccessful) {
                            Log.d("Debug",response.body()!!.toString())
                            navController.navigate("studentHome")
                        }
                    }
                    override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                        Log.d("Error",t.toString())
                    }
                })
            })
        }
        composable("studentHome") {
            val examInterface = ExamClient.build()

            StudentHome(goTest = {examCode ->
                val getExam = examInterface.getExam(examCode)
                getExam.enqueue(object : Callback<Exam> {
                    override fun onResponse(call: Call<Exam>, response: Response<Exam>) {
                        if (response.isSuccessful) {
                            Log.d("Debug",response.body()!!.toString())
                            navController.navigate("test/$examCode")
                        }
                    }
                    override fun onFailure(call: Call<Exam>, t: Throwable) {
                        Log.d("Error",t.toString())
                    }
                })
            }, goScores = {
                navController.navigate("scores/201910421")
            })
        }
        composable("test/{testCode}", arguments = listOf(navArgument("testCode"){ type = NavType.StringType})){
            val testCode = it.arguments?.getString("testCode") as String
            val exam = remember {
                mutableStateOf(Exam("",emptyList()))
            }
            val score = remember {
                mutableStateOf(0)
            }

            val examInterface = ExamClient.build()
            val getExam = examInterface.getExam(testCode)

            val navOptions = NavOptions.Builder()
                .setPopUpTo("test/{testCode}", true)
                .build()

            getExam.enqueue(object : Callback<Exam> {
                override fun onResponse(call: Call<Exam>, response: Response<Exam>) {
                    if (response.isSuccessful) {
                        Log.d("Debug",response.body()!!.toString())
                        exam.value = response.body()!!
                    }
                }
                override fun onFailure(call: Call<Exam>, t: Throwable) {
                    Log.d("Error",t.toString())
                }
            })

            Test(goBack = { questions ->
                questions.forEach { question ->
                    if (question.correctOptionOrder == question.selected) {
                        score.value = score.value + 1
                    }
                }
                navController.navigate("score/${score.value}/${questions.size}", navOptions)
            }, exam = exam.value)
        }
        composable("score/{score}/{questions}", arguments = listOf(navArgument("score"){ type = NavType.StringType}, navArgument("questions"){ type = NavType.StringType})){
            val score = it.arguments?.getString("score") as String
            val questions = it.arguments?.getString("questions") as String
            val navOptions = NavOptions.Builder()
                .setPopUpTo("score/{score}/{questions}", true)
                .setPopUpTo("test/{testCode}", true)
                .build()
            Score(
                goHome = {
                    navController.navigate("studentHome", navOptions)
                },
                score = score,
                questions = questions)
        }
        composable("scores/{studentCode}", arguments = listOf(navArgument("studentCode"){ type = NavType.StringType })){
            val studentCode = it.arguments?.getString("studentCode") as String
            GetScores(studentCode = studentCode, goBack = { navController.navigate("login") })
        }
    }
}