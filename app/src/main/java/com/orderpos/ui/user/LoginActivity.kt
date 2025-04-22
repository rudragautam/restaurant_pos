package com.orderpos.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.orderpos.MainActivity
import com.orderpos.R
import com.orderpos.base.BaseActivity
import com.orderpos.databinding.ActivityLoginBinding
import com.orderpos.databinding.ActivityMainBinding

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            button.setOnClickListener {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }

        }

    }
}