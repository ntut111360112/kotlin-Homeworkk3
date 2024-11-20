package com.example.kotlinlab7

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 設置系統邊距
        setupSystemInsets()

        // 取得 UI 元件
        val spinner = findViewById<Spinner>(R.id.spinner)
        val listView = findViewById<ListView>(R.id.listView)
        val gridView = findViewById<GridView>(R.id.gridView)

        // 資料初始化
        val countList = generateCountList()
        val itemList = generateItemList()

        // 設置 Spinner 適配器
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            countList
        )

        // 設置 GridView 和 ListView 適配器
        gridView.apply {
            numColumns = 3
            adapter = MyAdapter(this@MainActivity, itemList, R.layout.activity_vertical)
        }

        listView.adapter = MyAdapter(this, itemList, R.layout.activity_horizontal)
    }

    private fun setupSystemInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun generateCountList(): List<String> {
        return (1..10).map { "${it}個" }
    }

    private fun generateItemList(): List<Item> {
        val itemList = mutableListOf<Item>()
        val priceRange = 10..100
        val images = resources.obtainTypedArray(R.array.image_list)

        for (index in 0 until images.length()) {
            val photo = images.getResourceId(index, 0)
            val name = "水果${index + 1}"
            val price = priceRange.random()
            itemList.add(Item(photo, name, price))
        }

        images.recycle()
        return itemList
    }
}
