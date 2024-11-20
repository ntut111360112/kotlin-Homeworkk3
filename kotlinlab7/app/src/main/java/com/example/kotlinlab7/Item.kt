package com.example.kotlinlab7

/**
 * 資料模型類，用於表示物品資訊
 * @param photo 物品的圖片資源 ID
 * @param name 物品的名稱
 * @param price 物品的價格
 */
class Item(
    val photo: Int, // 圖片資源 ID
    val name: String, // 名稱
    val price: Int // 價格
) {
    override fun toString(): String {
        return "Item(name='$name', price=$price, photo=$photo)"
    }
}
