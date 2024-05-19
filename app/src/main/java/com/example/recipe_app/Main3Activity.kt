package com.example.recipe_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Main3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton: Button = findViewById(R.id.back3)
        backButton.setOnClickListener {
            finish() // This will close the current activity and return to the previous one
        }

        // Receive data from MainActivity
        val currentSpinnerSelection = intent.getStringExtra("CURRENT_SPINNER_SELECTION")
        val recipeList = intent.getStringExtra("RECIPE_LIST")

        // Set data to the appropriate views
        val editFoodNameEditText: EditText = findViewById(R.id.edit_foodname)
        val editRecipeEditText: EditText = findViewById(R.id.edit_recipe)
        editFoodNameEditText.setText(currentSpinnerSelection)
        editRecipeEditText.setText(recipeList)

        val editButton: Button = findViewById(R.id.Edit_button)
        editButton.setOnClickListener {
            // Get edited values
            val updatedFoodName = editFoodNameEditText.text.toString()
            val updatedRecipe = editRecipeEditText.text.toString()

            // Prepare data to send back to MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("UPDATED_FOOD_NAME", updatedFoodName)
            resultIntent.putExtra("UPDATED_RECIPE", updatedRecipe)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
