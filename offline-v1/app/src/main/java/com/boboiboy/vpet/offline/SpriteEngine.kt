package com.boboiboy.vpet.offline

enum class PetAnimation {
    IDLE, WALK, EAT, SLEEP, HAPPY, SAD, ANGRY, HURT, SPECIAL, EVOLUTION, EXCITED, VICTORY, DEFEAT
}

data class SpriteFrame(
    val assetName:String,
    val durationMs:Long=120
)

data class SpriteSequence(
    val animation:PetAnimation,
    val frames:List<SpriteFrame>,
    val loop:Boolean=true
)

class SpriteEngine {
    private val sequences=mutableMapOf<PetAnimation,SpriteSequence>()

    fun register(sequence:SpriteSequence) {
        require(sequence.frames.isNotEmpty())
        sequences[sequence.animation]=sequence
    }

    fun sequence(animation:PetAnimation):SpriteSequence? = sequences[animation]

    fun frameNames(animation:PetAnimation):List<String> =
        sequences[animation]?.frames?.map { it.assetName }.orEmpty()
}

/*
Recommended 64x56 asset naming:

idle_01.png
idle_02.png
walk_01.png
walk_02.png
eat_01.png
eat_02.png
sleep_01.png
happy_01.png
sad_01.png
angry_01.png
hurt_01.png
special_01.png
special_02.png
evolution_01.png
victory_01.png
defeat_01.png
*/
