package com.socu.enpit.sssforshop

import com.nifcloud.mbaas.core.NCMBObject
import com.nifcloud.mbaas.core.NCMBQuery

object CloudDataManager {
    private const val CLASS_NAME_EDIT_SCREEN = "EditScreen"
    private const val KEY_ACCOUNT_USER_NAME: String = "AccountUserName"
    private const val KEY_SHOP_NAME: String = "ShopName"

    private var accountUserName: String? = null

    fun setAccountUserName(name: String) {
        accountUserName = name
    }
    private fun saveStringData(className: String, key: String, data: String)
    {
        if (accountUserName == null) return
        val query = NCMBQuery<NCMBObject>(className)
        query.whereEqualTo(KEY_ACCOUNT_USER_NAME, accountUserName)
        query.setLimit(1)
        val list: List<NCMBObject> = query.find()
        if (list.isEmpty()) {
            val obj = NCMBObject(className)
            obj.put(KEY_ACCOUNT_USER_NAME, accountUserName)
            obj.put(key, data)
            obj.saveInBackground { e ->
                if (e != null) {
                    // 保存に失敗した場合の処理
                } else {
                    // 保存に成功した場合の処理
                }
            }
            return
        }
        val obj = list[0]
        obj.put(key, data)
        obj.saveInBackground { e ->
            if (e != null) {
                // 保存に失敗した場合の処理
            } else {
                // 保存に成功した場合の処理
            }
        }
    }
    fun setShopName(name: String) {
        saveStringData(CLASS_NAME_EDIT_SCREEN, KEY_SHOP_NAME, name)
    }
    fun getShopName(): String {
        if (accountUserName == null) return ""
        val query = NCMBQuery<NCMBObject>(CLASS_NAME_EDIT_SCREEN)
        query.whereEqualTo(KEY_ACCOUNT_USER_NAME, accountUserName)
        query.setLimit(1)
        val list: List<NCMBObject> = query.find()
        if (list.isEmpty()) {
            val obj = NCMBObject(CLASS_NAME_EDIT_SCREEN)
            obj.put(KEY_ACCOUNT_USER_NAME, accountUserName)
            obj.put(KEY_SHOP_NAME, accountUserName)
            obj.saveInBackground { e ->
                if (e != null) {
                    // 保存に失敗した場合の処理
                } else {
                    // 保存に成功した場合の処理
                }
            }
            return accountUserName!!
        }
        val obj = list[0]
        return obj.getString(KEY_SHOP_NAME)
    }
    fun setNewsText(text: String)
    {

    }
}