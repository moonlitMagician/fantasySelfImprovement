package com.example.selfimprove

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class themeScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    private fun showConfirmationDialog(onConfirm: () -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Theme Change")
            .setMessage("Are you sure you want to change the theme?")
            .setPositiveButton("Yes") { _, _ ->
                onConfirm() // Call the provided confirmation action
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog
            }
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_theme_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val statButton = findViewById<Button>(R.id.themeStatButton)
        val sorcererButton = findViewById<Button>(R.id.sorcererButton)
        val adventureButton = findViewById<Button>(R.id.adventurerButton)

        statButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        sorcererButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("sorcererTheme", true)
            // Save the theme state directly to SharedPreferences
            val sharedPreferences = getSharedPreferences("StatData", MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putBoolean("sorcererTheme", true)
                putBoolean("adventureTheme", false)
                apply() // Save asynchronously
            }
            startActivity(intent)
        }

        adventureButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("adventureTheme", true)
            // Save the theme state directly to SharedPreferences
            val sharedPreferences = getSharedPreferences("StatData", MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putBoolean("sorcererTheme", false)
                putBoolean("adventureTheme", true)
                apply() // Save asynchronously
            }
            startActivity(intent)
        }
    }
}