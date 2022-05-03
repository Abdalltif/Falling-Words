package com.abdullateif.fallingwords.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullateif.fallingwords.common.Resource
import com.abdullateif.fallingwords.common.UIState
import com.abdullateif.fallingwords.data.model.Question
import com.abdullateif.fallingwords.data.model.Word
import com.abdullateif.fallingwords.data.repository.WordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: WordsRepository
) : ViewModel() {

    private lateinit var currentQuestion: Question
    private var questionIndex: Int = 0
    private val wordsList = ArrayList<Word>()
    private var score = 0

    private val _state = MutableLiveData<GameState>()
    val state: LiveData<GameState> = _state

    init {
        fetchWords()
    }

    fun fetchWords() {
        _state.value = GameState(
            uiState = UIState.LOADING
        )
        viewModelScope.launch(Dispatchers.Default) {
            val result = repository.fetchWords()
            withContext(Dispatchers.Main) {
                _state.value = when (result) {
                    is Resource.Success -> {
                        wordsList.addAll(result.data!!)
                        sendQuestion()
                    }
                    is Resource.Error ->
                         GameState(
                            uiState = UIState.ERROR,
                            message = result.message
                        )
                }
            }
        }
    }

    private fun sendQuestion(): GameState {
        val isSendingCorrectQuestion = Random.nextBoolean()
        val randomIndex = if (wordsList.size > 1)
            (0 until wordsList.size).random()
        else 0

        questionIndex++

        currentQuestion = if (isSendingCorrectQuestion)
            Question(wordsList[questionIndex].textEng, wordsList[questionIndex].textSpa)
        else
            Question(wordsList[questionIndex].textEng, wordsList[randomIndex].textSpa)

        return GameState(
            uiState = UIState.QUESTION,
            question = currentQuestion,
            score = score
        )
    }

    fun noAnswer() {
        _state.value = sendQuestion()
    }
}