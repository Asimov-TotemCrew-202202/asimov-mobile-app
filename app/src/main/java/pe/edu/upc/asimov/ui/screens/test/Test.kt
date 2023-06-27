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
import pe.edu.upc.asimov.data.remote.alternatives.AlternativeData
import pe.edu.upc.asimov.data.remote.alternatives.AlternativeList
import pe.edu.upc.asimov.data.remote.exam.Exam
import pe.edu.upc.asimov.data.remote.exam.Question

@Composable
fun Test(goBack: (AlternativeList) -> Unit, exam: Exam){
    val score = remember {
        mutableStateOf(0)
    }
    val questionIndex = remember {
        mutableStateOf(0)
    }
    val alternativesList = remember {
        mutableStateOf(AlternativeList(emptyList()))
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
                    Log.d("Id", question.id)
                    Log.d("Selected", question.selected)
                    alternativesList.value.alternatives += AlternativeData(question.id, question.selected)
                }
                goBack(alternativesList.value)
                      },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                "Terminar Examen",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        if (questionIndex.value + 1 != exam.examDetailResources.size){
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    questionIndex.value = questionIndex.value+1
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Text(
                    "Next Question",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
        if (exam.examDetailResources.isNotEmpty()) {
            Question(exam.examDetailResources[questionIndex.value], onSelectedOption = {selectedOption ->
                exam.examDetailResources[questionIndex.value].selected = selectedOption.toString()
            })
        }
    }
}
@Composable
fun Question(question: Question, onSelectedOption: (Int) -> Unit){
    val selectedOption = remember {
        mutableStateOf(-1)
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
                    selectedOption.value = selected
                    onSelectedOption(selected + 1)
                })
            }
        }
    }
}

@Composable
fun Answer(selectedOption: Int?, alternative: String, index: Int, onSelectedOption: (Int) -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ){
        RadioButton(
            selected = selectedOption == index,
            onClick = {
                onSelectedOption(index)
            }
        )
        Text(text = alternative)
    }
}