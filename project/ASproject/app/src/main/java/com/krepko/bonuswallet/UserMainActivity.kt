package com.krepko.bonuswallet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.krepko.bonuswallet.data.DataManager

class UserMainActivity : AppCompatActivity() {

    private lateinit var tvGreeting: TextView
    private lateinit var tvBalance: TextView
    private lateinit var btnHistory: Button
    private lateinit var btnQR: Button
    private lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)

        tvGreeting = findViewById(R.id.tvGreeting)
        tvBalance = findViewById(R.id.tvBalance)
        btnHistory = findViewById(R.id.btnHistory)
        btnQR = findViewById(R.id.btnQR)

        dataManager = DataManager(this)

        val userId = intent.getIntExtra("user_id", -1)
        val user = dataManager.getUsers().find { it.id == userId }

        if (user != null) {
            tvGreeting.text = "Здравствуйте, ${user.fullName}!"
            tvBalance.text = "${user.bonusBalance} баллов"
        }

        btnHistory.setOnClickListener {
            val intent = Intent(this, PurchaseHistoryActivity::class.java)
            intent.putExtra("user_id", userId)
            startActivity(intent)
        }

        btnQR.setOnClickListener {
            val intent = Intent(this, QRActivity::class.java)
            intent.putExtra("user_id", userId)
            startActivity(intent)
        }
    }
}