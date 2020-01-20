package com.gespl.bgv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        android.os.Handler().postDelayed(Runnable {
            val intent: Intent = Intent(this,LogInActivity::class.java)
            startActivity(intent)
            finish()

        },2000)
    }
}
