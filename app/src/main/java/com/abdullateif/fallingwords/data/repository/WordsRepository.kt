package com.abdullateif.fallingwords.data.repository

import com.abdullateif.fallingwords.common.Resource
import com.abdullateif.fallingwords.data.model.Word

interface WordsRepository {
    suspend fun fetchWords() : Resource<List<Word>>
}