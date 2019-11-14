package com.socu.enpit.sssforshop

import com.nifcloud.mbaas.core.NCMBObject
import com.nifcloud.mbaas.core.NCMBQuery

object CloudDataManager {
    // 全店舗共通のクラス
    private const val CLASS_SHARE_SHOP_INFO = "ShopInfo"

    // 店舗ごとに存在するクラス
    private const val CLASS_EACH_NEWS = "_News"
    private const val CLASS_EACH_GROCERIES = "_Groceries"
    private const val CLASS_EACH_REQUEST = "_Request"

    // データの列
    private const val KEY_USER_NAME: String = "userName"
    private const val KEY_SHOP_NAME: String = "shopName"
    private const val KEY_TITLE: String = "title"
    private const val KEY_CONTENTS: String = "contents"
    private const val KEY_IMAGE_NAME: String = "imageName"

    private var accountUserName: String? = null

    private class KeyAndData constructor(k: String, d:String) {
        val key: String = k
        val data: String = d
    }

    fun setAccountUserName(name: String) {
        accountUserName = name
    }
    private fun getStringData(className: String, findKads: List<KeyAndData>, getKey: String): String? {
        val query = NCMBQuery<NCMBObject>(className)
        for (kad in findKads) query.whereEqualTo(kad.key, kad.data)
        query.setLimit(1)
        val list: List<NCMBObject> = query.find()
        if (list.isEmpty()) {
            return null
        }
        val obj = list[0]
        return obj.getString(getKey)
    }
    private fun addStringData(className: String, insertKads: List<KeyAndData>) {
        val obj = NCMBObject(className)
        for (kad in insertKads)  obj.put(kad.key, kad.data)
        obj.saveInBackground { e ->
            if (e != null) {
                // 保存に失敗した場合の処理
            } else {
                // 保存に成功した場合の処理
            }
        }
    }
    private fun overwriteStringData(className: String, findKads: List<KeyAndData>, insertKads: List<KeyAndData>): Boolean {
        val query = NCMBQuery<NCMBObject>(className)
        for (kad in findKads) query.whereEqualTo(kad.key, kad.data)
       query.setLimit(1)
        val list: List<NCMBObject> = query.find()
        if (list.isEmpty()) {
            return false
        }
        val obj = list[0]
        for (kad in insertKads) obj.put(kad.key, kad.data)
        obj.saveInBackground { e ->
            if (e != null) {
                // 保存に失敗した場合の処理
            } else {
                // 保存に成功した場合の処理
            }
        }
        return true
    }
    fun setShopName(name: String) {
        val findKads = listOf(KeyAndData(KEY_USER_NAME, accountUserName!!))
        val insertKads = listOf(KeyAndData(KEY_SHOP_NAME, name))
        val success = overwriteStringData(CLASS_SHARE_SHOP_INFO, findKads, insertKads)
        if (!success)
        {
            val kads = arrayListOf(findKads, insertKads).flatten()
            addStringData(CLASS_SHARE_SHOP_INFO, kads)
        }
    }
    fun getShopName(): String {
        val findKads = listOf(KeyAndData(KEY_USER_NAME, accountUserName!!))
        val ret = getStringData(CLASS_SHARE_SHOP_INFO, findKads, KEY_SHOP_NAME)
        if (ret != null) return ret
        val insertKads = listOf(KeyAndData(KEY_SHOP_NAME, accountUserName!!))
        val kads = arrayListOf(findKads, insertKads).flatten()
        addStringData(CLASS_SHARE_SHOP_INFO, kads)
        return accountUserName!!
    }
    fun setNewsText(text: String)
    {

    }
}