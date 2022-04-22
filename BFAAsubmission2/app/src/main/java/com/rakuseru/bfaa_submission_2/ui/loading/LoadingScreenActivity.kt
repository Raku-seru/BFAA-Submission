package com.rakuseru.bfaa_submission_2.ui.loading

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.rakuseru.bfaa_submission_2.databinding.ActivityLoadingScreenBinding
import com.rakuseru.bfaa_submission_2.ui.main.MainActivity

class LoadingScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoadingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        moveToMainActivity()
    }

    private fun moveToMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 1500L)
    }
}