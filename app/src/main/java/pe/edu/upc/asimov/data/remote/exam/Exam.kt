package pe.edu.upc.asimov.data.remote.exam

data class Exam(
    val id: String,
    val course: String,
    val questions: List<Question>
)

data class Question(
    val question: String,
    val alternatives: List<Alternative>
)

data class Alternative(
    val id: String,
    val text: String,
    val correct: Boolean
)

