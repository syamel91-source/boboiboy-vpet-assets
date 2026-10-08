package com.boboiboy.vpet.offline

enum class PetAnimation { IDLE, WALK, EAT, SLEEP, HAPPY, SAD, ANGRY, HURT, ATTACK, SPECIAL, EVOLUTION, EXCITED, VICTORY, DEFEAT }

data class SpriteFrame(val assetName:String,val durationMs:Long=120)
data class SpriteSequence(val animation:PetAnimation,val frames:List<SpriteFrame>,val loop:Boolean=true)

class SpriteEngine {
    private val sequences=mutableMapOf<PetAnimation,SpriteSequence>()
    fun register(sequence:SpriteSequence){ require(sequence.frames.isNotEmpty()); sequences[sequence.animation]=sequence }
    fun sequence(animation:PetAnimation):SpriteSequence?=sequences[animation]
    fun frameNames(animation:PetAnimation):List<String> = sequences[animation]?.frames?.map{it.assetName}.orEmpty()
}
