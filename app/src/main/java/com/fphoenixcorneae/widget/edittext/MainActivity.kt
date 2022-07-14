package com.fphoenixcorneae.widget.edittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fphoenixcorneae.widget.edittext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}