package com.abdullateif.fallingwords.data.source

import com.abdullateif.fallingwords.data.model.Word
import com.abdullateif.fallingwords.common.Resource

interface WordsDataSource {
    suspend fun fetchWords() : Resource<List<Word>>
}