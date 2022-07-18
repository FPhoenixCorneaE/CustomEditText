package com.fphoenixcorneae.widget.edittext

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.fphoenixcorneae.widget.edittext.databinding.ActivityMainBinding
import com.fphoenixcorneae.widget.edittext.prefix.PrefixEditText
import com.fphoenixcorneae.widget.edittext.suffix.SuffixEditText

class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { mViewBinding = it }.root)

        mViewBinding.apply {
            onPrefixTextChanged = object : PrefixEditText.OnTextChanged {
                override fun onTextChanged(text: CharSequence?) {
                    Log.d("Prefix", "setOnTextChanged: $text")
                }
            }
            onSuffixTextChanged = object : SuffixEditText.OnTextChanged {
                override fun onTextChanged(text: CharSequence?) {
                    Log.d("Suffix", "setOnTextChanged: $text")
                }
            }
            // 只允许输入汉字
            regex = listOf("[\u4e00-\u9fa5]+")
        }
    }
}