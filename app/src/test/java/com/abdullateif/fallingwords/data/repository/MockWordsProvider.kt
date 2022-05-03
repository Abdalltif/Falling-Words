import com.abdullateif.fallingwords.data.model.Word

object MockWordsProvider {
    private fun getMockWord(n: Int = 2) =
        Word(
            textEng = "Hi $n",
            textSpa = "Hola $n"
        )

    fun getMockWords() =
        (1..5).map {
            getMockWord(n = it)
        }
}