package pe.edu.upc.asimov.ui.screens.getScores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.asimov.data.remote.test.ExamScore

@Composable
fun GetScores(studentCode: String, goBack: () -> Unit){
    val exams = arrayOf(
        ExamScore("201910421","Matematica - 1º Competencia", 16),
        ExamScore("201910421","Ingles - 2º Competencia", 18),
        ExamScore("201910421","Historia - 1º Competencia", 18),
        ExamScore("201910225","Matematica - 1º Competencia", 20),
        ExamScore("201910225","Ingles - 2º Competencia", 19),
        ExamScore("201910225","Historia - 1º Competencia", 16),
        ExamScore("201910225","Razonamiento Verbal - 1º Competencia", 16),
        ExamScore("201910045","Matematica - 1º Competencia", 17),
        ExamScore("201910045","Ingles - 2º Competencia", 17),
        ExamScore("201910045","Historia - 1º Competencia", 19),
        ExamScore("201910146","Matematica - 1º Competencia", 19),
        ExamScore("201910146","Ingles - 2º Competencia", 20),
        ExamScore("201910146","Historia - 1º Competencia", 16)
    )
    val studentExams = exams.filter{ it.studentCode == studentCode }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido alumno, Nombre Apellido",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Codigo de alumno: $studentCode",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Tienes las siguientes calificaciones registradas:",
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn{
            items(studentExams){exam ->
                ScoreItem(exam.title, exam.score)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { goBack() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                "Cerrar Sesión",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ScoreItem(examTitle: String, examScore: Int){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Column {
            Text(
                text = "$examTitle:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Calificación: $examScore",
                fontSize = 20.sp
            )
        }
    }
}