package com.example.recipe_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    private lateinit var foodNameEditText: EditText
    private lateinit var recipeEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var deleteButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        foodNameEditText = findViewById(R.id.edit_foodname)
        recipeEditText = findViewById(R.id.edit_recipe)
        submitButton = findViewById(R.id.submit)
        deleteButton = findViewById(R.id.deletelist)
        backButton = findViewById(R.id.back)

        submitButton.setOnClickListener {
            val foodName = foodNameEditText.text.toString()
            val recipe = recipeEditText.text.toString()
            val resultIntent = Intent().apply {
                putExtra("NEW_FOOD_NAME", foodName)
                putExtra("RECIPE", recipe)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        deleteButton.setOnClickListener {
            val foodName = foodNameEditText.text.toString()
            val resultIntent = Intent().apply {
                putExtra("DELETE_FOOD_NAME", foodName)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}