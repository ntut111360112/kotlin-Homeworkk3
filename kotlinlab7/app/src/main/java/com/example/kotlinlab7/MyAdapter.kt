package com.example.kotlinlab7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(
    context: Context,
    private val data: List<Item>,
    private val layoutResourceId: Int
) : ArrayAdapter<Item>(context, layoutResourceId, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 利用 LayoutInflater 建立或重用畫面
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutResourceId, parent, false)

        // 取得當前的資料項目
        val currentItem = data[position]

        // 綁定圖片到 ImageView
        val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
        imgPhoto.setImageResource(currentItem.photo)

        // 綁定文字到 TextView
        val tvMsg = view.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = if (layoutResourceId == R.layout.activity_vertical) {
            currentItem.name
        } else {
            "${currentItem.name}: ${currentItem.price}元"
        }

        // 回傳綁定後的 View
        return view
    }
}
