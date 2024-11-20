package com.example.kotlinlab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // 延遲初始化的適配器，用於 RecyclerView
    private lateinit var myAdapter: MyAdapter

    // 用於儲存聯絡人資料的列表
    private val contacts = mutableListOf<Contact>()

    // ActivityResultLauncher，處理 SecActivity 的回傳結果
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    val name = intent.getStringExtra("name") ?: ""
                    val phone = intent.getStringExtra("phone") ?: ""
                    // 將回傳的聯絡人資料新增到列表中
                    contacts.add(Contact(name, phone))
                    // 通知適配器更新資料
                    myAdapter.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 設置系統邊距以支援 Edge-to-Edge
        setupSystemInsets()

        // 初始化 RecyclerView 和按鈕元件
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        // 設置 RecyclerView 的佈局管理器為垂直方向
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        // 初始化 RecyclerView 的適配器
        myAdapter = MyAdapter(contacts)
        recyclerView.adapter = myAdapter

        // 設置按鈕點擊事件，啟動 SecActivity
        btnAdd.setOnClickListener {
            val intent = Intent(this, SecActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private fun setupSystemInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
