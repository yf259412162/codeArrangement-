package com.example.amodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.annotations.BindPath


@BindPath("A/AActivity")
class AActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
    }
}
