package com.example.kotlinlab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val contacts: MutableList<Contact> // 聯絡人列表
) : RecyclerView.Adapter<MyAdapter.ContactViewHolder>() {

    // 自定義 ViewHolder 類別，用於管理項目內的 View
    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        private val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)

        // 將資料綁定到 View
        fun bind(contact: Contact, onDeleteClick: (Contact) -> Unit) {
            tvName.text = contact.name
            tvPhone.text = contact.phone

            // 刪除按鈕點擊事件
            imgDelete.setOnClickListener {
                onDeleteClick(contact)
            }
        }
    }

    // 建立 ViewHolder 與項目佈局
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_row, parent, false)
        return ContactViewHolder(itemView)
    }

    // 獲取列表中項目的數量
    override fun getItemCount(): Int = contacts.size

    // 綁定資料到 ViewHolder
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact) { item ->
            // 刪除項目並更新 RecyclerView
            contacts.remove(item)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, contacts.size)
        }
    }
}
