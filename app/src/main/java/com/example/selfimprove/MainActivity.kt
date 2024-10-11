package com.example.selfimprove

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    //exp variables
    var strengthXp = 0
    var smartXp = 0
    var dexXp = 0
    var wisXp = 0
    var chsXp = 0
    var conXp = 0

    //overall level
    lateinit var levelNum: TextView

    //level variables
    var strengthLevel = 0
    var smartLevel = 0
    var dexLevel = 0
    var wisLevel = 0
    var chsLevel = 0
    var conLevel = 0

    //stats level numbers
    lateinit var strengthNum: TextView
    lateinit var smartNum: TextView
    lateinit var dexNum: TextView
    lateinit var wisNum: TextView
    lateinit var chsNum: TextView
    lateinit var conNum: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //overall level text render
        val levelText = findViewById<TextView>(R.id.levelText)

        //stat level text renders
        levelNum = findViewById<TextView>(R.id.levelNum)
        strengthNum = findViewById(R.id.strengthNum)
        smartNum = findViewById(R.id.intNum)
        dexNum = findViewById(R.id.dexNum)
        wisNum = findViewById(R.id.wisNum)
        chsNum = findViewById(R.id.chsNum)
        conNum = findViewById(R.id.conNum)

        //Rendered Progress bars
        val strengthProgress = findViewById<ProgressBar>(R.id.strengthProgress)
        val smartProgress = findViewById<ProgressBar>(R.id.intProgress)
        val dexProgress = findViewById<ProgressBar>(R.id.dexProgress)
        val wisProgress = findViewById<ProgressBar>(R.id.wisProgress)
        val chsProgress = findViewById<ProgressBar>(R.id.chsProgress)
        val conProgress = findViewById<ProgressBar>(R.id.conProgress)

        //Main Buttons
        val questButton = findViewById<Button>(R.id.questButton)

        //placeholder buttons
        val placeholderButton1 = findViewById<Button>(R.id.placehold1)
        val placeholderButton2 = findViewById<Button>(R.id.placehold2)

        placeholderButton1.setOnClickListener {
            increaseStrength(strengthProgress)
            increaseSmart(smartProgress)
            increaseDex(dexProgress)
            increaseWis(wisProgress)
            increaseChs(chsProgress)
            increaseCon(conProgress)
        }
    }

    //ALL INCREASE STAT FUNCTIONS ARE PLACEHOLDER FOR NOW (until the quests screen is done)
    fun increaseStrength(strengthBar: ProgressBar){
            strengthXp += 50
        if (strengthXp <= strengthBar.max) {
            strengthBar.progress += strengthXp
        } else {
            strengthLevel++
            strengthBar.progress = 0
            strengthXp = 0
            strengthNum.text = strengthLevel.toString()
            updateOverallLevel()
        }
    }

    fun increaseSmart(smartBar : ProgressBar){
        smartXp += 25
        if (smartXp <= smartBar.max) {
            smartBar.progress += smartXp
        } else {
            smartLevel++
            smartBar.progress = 0
            smartXp = 0
            smartNum.text = smartLevel.toString()
            updateOverallLevel()
        }
    }

    fun increaseDex(dexBar : ProgressBar){
        dexXp += 33
        if (dexXp <= dexBar.max) {
            dexBar.progress += dexXp
        } else {
            dexLevel++
            dexBar.progress = 0
            dexXp = 0
            dexNum.text = dexLevel.toString()
            updateOverallLevel()
        }
    }

    fun increaseWis(wisBar : ProgressBar){
        wisXp += 33
        if (wisXp <= wisBar.max) {
            wisBar.progress += wisXp
        } else {
            wisLevel++
            wisBar.progress = 0
            wisXp = 0
            wisNum.text = wisLevel.toString()
            updateOverallLevel()
        }
    }

    fun increaseChs(chsBar: ProgressBar){
        chsXp += 33
        if (chsXp <= chsBar.max){
            chsBar.progress += chsXp
        } else {
            chsLevel++
            chsBar.progress = 0
            chsXp = 0
            chsNum.text = chsLevel.toString()
            updateOverallLevel()
        }
    }

    fun increaseCon(conBar: ProgressBar){
        conXp += 35
        if (conXp <= conBar.max){
            conBar.progress += conXp
        } else {
            conLevel++
            conBar.progress = 0
            conXp = 0
            conNum.text = conLevel.toString()
            updateOverallLevel()
        }
    }



    private fun updateOverallLevel(){
        var overallLevel =+ strengthLevel + smartLevel + dexLevel + wisLevel + chsLevel + conLevel
        var displayLevel = overallLevel / 2
        levelNum.text = displayLevel.toString()
    }
}