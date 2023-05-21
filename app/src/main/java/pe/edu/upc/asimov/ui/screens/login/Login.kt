package pe.edu.upc.asimov.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Login(goTest: (studentCode: String, examCode: String) -> Unit){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val studentCode = remember { mutableStateOf("") }
        val testCode = remember { mutableStateOf("") }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp),
            backgroundColor = Color.Black
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    text = "Asimov",
                    color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "Ingreso a evaluación",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            text = "Código de alumno")
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = studentCode.value,
            onValueChange = { studentCode.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            text = "Código de evaluación")
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = testCode.value,
            onValueChange = { testCode.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { goTest(studentCode.value, testCode.value) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                "Continuar",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}