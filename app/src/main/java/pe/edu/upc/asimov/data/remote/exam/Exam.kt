package pe.edu.upc.asimov.data.remote.exam

data class Exam(
    val id: String,
    val examDetailResources: List<Question>
)

data class Question(
    val question: String,
    val options: List<String>,
    var selected: String,
    val correctOptionOrder: String
)

data class Alternative(
    val text: String
)

