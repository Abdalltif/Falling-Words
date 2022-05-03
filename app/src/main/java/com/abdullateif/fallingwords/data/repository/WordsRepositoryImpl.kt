package com.abdullateif.fallingwords.data.repository

import com.abdullateif.fallingwords.common.Resource
import com.abdullateif.fallingwords.data.model.Word
import com.abdullateif.fallingwords.data.source.WordsDataSource
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val dataSource: WordsDataSource
) : WordsRepository {
    override suspend fun fetchWords(): Resource<List<Word>> {
        return dataSource.fetchWords()
    }
}