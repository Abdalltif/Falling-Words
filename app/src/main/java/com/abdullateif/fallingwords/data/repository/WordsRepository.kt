package com.abdullateif.fallingwords.data.repository

import com.abdullateif.fallingwords.data.model.Word

interface WordsRepository {
    suspend fun fetchWords() : Result<List<Word>>
}