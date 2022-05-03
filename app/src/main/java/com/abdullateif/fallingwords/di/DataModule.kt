package com.abdullateif.fallingwords.di

import android.content.Context
import com.abdullateif.fallingwords.data.repository.WordsRepository
import com.abdullateif.fallingwords.data.repository.WordsRepositoryImpl
import com.abdullateif.fallingwords.data.source.WordsDataSource
import com.abdullateif.fallingwords.data.source.WordsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideWordsDataSource(
        @ApplicationContext appContext: Context
    ) : WordsDataSource = WordsLocalDataSourceImpl(appContext)

    @Provides
    fun provideWordsRepository(
        dataSource: WordsDataSource
    ): WordsRepository {
        return WordsRepositoryImpl(dataSource)
    }
}