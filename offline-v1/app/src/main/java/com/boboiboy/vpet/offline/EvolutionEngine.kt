package com.boboiboy.vpet.offline

data class EvolutionForm(
    val id:String,
    val name:String,
    val requiredLevel:Int,
    val requiredWins:Int,
    val requiredHappiness:Int,
    val animation:PetAnimation=PetAnimation.EVOLUTION
)

class EvolutionEngine {
    private val forms=listOf(
        EvolutionForm("basic","BoBoiBoy Basic",1,0,0),
        EvolutionForm("thunder","BoBoiBoy Thunder",5,3,60),
        EvolutionForm("wind","BoBoiBoy Wind",8,6,70),
        EvolutionForm("earth","BoBoiBoy Earth",12,10,75)
    )

    fun current(level:Int):EvolutionForm =
        forms.lastOrNull { level >= it.requiredLevel } ?: forms.first()

    fun next(level:Int,wins:Int,happiness:Int):EvolutionForm? =
        forms.firstOrNull {
            level < it.requiredLevel &&
            level >= it.requiredLevel - 2 &&
            wins >= it.requiredWins &&
            happiness >= it.requiredHappiness
        }

    fun canEvolve(form:EvolutionForm,level:Int,wins:Int,happiness:Int):Boolean =
        level >= form.requiredLevel &&
        wins >= form.requiredWins &&
        happiness >= form.requiredHappiness
}
