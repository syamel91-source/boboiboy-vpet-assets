package com.boboiboy.vpet.offline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class PetAnimation { IDLE, ATTACK, SPECIAL, HURT, HAPPY, SAD }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { OfflineBattle() }
    }
}

@Composable
fun OfflineBattle() {
    var playerHp by remember { mutableIntStateOf(100) }
    var cpuHp by remember { mutableIntStateOf(100) }
    var anim by remember { mutableStateOf(PetAnimation.IDLE) }
    var over by remember { mutableStateOf(false) }
    var log by remember { mutableStateOf(listOf("Offline battle ready.")) }

    fun add(s:String) { log=(log+s).takeLast(8) }
    fun reset() { playerHp=100; cpuHp=100; anim=PetAnimation.IDLE; over=false; log=listOf("New offline battle.") }

    fun act(a:String) {
        if(over) return
        when(a) {
            "attack" -> { cpuHp=(cpuHp-12).coerceAtLeast(0); anim=PetAnimation.ATTACK; add("Attack: 12 damage") }
            "special" -> { cpuHp=(cpuHp-22).coerceAtLeast(0); anim=PetAnimation.SPECIAL; add("Special: 22 damage") }
            "heal" -> { playerHp=(playerHp+10).coerceAtMost(100); anim=PetAnimation.HAPPY; add("Heal: +10 HP") }
        }
        if(cpuHp==0) { over=true; anim=PetAnimation.HAPPY; add("VICTORY!") }
        else {
            playerHp=(playerHp-8).coerceAtLeast(0); anim=PetAnimation.HURT; add("CPU: 8 damage")
            if(playerHp==0) { over=true; anim=PetAnimation.SAD; add("DEFEAT.") } else anim=PetAnimation.IDLE
        }
    }

    MaterialTheme(colorScheme=darkColorScheme(background=Color(0xFF101014),surface=Color(0xFF1B1B22))) {
        Surface(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
                Text("BoBoiBoy VPET", fontSize=26.sp, fontWeight=FontWeight.Bold)
                Text("OFFLINE MODE", color=Color(0xFF8AB4F8))
                Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(10.dp)) {
                    PetCard("PLAYER",playerHp,anim,Modifier.weight(1f))
                    PetCard("CPU",cpuHp,PetAnimation.IDLE,Modifier.weight(1f))
                }
                Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                    Button({act("attack")}, enabled=!over){Text("Attack")}
                    Button({act("special")}, enabled=!over){Text("Special")}
                    Button({act("heal")}, enabled=!over){Text("Heal")}
                }
                if(over) Button({reset()},Modifier.fillMaxWidth()){Text("NEW BATTLE")}
                Text("Battle Log",fontWeight=FontWeight.Bold)
                Column(Modifier.fillMaxWidth().weight(1f).background(Color(0xFF17171D),RoundedCornerShape(12.dp)).padding(12.dp)) {
                    log.forEach { Text("• $it",fontSize=14.sp) }
                }
                Text("No Internet • No server • Local battle",fontSize=12.sp,color=Color.Gray)
            }
        }
    }
}

@Composable
fun PetCard(title:String,hp:Int,anim:PetAnimation,modifier:Modifier) {
    Column(modifier.border(1.dp,Color(0xFF3A3A45),RoundedCornerShape(14.dp)).padding(10.dp),horizontalAlignment=Alignment.CenterHorizontally) {
        Text(title,fontWeight=FontWeight.Bold)
        Box(Modifier.size(100.dp).background(Color(0xFF24242D),RoundedCornerShape(12.dp)),contentAlignment=Alignment.Center) {
            Text(when(anim){PetAnimation.ATTACK->"⚡";PetAnimation.SPECIAL->"🔥";PetAnimation.HURT->"💥";PetAnimation.HAPPY->"★";PetAnimation.SAD->"…";else->"●"},fontSize=42.sp)
        }
        Text("HP $hp / 100")
        LinearProgressIndicator(progress={hp/100f},Modifier.fillMaxWidth())
        Text(anim.name,fontSize=11.sp,color=Color.Gray)
    }
}