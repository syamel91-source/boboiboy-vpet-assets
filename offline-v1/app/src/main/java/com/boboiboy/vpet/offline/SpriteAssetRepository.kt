package com.boboiboy.vpet.offline

import android.content.Context

class SpriteAssetRepository(private val context:Context) {
    fun exists(name:String):Boolean =
        runCatching { context.assets.open("sprites/$name").close(); true }.getOrDefault(false)

    fun animationFrames(animation:PetAnimation):List<String> {
        val prefix=when(animation) {
            PetAnimation.IDLE -> "idle"
            PetAnimation.WALK -> "walk"
            PetAnimation.EAT -> "eat"
            PetAnimation.SLEEP -> "sleep"
            PetAnimation.HAPPY -> "happy"
            PetAnimation.SAD -> "sad"
            PetAnimation.ANGRY -> "angry"
            PetAnimation.HURT -> "hurt"
            PetAnimation.SPECIAL -> "special"
            PetAnimation.EVOLUTION -> "evolution"
            PetAnimation.EXCITED -> "excited"
            PetAnimation.VICTORY -> "victory"
            PetAnimation.DEFEAT -> "defeat"
        }
        return runCatching {
            context.assets.list("sprites")
                ?.filter { it.startsWith(prefix+"_") && it.endsWith(".png") }
                ?.sorted()
                ?.map { "sprites/$it" }
                ?: emptyList()
        }.getOrDefault(emptyList())
    }
}
