package com.practicum.playlist_maker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Switch

class SettingsActivity : AppCompatActivity() {
    private lateinit var switchTheme:Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageButton>(R.id.back)
        backButton.setOnClickListener {
            val backIntent = Intent(this, MainActivity::class.java)
            startActivity(backIntent)
        }

        val buttonShare = findViewById<LinearLayout>(R.id.buttonShare)
        buttonShare.setOnClickListener {
            val message = getString(R.string.share_message)
            val shareIntent = Intent(Intent.ACTION_SENDTO).apply{
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)))
        }

        val buttonSupport = findViewById<LinearLayout>(R.id.buttonSupport)
        buttonSupport.setOnClickListener {
            val recipient = getString(R.string.email_recipient) // Замените на ваш адрес электронной почты
            val subject =getString(R.string.message_to_developers)
            val body = getString(R.string.thank_to_developers)

            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$recipient")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }

            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent)
            } else {
                Log.e(getString(R.string.activity_settings_name), getString(R.string.no_email_client_found))
            }
        }
        val buttonTerms = findViewById<LinearLayout>(R.id.buttonTerms)
        buttonTerms.setOnClickListener {
            val termsUrl = getString(R.string.terms_url)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(termsUrl))
            startActivity(browserIntent)
        }

    }
}