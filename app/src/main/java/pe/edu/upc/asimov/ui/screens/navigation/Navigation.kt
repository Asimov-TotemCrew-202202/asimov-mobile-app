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
import pe.edu.upc.asimov.data.remote.alternatives.Alternative
import pe.edu.upc.asimov.data.remote.alternatives.AlternativePost
import pe.edu.upc.asimov.data.remote.alternatives.AlternativesClient
import pe.edu.upc.asimov.data.remote.exam.Exam
import pe.edu.upc.asimov.data.remote.exam.ExamClient
import pe.edu.upc.asimov.data.remote.scores.Score
import pe.edu.upc.asimov.data.remote.scores.ScoreClient
import pe.edu.upc.asimov.data.remote.students.Student
import pe.edu.upc.asimov.data.remote.students.StudentClient
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
            val studentId = remember {
                mutableStateOf("")
            }
            val userInterface = UserClient.build()
            val studentInterface = StudentClient.build()

            Login(goTest = { user ->
                val login = userInterface.login(user)
                login.enqueue(object : Callback<UserLoginResponse> {
                    override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {
                        if (response.isSuccessful) {
                            val getStudentId = studentInterface.getStudentId(response.body()!!.id)
                            getStudentId.enqueue(object : Callback<Student> {
                                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                                    if (response.isSuccessful) {
                                        studentId.value = response.body()!!.id
                                        Log.d("Debug",response.body()!!.toString())
                                        navController.navigate("studentHome/${studentId.value}")
                                    }
                                }
                                override fun onFailure(call: Call<Student>, t: Throwable) {
                                    Log.d("Error",t.toString())
                                }
                            })

                            Log.d("Debug",response.body()!!.toString())
                        }
                    }
                    override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                        Log.d("Error",t.toString())
                    }
                })
            })
        }
        composable("studentHome/{studentId}", arguments = listOf(navArgument("studentId"){ type = NavType.StringType})) {
            val studentId = it.arguments?.getString("studentId") as String
            val examInterface = ExamClient.build()

            StudentHome(goTest = {examCode ->
                val getExam = examInterface.getExam(examCode)
                getExam.enqueue(object : Callback<Exam> {
                    override fun onResponse(call: Call<Exam>, response: Response<Exam>) {
                        if (response.isSuccessful) {
                            Log.d("Debug",response.body()!!.toString())
                            navController.navigate("test/$examCode/$studentId")
                        }
                    }
                    override fun onFailure(call: Call<Exam>, t: Throwable) {
                        Log.d("Error",t.toString())
                    }
                })
            }, goScores = {
                navController.navigate("scores/$studentId")
            })
        }
        composable("test/{testCode}/{studentId}", arguments = listOf(navArgument("testCode"){ type = NavType.StringType}, navArgument("studentId"){ type = NavType.StringType})){
            val testCode = it.arguments?.getString("testCode") as String
            val studentId = it.arguments?.getString("studentId") as String
            val exam = remember {
                mutableStateOf(Exam("",emptyList()))
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

            val alternativesInterface = AlternativesClient.build()

            Test(goBack = { alternatives ->
                alternatives.alternatives.forEach{alternative ->
                    val postAlternatives = alternativesInterface.postAlternative(studentId, alternative.id, AlternativePost(alternative.selected))
                    postAlternatives.enqueue(object : Callback<Alternative> {
                        override fun onResponse(call: Call<Alternative>, response: Response<Alternative>) {
                            if (response.isSuccessful) {
                                Log.d("Debug",response.body()!!.toString())
                            }
                        }
                        override fun onFailure(call: Call<Alternative>, t: Throwable) {
                            Log.d("Error",t.toString())
                        }
                    })
                }
                navController.navigate("studentHome/$studentId", navOptions)
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
            val studentId = it.arguments?.getString("studentCode") as String
            val scores = remember {
                mutableStateOf<List<Score>?>(null)
            }

            val scoreInterface = ScoreClient.build()
            val getScores = scoreInterface.getScores(studentId)
            getScores.enqueue(object : Callback<List<Score>> {
                override fun onResponse(call: Call<List<Score>>, response: Response<List<Score>>) {
                    if (response.isSuccessful) {
                        Log.d("Debug",response.body()!!.toString())
                        scores.value = response.body()!!
                        val scoresGrouped = scores.value!!.groupBy { score ->
                            score.examId
                        }
                        Log.d("Scores",scoresGrouped.toString())
                    }
                }
                override fun onFailure(call: Call<List<Score>>, t: Throwable) {
                    Log.d("Error",t.toString())
                }
            })

            GetScores(studentCode = studentId, goBack = { navController.navigate("studentHome/$studentId") })
        }
    }
}