package com.example.selfimprove

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import java.util.Calendar
import java.util.concurrent.TimeUnit

class questScreen : AppCompatActivity() {


    val questDetails = arrayOf(
        "Complete a workout routine",
        "Cook a healthy meal from scratch.",
        "Practice a sport, or physical activity",
        "Learn a new skill or hobby",
        "Practice a hobby you already have",
        "Complete or make progress on a puzzle (such as a Jigsaw puzzle or rubik's cube)",
        "Write a journal entry and reflect on how you're currently feeling",
        "Meditate for at least 10 minutes",
        "Initiate a conversation with a stranger",
        "Genuinely compliment 3 Strangers",
        "Avoid Processed/Junk food for all 3 of your meals",
        "Take a 5km walk or run",
        "Take a short, cold shower",
        "Catch up with an old friend or family member",
        "Listen to a genre of music you've never listened to before",
        "Speak on the phone with a friend or family member for at least 15 minutes",
        "Watch a documentary on a topic you find interesting",
        "Listen to a podcast on a topic you find interesting",
        "Complete a challenging puzzle (Such as a crossword, or sudoku)",
    )

    //The reward the quest is going to give, used only in backend and logic
    val questReward = arrayOf(
        "strengthXpEarned",
        "conXpEarned",
        "strengthXpEarned",
        "dexXpEarned",
        "dexXpEarned",
        "dexXpEarned",
        "wisXpEarned",
        "wisXpEarned",
        "chsXpEarned",
        "chsXpEarned",
        "conXpEarned",
        "strengthXpEarned",
        "conXpEarned",
        "chsXpEarned",
        "wisXpEarned",
        "chsXpEarned",
        "smartXpEarned",
        "smartXpEarned",
        "smartXpEarned",
    )

    //Displaying the reward of a quest to the user
    val rewardTitle = arrayOf(
        "Strength",
        "Constitution",
        "Strength",
        "Dexterity",
        "Dexterity",
        "Dexterity",
        "Wisdom",
        "Wisdom",
        "Charisma",
        "Charisma",
        "Constitution",
        "Strength",
        "Constitution",
        "Charisma",
        "Wisdom",
        "Charisma",
        "Intelligence",
        "Intelligence",
        "Intelligence"
    )

    private lateinit var complete1: Button
    private lateinit var complete2: Button
    private lateinit var complete3: Button

    private lateinit var completeText1: TextView
    private lateinit var completeText2: TextView
    private lateinit var completeText3: TextView
    @SuppressLint("MissingInflatedId")

    private lateinit var timerTextView: TextView
    private val oneDayMillis = 24 * 60 * 60 * 1000L
    private val halfMinMillis = 30 * 1000L


    fun createRandomQuest(): List<Int> {
        val questId = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9 , 10, 11, 12, 13, 14, 15, 16, 17, 18)
        val selectedIndices = mutableSetOf<Int>()

        while (selectedIndices.size < 3) {
            // Generate a random index within the bounds of the array
            val randomIndex = questId.indices.random()

            // Add the index to the set (it won't add duplicates)
            selectedIndices.add(randomIndex)
        }

        // Return the selected indices as a list
        return selectedIndices.toList()
    }

    private fun resetVisibilityToDefault(sharedPrefs: SharedPreferences) {
        // Quest buttons visible, completion texts invisible
        complete1.isVisible = true
        completeText1.isVisible = false

        complete2.isVisible = true
        completeText2.isVisible = false

        complete3.isVisible = true
        completeText3.isVisible = false

        // Save default visibility state in sharedPrefs
        with(sharedPrefs.edit()) {
            putBoolean("complete1Visible", complete1.isVisible)
            putBoolean("completeText1Visible", completeText1.isVisible)

            putBoolean("complete2Visible", complete2.isVisible)
            putBoolean("completeText2Visible", completeText2.isVisible)

            putBoolean("complete3Visible", complete3.isVisible)
            putBoolean("completeText3Visible", completeText3.isVisible)

            apply()
        }
    }



    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quest_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        timerTextView = findViewById(R.id.timerText)

        //storing daily quests:
        val sharedPrefs: SharedPreferences =
            getSharedPreferences("questPrefs", Context.MODE_PRIVATE)
        val lastGeneratedTime = sharedPrefs.getLong("lastGeneratedTime", 0L)
        val currentTime = Calendar.getInstance().timeInMillis

        val timeSinceLastGeneration = currentTime - lastGeneratedTime
        val timeRemaining = oneDayMillis - timeSinceLastGeneration


        if (timeRemaining > 0) {
            startTimer(timeRemaining)
        } else {
            // Quests have expired, generate new ones
            generateNewQuests(sharedPrefs)
            resetVisibilityToDefault(sharedPrefs)
        }

        var quest1Id: Int
        var quest2Id: Int
        var quest3Id: Int

        if (currentTime - lastGeneratedTime >= oneDayMillis) {

            // More than 24 hours have passed, generate new quests
            val newQuestIds = createRandomQuest()
            quest1Id = newQuestIds[0]
            quest2Id = newQuestIds[1]
            quest3Id = newQuestIds[2]

            // Save new quests and the current timestamp
            with(sharedPrefs.edit()) {
                putInt("quest1Id", quest1Id)
                putInt("quest2Id", quest2Id)
                putInt("quest3Id", quest3Id)
                putLong("lastGeneratedTime", currentTime)
                apply()
            }

        } else {
            // Load previously stored quests
            quest1Id = sharedPrefs.getInt("quest1Id", 0)
            quest2Id = sharedPrefs.getInt("quest2Id", 1)
            quest3Id = sharedPrefs.getInt("quest3Id", 2)
        }

        //linking buttons
        val statButton = findViewById<Button>(R.id.statButton)


        //quest reward text
        val reward1 = findViewById<TextView>(R.id.reward1)
        val reward2 = findViewById<TextView>(R.id.reward2)
        val reward3 = findViewById<TextView>(R.id.reward3)

        //quest details text
        val quest1 = findViewById<TextView>(R.id.quest1)
        val quest2 = findViewById<TextView>(R.id.quest2)
        val quest3 = findViewById<TextView>(R.id.quest3)

        //quest buttons
        complete1 = findViewById<Button>(R.id.complete1)
        complete2 = findViewById<Button>(R.id.complete2)
        complete3 = findViewById<Button>(R.id.complete3)

        //quest completed text
        completeText1 = findViewById<TextView>(R.id.completeText1)
        completeText2 = findViewById<TextView>(R.id.completeText2)
        completeText3 = findViewById<TextView>(R.id.completeText3)

        complete1.isVisible = sharedPrefs.getBoolean("complete1Visible", true)
        completeText1.isVisible = sharedPrefs.getBoolean("completeText1Visible", false)

        complete2.isVisible = sharedPrefs.getBoolean("complete2Visible", true)
        completeText2.isVisible = sharedPrefs.getBoolean("completeText2Visible", false)

        complete3.isVisible = sharedPrefs.getBoolean("complete3Visible", true)
        completeText3.isVisible = sharedPrefs.getBoolean("completeText3Visible", false)


        //changing quests values
        reward1.text = "Reward: 50 ${rewardTitle[quest1Id]} XP"
        quest1.text = questDetails[quest1Id]

        reward2.text = "Reward: 75 ${rewardTitle[quest2Id]} XP"
        quest2.text = questDetails[quest2Id]

        reward3.text = "Reward: 100 ${rewardTitle[quest3Id]} XP"
        quest3.text = questDetails[quest3Id]

        //logic to send the user back to the stat screen
        statButton.setOnClickListener {
            finish()
        }

        //test condition for completing quests
        complete1.setOnClickListener {
            // Flip visibility
            complete1.isVisible = false
            completeText1.isVisible = true

            // Save visibility state
            with(sharedPrefs.edit()) {
                putBoolean("complete1Visible", complete1.isVisible)
                putBoolean("completeText1Visible", completeText1.isVisible)
                apply()
            }

            val resultIntent = Intent()
            resultIntent.putExtra(questReward[quest1Id], 50)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        complete2.setOnClickListener {
            // Flip visibility
            complete2.isVisible = false
            completeText2.isVisible = true

            // Save visibility state
            with(sharedPrefs.edit()) {
                putBoolean("complete2Visible", complete2.isVisible)
                putBoolean("completeText2Visible", completeText2.isVisible)
                apply()
            }
            val resultIntent = Intent()
            resultIntent.putExtra(questReward[quest2Id], 75)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        complete3.setOnClickListener {
            // Flip visibility
            complete3.isVisible = false
            completeText3.isVisible = true

            // Save visibility state
            with(sharedPrefs.edit()) {
                putBoolean("complete3Visible", complete3.isVisible)
                putBoolean("completeText3Visible", completeText3.isVisible)
                apply()
            }
            val resultIntent = Intent()
            resultIntent.putExtra(questReward[quest3Id], 100)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun generateNewQuests(sharedPrefs: SharedPreferences) {
        val newQuestIds = createRandomQuest()

        // Save new quests and the current timestamp
        with(sharedPrefs.edit()) {
            putInt("quest1Id", newQuestIds[0])
            putInt("quest2Id", newQuestIds[1])
            putInt("quest3Id", newQuestIds[2])
            putLong("lastGeneratedTime", System.currentTimeMillis())
            apply()
        }

    }

    private fun startTimer(timeInMillis: Long) {
        object : CountDownTimer(timeInMillis, 1000) {
            @SuppressLint("DefaultLocale")
            override fun onTick(millisUntilFinished: Long) {
                // Update the TextView with the remaining time in hours, minutes, and seconds
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                timerTextView.text = String.format(
                    "Time until quests regenerate: %02d:%02d:%02d",
                    hours, minutes, seconds
                )
            }

            override fun onFinish() {
                // When the timer finishes, regenerate quests and start the process again
                val sharedPrefs = getSharedPreferences("questPrefs", MODE_PRIVATE)
                generateNewQuests(sharedPrefs)
            }
        }.start()
    }
}