package com.practicum.playlist_maker
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.textview.MaterialTextView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarSettings)
        toolbar.setNavigationOnClickListener {
            val backIntent = Intent(this, MainActivity::class.java)
            startActivity(backIntent)
        }

        val buttonShare = findViewById<MaterialTextView>(R.id.buttonShare)
        buttonShare.setOnClickListener {
            val message = getString(R.string.share_message)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)))
        }

        val buttonSupport = findViewById<MaterialTextView>(R.id.buttonSupport)
        buttonSupport.setOnClickListener {
            val recipient = getString(R.string.email_recipient)
            val subject =getString(R.string.message_to_developers)
            val body = getString(R.string.thank_to_developers)

            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }

            startActivity(emailIntent)

        }

        val buttonTerms = findViewById<MaterialTextView>(R.id.buttonTerms)
        buttonTerms.setOnClickListener {
            val termsUrl = getString(R.string.terms_url)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(termsUrl))
            startActivity(browserIntent)
        }

    }
}