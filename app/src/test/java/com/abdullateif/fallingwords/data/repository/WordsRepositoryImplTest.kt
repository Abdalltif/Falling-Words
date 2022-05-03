package com.abdullateif.fallingwords.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abdullateif.fallingwords.common.Resource
import com.abdullateif.fallingwords.data.source.WordsDataSource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
class WordsRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dataSource = mockk<WordsDataSource>()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `fetch data successfully by mock data`() = runTest {
        val mockWordList = MockWordsProvider.getMockWords()

        coEvery { dataSource.fetchWords() } returns Resource.Success(data = mockWordList)

        val result = dataSource.fetchWords()

        coVerify { dataSource.fetchWords() }

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data).isNotNull()
    }

    @Test
    fun `fetch data with exception`() = runTest {
        coEvery { dataSource.fetchWords() } returns Resource.Error(message = "error")

        val result = dataSource.fetchWords()

        coVerify { dataSource.fetchWords() }

        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat(result.data).isNull()
    }
}