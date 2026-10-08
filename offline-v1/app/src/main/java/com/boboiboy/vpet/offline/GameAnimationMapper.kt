package com.boboiboy.vpet.offline

object GameAnimationMapper {
    fun forCare(action:String):PetAnimation = when(action) {
        "FEED" -> PetAnimation.EAT
        "TRAIN" -> PetAnimation.EXCITED
        "SLEEP" -> PetAnimation.SLEEP
        "CLEAN" -> PetAnimation.HAPPY
        else -> PetAnimation.IDLE
    }

    fun forBattle(action:String):PetAnimation = when(action) {
        "ATTACK" -> PetAnimation.ATTACK
        "SPECIAL" -> PetAnimation.SPECIAL
        "HIT" -> PetAnimation.HURT
        "WIN" -> PetAnimation.VICTORY
        "LOSE" -> PetAnimation.DEFEAT
        else -> PetAnimation.IDLE
    }

    fun forEvolution():PetAnimation = PetAnimation.EVOLUTION
}
