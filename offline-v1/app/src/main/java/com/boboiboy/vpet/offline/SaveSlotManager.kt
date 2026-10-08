package com.boboiboy.vpet.offline

import android.content.Context

data class SaveSlot(
    val slot:Int,
    val level:Int,
    val exp:Int,
    val wins:Int,
    val formId:String
)

class SaveSlotManager(context:Context) {
    private val prefs=context.getSharedPreferences("vpet_slots",Context.MODE_PRIVATE)

    fun save(slot:Int,level:Int,exp:Int,wins:Int,formId:String) {
        prefs.edit()
            .putInt("$slot.level",level)
            .putInt("$slot.exp",exp)
            .putInt("$slot.wins",wins)
            .putString("$slot.form",formId)
            .apply()
    }

    fun load(slot:Int):SaveSlot? {
        if(!prefs.contains("$slot.level")) return null
        return SaveSlot(
            slot,
            prefs.getInt("$slot.level",1),
            prefs.getInt("$slot.exp",0),
            prefs.getInt("$slot.wins",0),
            prefs.getString("$slot.form","basic") ?: "basic"
        )
    }

    fun delete(slot:Int) {
        prefs.edit()
            .remove("$slot.level")
            .remove("$slot.exp")
            .remove("$slot.wins")
            .remove("$slot.form")
            .apply()
    }
}
