package com.boboiboy.vpet.offline
import kotlin.math.max
import kotlin.math.min
data class PetState(val name:String="BoBoiBoy Basic 1",val hp:Int=100,val hunger:Int=100,val energy:Int=100,val mood:Int=100,val exp:Int=0,val level:Int=1,val ageMinutes:Long=0L,val lastTickMillis:Long=System.currentTimeMillis()){
fun clamp()=copy(hp=hp.coerceIn(0,100),hunger=hunger.coerceIn(0,100),energy=energy.coerceIn(0,100),mood=mood.coerceIn(0,100),exp=max(0,exp),level=max(1,level))
fun applyTick(now:Long=System.currentTimeMillis()):PetState{val e=max(0L,now-lastTickMillis);val m=e/60000L;if(m<=0)return copy(lastTickMillis=now).clamp();return copy(hunger=hunger-m.toInt(),energy=energy-m.toInt(),mood=mood-(m/3L).toInt(),ageMinutes=ageMinutes+m,lastTickMillis=now).clamp()}
fun feed()=copy(hunger=min(100,hunger+20),mood=min(100,mood+5),energy=max(0,energy-2),exp=exp+2,lastTickMillis=System.currentTimeMillis()).clamp()
fun play()=copy(mood=min(100,mood+15),energy=max(0,energy-10),hunger=max(0,hunger-3),exp=exp+5,lastTickMillis=System.currentTimeMillis()).clamp()
fun train()=copy(energy=max(0,energy-20),hunger=max(0,hunger-5),mood=max(0,mood-2),exp=exp+15,lastTickMillis=System.currentTimeMillis()).copy(level=(1+(exp+15)/100).coerceAtMost(99))
fun sleep()=copy(energy=100,mood=min(100,mood+5),lastTickMillis=System.currentTimeMillis()).clamp()
}