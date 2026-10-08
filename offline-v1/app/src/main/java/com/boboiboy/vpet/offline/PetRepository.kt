package com.boboiboy.vpet.offline
import android.content.Context
import org.json.JSONObject
class PetRepository(context:Context){
private val prefs=context.getSharedPreferences("vpet_save",Context.MODE_PRIVATE)
fun load():PetState{val raw=prefs.getString("pet",null)?:return PetState();return runCatching{val o=JSONObject(raw);PetState(o.optString("name","BoBoiBoy Basic 1"),o.optInt("hp",100),o.optInt("hunger",100),o.optInt("energy",100),o.optInt("mood",100),o.optInt("exp",0),o.optInt("level",1),o.optLong("ageMinutes",0L),o.optLong("lastTickMillis",System.currentTimeMillis())).applyTick()}.getOrDefault(PetState())}
fun save(s:PetState){val o=JSONObject().apply{put("name",s.name);put("hp",s.hp);put("hunger",s.hunger);put("energy",s.energy);put("mood",s.mood);put("exp",s.exp);put("level",s.level);put("ageMinutes",s.ageMinutes);put("lastTickMillis",s.lastTickMillis)};prefs.edit().putString("pet",o.toString()).apply()}
}