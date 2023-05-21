package pe.edu.upc.asimov.ui.screens.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.asimov.data.remote.test.Answer
import pe.edu.upc.asimov.data.remote.test.Question
import pe.edu.upc.asimov.data.remote.test.Test

@Composable
fun Test(goBack: () -> Unit){
    val question1 = Question(
        "1",
        "¿Cuánto es la raíz cuadrada de 16?",
        listOf(
            Answer("1", "4"),
            Answer("2", "8"),
            Answer("3", "7"),
            Answer("4", "6")
        ),
        Answer("1", "4")
    )
    val question2 = Question(
        "2",
        "¿Cuánto es 3 x 5?",
        listOf(
            Answer("1", "10"),
            Answer("2", "12"),
            Answer("3", "13"),
            Answer("4", "15")
        ),
        Answer("4","15")
    )
    val questions = listOf(question1, question2)
    val mathTest = Test("1","Examen Parcial - Matemática Básica 1", questions)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "EVALUACIÓN",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(text = mathTest.title)
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn{
            items(mathTest.questions){
                Question(it)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { goBack() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                "Terminar Evaluación",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}
@Composable
fun Question(question: Question){
    Card(
        modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Column {
            Text(text = "Pregunta " + question.id, fontWeight = FontWeight.Bold)
            Text(text = question.text, fontWeight = FontWeight.Bold)
            for(option in question.options){
                Answer(option)
            }
        }
    }
}

@Composable
fun Answer(answer: Answer){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ){
        Checkbox(
            checked = false,
            onCheckedChange = {}
        )
        Text(text = answer.text)
    }
}