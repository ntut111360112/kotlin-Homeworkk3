package com.example.kotlinlab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)

        // 設置系統邊距
        setupSystemInsets()

        // 取得 UI 元件
        val nameInput = findViewById<EditText>(R.id.edName)
        val phoneInput = findViewById<EditText>(R.id.edPhone)
        val sendButton = findViewById<Button>(R.id.btnSend)

        // 設置按鈕點擊事件
        sendButton.setOnClickListener {
            val name = nameInput.text.toString()
            val phone = phoneInput.text.toString()

            // 驗證輸入資料
            when {
                name.isBlank() -> showToast("請輸入姓名")
                phone.isBlank() -> showToast("請輸入電話")
                else -> {
                    // 回傳輸入的資料
                    val resultIntent = Intent().apply {
                        putExtra("name", name)
                        putExtra("phone", phone)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }

    // 設定系統邊距處理
    private fun setupSystemInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // 顯示 Toast 訊息
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
