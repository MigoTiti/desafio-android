package com.lucasrodrigues.apodnasa.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lucasrodrigues.apodnasa.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater)
    }
}