package com.example.selfimprove

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    var overallLevel = 0
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

    //progress bars
    lateinit var strengthProgress: ProgressBar
    lateinit var smartProgress: ProgressBar
    lateinit var dexProgress: ProgressBar
    lateinit var wisProgress: ProgressBar
    lateinit var chsProgress: ProgressBar
    lateinit var conProgress: ProgressBar

    //launchers
    private lateinit var questLauncher: ActivityResultLauncher<Intent>

    @SuppressLint("MissingInflatedId")

    fun saveStatData() {
        val sharedPreferences = getSharedPreferences("StatData", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("strengthXp", strengthXp)
        editor.putInt("strengthLevel", strengthLevel)

        editor.putInt("smartXp", smartXp)
        editor.putInt("smartLevel", smartLevel)

        editor.putInt("dexXp", dexXp)
        editor.putInt("dexLevel", dexLevel)

        editor.putInt("wisXp", wisXp)
        editor.putInt("wisLevel", wisLevel)

        editor.putInt("chsXp", chsXp)
        editor.putInt("chsLevel", chsLevel)

        editor.putInt("conXp", conXp)
        editor.putInt("conLevel", conLevel)

        editor.putInt("overallLevel", overallLevel)

        editor.apply() // Saves changes asynchronously
    }

    fun loadStatData() {
        val sharedPreferences = getSharedPreferences("StatData", MODE_PRIVATE)

        strengthXp = sharedPreferences.getInt("strengthXp", 0)
        strengthLevel = sharedPreferences.getInt("strengthLevel", 0)

        smartXp = sharedPreferences.getInt("smartXp", 0)
        smartLevel = sharedPreferences.getInt("smartLevel", 0)

        dexXp = sharedPreferences.getInt("dexXp", 0)
        dexLevel = sharedPreferences.getInt("dexLevel", 0)

        wisXp = sharedPreferences.getInt("wisXp", 0)
        wisLevel = sharedPreferences.getInt("wisLevel", 0)

        chsXp = sharedPreferences.getInt("chsXp", 0)
        chsLevel = sharedPreferences.getInt("chsLevel", 0)

        conXp = sharedPreferences.getInt("conXp", 0)
        conLevel = sharedPreferences.getInt("conLevel", 0)

        overallLevel = sharedPreferences.getInt("overallLevel", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadStatData()

        questLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                data?.let {

                    //getting xp from each quest type
                    val strengthXpEarned = it.getIntExtra("strengthXpEarned", 0)
                    val dexXpEarned = it.getIntExtra("dexXpEarned", 0)
                    val wisXpEarned = it.getIntExtra("wisXpEarned", 0)
                    val conXpEarned = it.getIntExtra("conXpEarned", 0)
                    val smartXpEarned = it.getIntExtra("smartXpEarned", 0)
                    val chsXpEarned = it.getIntExtra("chsXpEarned", 0)

                    //updating xp values
                    dexXp += dexXpEarned
                    wisXp += wisXpEarned
                    strengthXp += strengthXpEarned
                    conXp += conXpEarned
                    smartXp += smartXpEarned
                    chsXp += chsXpEarned

                    //invoking the methods
                    if (strengthXpEarned > 0) {
                        increaseStrength(strengthProgress)
                    }
                    if (dexXpEarned > 0) {
                        increaseDex(dexProgress)
                    }
                    if (wisXpEarned > 0) {
                        increaseWis(wisProgress)
                    }
                    if (conXpEarned > 0) {
                        increaseStrength(conProgress)
                    }
                    if (smartXpEarned > 0) {
                        increaseDex(smartProgress)
                    }
                    if (chsXpEarned > 0) {
                        increaseWis(chsProgress)
                    }
                }
            }
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

        //render the saved stats
        strengthNum.text = strengthLevel.toString()
        smartNum.text = smartLevel.toString()
        dexNum.text = dexLevel.toString()
        wisNum.text = wisLevel.toString()
        chsNum.text = wisLevel.toString()
        conNum.text = conLevel.toString()
        levelNum.text = (overallLevel / 2).toString()


        //Rendered Progress bars
        strengthProgress = findViewById<ProgressBar>(R.id.strengthProgress)
        smartProgress = findViewById<ProgressBar>(R.id.intProgress)
        dexProgress = findViewById(R.id.dexProgress)
        wisProgress = findViewById<ProgressBar>(R.id.wisProgress)
        chsProgress = findViewById<ProgressBar>(R.id.chsProgress)
        conProgress = findViewById<ProgressBar>(R.id.conProgress)

        smartProgress.progress = smartXp
        strengthProgress.progress = strengthXp
        dexProgress.progress = dexXp
        wisProgress.progress = wisXp
        chsProgress.progress = chsXp
        conProgress.progress = conXp


        //Main Buttons
        val questButton = findViewById<Button>(R.id.questButton)

        //placeholder buttons
        val placeholderButton1 = findViewById<Button>(R.id.placehold1)
        val placeholderButton2 = findViewById<Button>(R.id.placehold2)

        //logic to send the user to the quests screen
        questButton.setOnClickListener {
            val intent = Intent(this, questScreen::class.java)
            questLauncher.launch(intent)
        }

        //testing button for levelling up
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
        if (strengthXp <= strengthBar.max) {
            strengthBar.progress += strengthXp
        } else {
            strengthLevel++
            strengthBar.progress = 0
            strengthXp = 0
            strengthNum.text = strengthLevel.toString()
            updateOverallLevel()
        }
        saveStatData()
    }

    fun increaseSmart(smartBar : ProgressBar){
        if (smartXp <= smartBar.max) {
            smartBar.progress += smartXp
        } else {
            smartLevel++
            smartBar.progress = 0
            smartXp = 0
            smartNum.text = smartLevel.toString()
            updateOverallLevel()
        }
        saveStatData()
    }

    fun increaseDex(dexBar : ProgressBar){
        if (dexXp <= dexBar.max) {
            dexBar.progress += dexXp
        } else {
            dexLevel++
            dexBar.progress = 0
            dexXp = 0
            dexNum.text = dexLevel.toString()
            updateOverallLevel()
        }
        saveStatData()
    }

    fun increaseWis(wisBar : ProgressBar){
        if (wisXp <= wisBar.max) {
            wisBar.progress += wisXp
        } else {
            wisLevel++
            wisBar.progress = 0
            wisXp = 0
            wisNum.text = wisLevel.toString()
            updateOverallLevel()
        }
        saveStatData()
    }

    fun increaseChs(chsBar: ProgressBar){
        if (chsXp <= chsBar.max){
            chsBar.progress += chsXp
        } else {
            chsLevel++
            chsBar.progress = 0
            chsXp = 0
            chsNum.text = chsLevel.toString()
            updateOverallLevel()
        }
        saveStatData()
    }

    fun increaseCon(conBar: ProgressBar){
        if (conXp <= conBar.max){
            conBar.progress += conXp
        } else {
            conLevel++
            conBar.progress = 0
            conXp = 0
            conNum.text = conLevel.toString()
            updateOverallLevel()
        }
        saveStatData()
    }



    private fun updateOverallLevel(){
        overallLevel =+ strengthLevel + smartLevel + dexLevel + wisLevel + chsLevel + conLevel
        var displayLevel = overallLevel / 2
        levelNum.text = displayLevel.toString()
        saveStatData()
    }
}