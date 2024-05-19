package com.example.recipe_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.AdapterView

class MainActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<String>
    private val items = mutableListOf("Asam Laksa", "Chicken Rice", "Cendol", "Nasi Lemak", "Kari Noodle")
    private val recipes = mutableMapOf(
        "Asam Laksa" to "Ingredients and preparation steps for Asam Laksa...",
        "Chicken Rice" to "Ingredients and preparation steps for Chicken Rice...",
        "Cendol" to "Ingredients and preparation steps for Cendol...",
        "Nasi Lemak" to "Ingredients and preparation steps for Nasi Lemak...",
        "Kari Noodle" to "Ingredients and preparation steps for Kari Noodle..."
    )
    private lateinit var recipeListTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recipetypes)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinner = findViewById(R.id.spinner)
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        recipeListTextView = findViewById(R.id.recipe_list)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = spinner.selectedItem.toString()
                recipeListTextView.text = "Recipe: ${recipes[selectedItem]}"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val addButton: Button = findViewById(R.id.add)
        addButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_FOOD)
        }

        val editButton: Button = findViewById(R.id.Edit_main)
        editButton.setOnClickListener {
            val intent = Intent(this, Main3Activity::class.java)
            intent.putExtra("CURRENT_SPINNER_SELECTION", spinner.selectedItem.toString())
            intent.putExtra("RECIPE_LIST", recipeListTextView.text.toString())
            startActivityForResult(intent, REQUEST_CODE_EDIT_FOOD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_FOOD && resultCode == Activity.RESULT_OK) {
            data?.let {
                val newFoodName = it.getStringExtra("NEW_FOOD_NAME")
                val recipe = it.getStringExtra("RECIPE")
                if (!newFoodName.isNullOrEmpty()) {
                    items.add(newFoodName)
                    adapter.notifyDataSetChanged()
                    recipes[newFoodName] = recipe ?: "Default recipe for $newFoodName"
                }
                if (!recipe.isNullOrEmpty()) {
                    recipeListTextView.text = "Recipe: $recipe"
                    Toast.makeText(this, "Recipe: $recipe", Toast.LENGTH_SHORT).show()
                }

                val deleteFoodName = it.getStringExtra("DELETE_FOOD_NAME")
                if (!deleteFoodName.isNullOrEmpty()) {
                    val index = items.indexOf(deleteFoodName)
                    if (index != -1) {
                        items.removeAt(index)
                        recipes.remove(deleteFoodName)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(this, "Item '$deleteFoodName' deleted successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Item '$deleteFoodName' not found in the list", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else if (requestCode == REQUEST_CODE_EDIT_FOOD && resultCode == Activity.RESULT_OK) {
            data?.let {
                val updatedFoodName = it.getStringExtra("UPDATED_FOOD_NAME")
                val updatedRecipe = it.getStringExtra("UPDATED_RECIPE")

                if (!updatedFoodName.isNullOrEmpty()) {
                    val position = items.indexOf(spinner.selectedItem.toString())
                    if (position != -1) {
                        items[position] = updatedFoodName
                        recipes[updatedFoodName] = updatedRecipe ?: "Default recipe for $updatedFoodName"
                        adapter.notifyDataSetChanged()
                    }
                }
                if (!updatedRecipe.isNullOrEmpty()) {
                    recipeListTextView.text = "Recipe: $updatedRecipe"
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_ADD_FOOD = 1
        private const val REQUEST_CODE_EDIT_FOOD = 2
    }
}