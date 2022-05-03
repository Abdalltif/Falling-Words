package com.abdullateif.fallingwords.ui.game

import com.abdullateif.fallingwords.common.UIState
import com.abdullateif.fallingwords.data.model.Question


data class GameState(
    val uiState: UIState = UIState.LOADING,
    val question: Question? = null,
    val score: Int? = null,
    val message: String? = null
)