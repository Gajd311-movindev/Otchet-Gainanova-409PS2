package com.krepko.bonuswallet

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PurchaseHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
    }
}