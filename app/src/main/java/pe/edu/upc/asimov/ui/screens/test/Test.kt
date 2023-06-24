package pe.edu.upc.asimov.ui.screens.test

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import pe.edu.upc.asimov.data.remote.exam.Exam
import pe.edu.upc.asimov.data.remote.exam.Question

@Composable
fun Test(goBack: (Int, Int) -> Unit, exam: Exam){
    val score = remember {
        mutableStateOf(0)
    }
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
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                exam.examDetailResources.forEach { question ->
                    if (question.correctOptionOrder == question.selected) {
                        score.value = score.value + 1
                    }
                }
                goBack(score.value, exam.examDetailResources.size)
                      },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                "Terminar Evaluación",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        LazyColumn{
            itemsIndexed(exam.examDetailResources){index, question ->
                Question(question, onSelectedOption = {optionSelected ->
                    exam.examDetailResources[index].selected = optionSelected.toString()
                })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
@Composable
fun Question(question: Question, onSelectedOption: (Int) -> Unit){
    val selectedOption = remember {
        mutableStateOf(question.selected)
    }
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Column {
            Text(text = question.question, fontWeight = FontWeight.Bold)
            for((index, alternative) in question.options.withIndex()){
                Answer(selectedOption.value, alternative, index, onSelectedOption = {selected ->
                    selectedOption.value = selected.toString()
                    onSelectedOption(selected + 1)
                })
            }
        }
    }
}

@Composable
fun Answer(selected: String?, alternative: String, index: Int, onSelectedOption: (Int) -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ){
        RadioButton(
            selected = selected == index.toString(),
            onClick = {
                onSelectedOption(index)
            }
        )
        Text(text = alternative)
    }
}