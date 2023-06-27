package pe.edu.upc.asimov.data.remote.test

data class Test(
    val id: String,
    val title: String,
    val questions: List<Question>
)

data class Question(
    val id: String,
    val text: String,
    val options: List<Answer>,
    val correctAnswer: Answer
)

data class Answer(
    val id: String,
    val text: String
)

data class ExamScore(
    val examId: Int,
    val topicName: String,
    val finalScore: Int,
    val questionsCount: Int
)