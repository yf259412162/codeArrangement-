package com.example.bmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.annotations.BindPath

@BindPath("B/BActivity")
class BActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
    }
}
