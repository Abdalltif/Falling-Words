package com.abdullateif.fallingwords.data.source

import android.content.Context
import com.abdullateif.fallingwords.data.model.Word
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException
import javax.inject.Inject
import com.abdullateif.fallingwords.common.Resource

class WordsLocalDataSourceImpl @Inject constructor(
    private val appContext: Context
) : WordsDataSource {
    override suspend fun fetchWords(): Resource<List<Word>> {
        return try {
            val wordsJson = appContext.assets.open("words.json")
                .bufferedReader()
                .use { it.readText() }

            val wordType = object : TypeToken<List<Word>>(){}.type
            val wordList = Gson().fromJson<List<Word>>(wordsJson, wordType)

            Resource.Success(wordList.shuffled().take(10))
        } catch (e: FileNotFoundException){
            Resource.Error("File not Found!")
        }
    }
}