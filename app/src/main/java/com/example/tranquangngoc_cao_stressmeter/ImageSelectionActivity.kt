package com.example.tranquangngoc_cao_stressmeter

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ImageSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_selection)

        val selectedImage = findViewById<ImageView>(R.id.selectedImageView)
        selectedImage.scaleType = ImageView.ScaleType.FIT_CENTER
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val submitButton = findViewById<Button>(R.id.submitButton)

        // Get the image resource ID passed from the fragment
        val imageResId = intent.getIntExtra("IMAGE_RES_ID", 0)
        selectedImage.setImageResource(imageResId)

        cancelButton.setOnClickListener {
            finish() // Close this activity and return to the previous screen
        }

        submitButton.setOnClickListener {
            val stressScore = (1..16).random() // Random stress score between 1 and 16
            CsvHelper.saveStressResult(this, stressScore)

            AlertDialog.Builder(this)
                .setMessage("Saved your score to CSV. Closing the app.")
                .setPositiveButton("OK") { _, _ ->
                    finishAffinity() // Close all activities and exit the app
                }
                .show()
        }
    }

}