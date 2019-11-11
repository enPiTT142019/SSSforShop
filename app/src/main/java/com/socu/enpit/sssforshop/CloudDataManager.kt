package com.socu.enpit.sssforshop

import com.nifcloud.mbaas.core.NCMBObject
import com.nifcloud.mbaas.core.NCMBQuery

object CloudDataManager {
    private var accountUserName: String? = null
    fun setAccountUserName(name: String) {
        accountUserName = name
    }
    fun setShopName(name: String) {
        if (accountUserName == null) return
        val query = NCMBQuery<NCMBObject>("EditScreen")
        query.whereEqualTo("AccountUserName", accountUserName)
        query.setLimit(1)
        val list: List<NCMBObject> = query.find()
        if (list.isEmpty()) {
            val obj = NCMBObject("EditScreen")
            obj.put("AccountUserName", accountUserName)
            obj.put("ShopName", name)
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
        obj.put("ShopName", name)
        obj.saveInBackground { e ->
            if (e != null) {
                // 保存に失敗した場合の処理
            } else {
                // 保存に成功した場合の処理
            }
        }
    }
    fun getShopName(): String {
        if (accountUserName == null) return ""
        val query = NCMBQuery<NCMBObject>("EditScreen")
        query.whereEqualTo("AccountUserName", accountUserName)
        query.setLimit(1)
        val list: List<NCMBObject> = query.find()
        if (list.isEmpty()) {
            val obj = NCMBObject("EditScreen")
            obj.put("AccountUserName", accountUserName)
            obj.put("ShopName", accountUserName)
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
        return obj.getString("ShopName")
    }
}