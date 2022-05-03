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

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: WordsRepository
) : ViewModel() {


    private val _state = MutableLiveData<GameState>()
    val state: LiveData<GameState> = _state

    init {
        fetchWords()
    }

    private fun fetchWords() {
        _state.value = GameState(
            uiState = UIState.LOADING
        )
        viewModelScope.launch(Dispatchers.Default) {
            val result = repository.fetchWords()
            withContext(Dispatchers.Main) {
                _state.value = when (result) {
                    is Resource.Success -> sendQuestion(result.data!!)
                    is Resource.Error ->
                         GameState(
                            uiState = UIState.ERROR,
                            message = result.message
                        )
                }
            }
        }
    }

    private fun sendQuestion(data: List<Word>): GameState {
        return GameState(
            uiState = UIState.QUESTION,
            question = Question(data[0].textEng, data[0].textSpa),
            score = 0
        )
    }
}