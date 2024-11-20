package com.example.kotlinlab8

// 聯絡人類別，用來儲存聯絡人資料
class Contact(
    val name: String, // 姓名
    val phone: String // 電話
) {
    // 顯示聯絡人詳細資料的方法
    fun displayInfo() {
        println("聯絡人姓名: $name")
        println("聯絡人電話: $phone")
    }
}

// 使用範例
fun main() {
    // 創建一個聯絡人物件
    val contact = Contact("張三", "0912345678")
    // 顯示聯絡人資料
    contact.displayInfo()
}

