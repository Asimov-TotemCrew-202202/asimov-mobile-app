package pe.edu.upc.asimov.data.remote.alternatives

data class Alternative(
    val id: String,
    val isCorrect: Boolean
)

data class AlternativePost(
    val checkedAlternative: String
)

data class AlternativeList(
    var alternatives: List<AlternativeData>
)

data class AlternativeData(
    val id: String,
    val selected: String
)