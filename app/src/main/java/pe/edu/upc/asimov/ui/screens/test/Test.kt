package pe.edu.upc.asimov.ui.screens.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.asimov.data.remote.exam.Alternative
import pe.edu.upc.asimov.data.remote.exam.Exam
import pe.edu.upc.asimov.data.remote.exam.Question
import pe.edu.upc.asimov.data.remote.test.Answer

@Composable
fun Test(goBack: () -> Unit, exam: Exam){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Test",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(text = exam.course)
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn{
            items(exam.questions){question ->
                Question(question)
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
            Text(text = question.question, fontWeight = FontWeight.Bold)
            for(alternative in question.alternatives){
                Answer(alternative)
            }
        }
    }
}

@Composable
fun Answer(alternative: Alternative){
    val selected = remember {
        mutableStateOf(false)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ){
        RadioButton(selected = selected.value, onClick = { selected.value = !selected.value })
        Text(text = alternative.text)
    }
}