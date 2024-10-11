package com.example.selfimprove

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class questScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quest_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //linking buttons
        val statButton = findViewById<Button>(R.id.statButton)

        //quest buttons
        val complete1 = findViewById<Button>(R.id.complete1)
        val complete2 = findViewById<Button>(R.id.complete2)
        val complete3 = findViewById<Button>(R.id.complete3)

        var questValue = 100

        //logic to send the user back to the stat screen
        statButton.setOnClickListener {
            finish()
        }

        //test condition for completing quests
        complete1.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("strengthXpEarned", 50)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        complete2.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("dexXpEarned", 75)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        complete3.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("wisXpEarned", 100)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}