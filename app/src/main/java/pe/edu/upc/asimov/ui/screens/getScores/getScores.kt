package pe.edu.upc.asimov.ui.screens.getScores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun GetScores(studentCode: String, scores: List<ExamScore>, goBack: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                goBack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Regresar",
                fontSize = 15.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Mis Calificaciones Evaluaciones IA",
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
            items(scores){score ->
                ScoreItem(score.examId, score.topicName, score.finalScore/score.questionsCount)
            }
        }
    }
}

@Composable
fun ScoreItem(examId: Int, examTitle: String, examScore: Int){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Column {
            Row() {
                Text(
                    text = "Id del Examen: ",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = examId.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp)
                )
            }
            Text(
                text = "Topico de la evaluación:",
                fontSize = 20.sp,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = examTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(5.dp)
            )
            Row() {
                Text(
                    text = "Calificación: ",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = examScore.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}