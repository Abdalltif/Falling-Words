package com.abdullateif.fallingwords.ui.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.abdullateif.fallingwords.R
import com.abdullateif.fallingwords.common.UIState
import com.abdullateif.fallingwords.databinding.ActivityGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupObservers()

        binding.btnCorrect.setOnClickListener {
            cancelFallingAnimation()
            viewModel.correctClicked()
        }
        binding.btnWrong.setOnClickListener {
            cancelFallingAnimation()
            viewModel.wrongClicked()
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { gameState ->
            when (gameState.uiState) {
                UIState.LOADING -> showLoading()
                UIState.QUESTION -> startFallingAnimation(gameState)
                UIState.ERROR -> showErrorDialog(gameState.message)
                UIState.GAME_OVER -> showGameOverDialog(gameState)
            }
        }
    }

    private fun startFallingAnimation(gameState: GameState) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fall_down)
        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                cancelFallingAnimation()
                viewModel.noAnswer()
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        binding.circularProgressbar.visibility = View.GONE
        binding.tvTranslation.text = gameState.question!!.translation
        binding.tvWord.text = gameState.question.word
        binding.tvScore.text = gameState.score.toString()
        binding.tvTranslation.startAnimation(animation)
    }

    private fun cancelFallingAnimation() {
        binding.tvTranslation.text = ""
        binding.tvWord.text = ""
        binding.tvTranslation.animation?.setAnimationListener(null)
        binding.tvTranslation.clearAnimation()
    }

    private fun showErrorDialog(message: String?) {
        binding.circularProgressbar.visibility = View.GONE
        AlertDialog.Builder(this).apply {
            setTitle(message)
            setCancelable(false)
            setPositiveButton(getString(R.string.try_again)) { _, _ ->
                viewModel.fetchWords()
            }
            setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitProcess(-1)
            }
            show()
        }
    }

    private fun showLoading () {
        binding.circularProgressbar.visibility = View.VISIBLE
    }

    private fun showGameOverDialog(gameState: GameState) {
        binding.tvScore.text = gameState.score.toString()
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.game_over))
            setMessage("Your score: ${gameState.score}")
            setCancelable(false)
            setPositiveButton(getString(R.string.play_again)) { _, _ ->
                viewModel.playAgain()
            }
            setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitProcess(-1)
            }
            show()
        }
    }
}