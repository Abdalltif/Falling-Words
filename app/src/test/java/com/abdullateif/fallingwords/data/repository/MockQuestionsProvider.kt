import com.abdullateif.fallingwords.data.model.Question

object MockQuestionsProvider {
    fun getMockQuestion(n: Int = 2) =
        Question(
            word = "Hi $n",
            translation = "Hola $n"
        )

    fun getMockQuestions() =
        (1..5).map {
            getMockQuestion(n = it)
        }
}