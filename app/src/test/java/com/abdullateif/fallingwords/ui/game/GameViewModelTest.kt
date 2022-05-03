package com.abdullateif.fallingwords.ui.game


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abdullateif.fallingwords.common.Resource
import com.abdullateif.fallingwords.common.UIState
import com.abdullateif.fallingwords.data.model.Question
import com.abdullateif.fallingwords.data.repository.WordsRepository
import com.abdullateif.fallingwords.data.repository.WordsRepositoryImpl
import com.abdullateif.fallingwords.data.source.WordsDataSource
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class GameViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dataSource = mockk<WordsDataSource>()
    private lateinit var wordsRepository: WordsRepository
    lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        wordsRepository = WordsRepositoryImpl(dataSource)
    }

    @Test
    fun `fetch data successfully and update loading state`() = runTest {
        viewModel = GameViewModel(wordsRepository)

        assertThat(viewModel.state.value).isEqualTo(
            GameState(
                uiState = UIState.LOADING,
            )
        )
    }

    @Test
    fun `fetch data successfully and update state with a question and score of 0`() = runTest {
        val mockWordList = MockWordsProvider.getMockWords()

        coEvery { wordsRepository.fetchWords() } returns Resource.Success(data = mockWordList)

        viewModel = GameViewModel(wordsRepository)

        coVerify {
            wordsRepository.fetchWords()
        }

        assertThat(viewModel.state.value!!.score).isEqualTo(0)
        assertThat(viewModel.state.value!!.question).isInstanceOf(Question::class.java)
    }

    @Test
    fun `fetch data successfully and update error state`() = runTest {
        coEvery { wordsRepository.fetchWords() } returns Resource.Error("File not found!")

        viewModel = GameViewModel(wordsRepository)

        coVerify {
            wordsRepository.fetchWords()
        }

        assertThat(viewModel.state.value).isEqualTo(
            GameState(
                uiState = UIState.ERROR,
                message = "File not found!"
            )
        )
    }
}