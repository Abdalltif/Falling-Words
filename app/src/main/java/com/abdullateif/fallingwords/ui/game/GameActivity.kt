package com.abdullateif.fallingwords.ui.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.abdullateif.fallingwords.R
import com.abdullateif.fallingwords.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun startFallingAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fall_down)
        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                cancelFallingAnimation()
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        binding.tvTranslation.startAnimation(animation)
    }

    private fun cancelFallingAnimation() {
        binding.tvTranslation.animation?.setAnimationListener(null)
        binding.tvTranslation.clearAnimation()
    }
}