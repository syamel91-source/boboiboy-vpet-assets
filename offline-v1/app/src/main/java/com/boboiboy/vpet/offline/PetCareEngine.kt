package com.boboiboy.vpet.offline

data class CareState(
    val hunger:Int=80,
    val happiness:Int=80,
    val energy:Int=80,
    val cleanliness:Int=80
)

class PetCareEngine {
    fun tick(state:CareState, minutes:Int):CareState {
        if(minutes<=0) return state
        val steps=minutes/10
        return state.copy(
            hunger=(state.hunger-steps).coerceAtLeast(0),
            energy=(state.energy-steps/2).coerceAtLeast(0),
            happiness=(state.happiness-steps/3).coerceAtLeast(0),
            cleanliness=(state.cleanliness-steps/4).coerceAtLeast(0)
        )
    }

    fun feed(state:CareState)=state.copy(
        hunger=(state.hunger+20).coerceAtMost(100),
        happiness=(state.happiness+5).coerceAtMost(100)
    )

    fun train(state:CareState):CareState? {
        if(state.energy<15 || state.hunger<10) return null
        return state.copy(
            energy=(state.energy-15).coerceAtLeast(0),
            hunger=(state.hunger-5).coerceAtLeast(0),
            happiness=(state.happiness+8).coerceAtMost(100)
        )
    }

    fun sleep(state:CareState)=state.copy(energy=100)

    fun clean(state:CareState)=state.copy(cleanliness=100)
}
