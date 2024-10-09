package com.practicum.playlist_maker
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    private var searchText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val inputEditText = findViewById<EditText>(R.id.searchEditText)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarSearch)
        toolbar.setNavigationOnClickListener {
            val backIntent = Intent(this, MainActivity::class.java)
            startActivity(backIntent)
        }

        // кнопка отчистки запроса
        clearButton.setOnClickListener {
            inputEditText.text.clear()
            clearButton.visibility = View.GONE
            inputEditText.clearFocus()
            hideKeyboard(inputEditText)
        }

        // Восстановить фокус и доступ к полю при клике
        inputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                inputEditText.isEnabled = true
                showKeyboard(inputEditText)
            }
        }

        inputEditText.setOnClickListener {
            if (!inputEditText.isEnabled) {
                inputEditText.isEnabled = true
                inputEditText.requestFocus()
                showKeyboard(inputEditText)
            }
        }

        // отслеживание изменений текста в поисковой строке
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // заглушка
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                searchText = s?.toString()
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    private fun showKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    // спрятать клавиатуру
    private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // видимость кнопки отчистки
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    // сохранение состояния активности, чтобы восстановить текст
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("SEARCH_TEXT", searchText)
    }

    // восстановление состояние активности (можно делать в методе onCreate())
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString("SEARCH_TEXT")
        findViewById<EditText>(R.id.searchEditText).setText(searchText)
    }

}
