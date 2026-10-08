package com.boboiboy.vpet.offline

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class PetData(
    val level:Int=1,val exp:Int=0,val hp:Int=100,val hunger:Int=80,
    val happiness:Int=80,val energy:Int=80,val clean:Int=80,val wins:Int=0
)

class MainActivity:ComponentActivity(){
    override fun onCreate(b:Bundle?){
        super.onCreate(b)
        val prefs=getSharedPreferences("vpet",Context.MODE_PRIVATE)
        setContent {
            var pet by remember { mutableStateOf(load(prefs)) }
            var page by remember { mutableStateOf("HOME") }
            var animation by remember { mutableStateOf(PetAnimation.IDLE) }
            fun save(p:PetData){ pet=p; prefs.edit()
                .putInt("level",p.level).putInt("exp",p.exp).putInt("hp",p.hp)
                .putInt("hunger",p.hunger).putInt("happiness",p.happiness)
                .putInt("energy",p.energy).putInt("clean",p.clean).putInt("wins",p.wins).apply() }
            fun play(a:PetAnimation){ animation=a }
            LaunchedEffect(Unit) {
                while(true) {
                    delay(60_000)
                    val p=load(prefs)
                    save(p.copy(
                        hunger=(p.hunger-2).coerceAtLeast(0),
                        energy=(p.energy-1).coerceAtLeast(0),
                        clean=(p.clean-1).coerceAtLeast(0),
                        happiness=(p.happiness-1).coerceAtLeast(0)
                    ))
                }
            }
            LaunchedEffect(animation) {
                if(animation!=PetAnimation.IDLE) { delay(900); animation=PetAnimation.IDLE }
            }
            MaterialTheme(colorScheme=darkColorScheme(background=Color(0xFF101014),surface=Color(0xFF1B1B22))) {
                when(page) {
                    "BATTLE" -> Battle(pet,animation,{play(it)},{save(levelUp(pet.copy(wins=pet.wins+1)));page="HOME"})
                    "EVOLUTION" -> EvolutionPage(pet,{page="HOME"},play)
                    else -> Home(pet,animation,{a ->
                        when(a) {
                            "FEED" -> { save(pet.copy(hunger=(pet.hunger+20).coerceAtMost(100),happiness=(pet.happiness+5).coerceAtMost(100))); play(PetAnimation.EAT) }
                            "TRAIN" -> { save(levelUp(pet.copy(energy=(pet.energy-12).coerceAtLeast(0),exp=pet.exp+25))); play(PetAnimation.EXCITED) }
                            "SLEEP" -> { save(pet.copy(energy=100,hp=(pet.hp+10).coerceAtMost(100))); play(PetAnimation.SLEEP) }
                            else -> { save(pet.copy(clean=100,happiness=(pet.happiness+5).coerceAtMost(100))); play(PetAnimation.HAPPY) }
                        }
                    },{page="BATTLE"},{page="EVOLUTION"})
                }
            }
        }
    }
    fun load(p:android.content.SharedPreferences)=PetData(
        p.getInt("level",1),p.getInt("exp",0),p.getInt("hp",100),p.getInt("hunger",80),
        p.getInt("happiness",80),p.getInt("energy",80),p.getInt("clean",80),p.getInt("wins",0)
    )
    fun levelUp(p:PetData):PetData=if(p.exp>=100)p.copy(level=p.level+1,exp=p.exp-100,hp=100,energy=100)else p
}

@Composable
fun Home(p:PetData,animation:PetAnimation,action:(String)->Unit,battle:()->Unit,evolution:()->Unit){
    val context=LocalContext.current
    Column(Modifier.fillMaxSize().padding(16.dp),verticalArrangement=Arrangement.spacedBy(10.dp)){
        Text("BoBoiBoy VPET",fontSize=27.sp,fontWeight=FontWeight.Bold)
        Text("OFFLINE • PET HOME",color=Color(0xFF8AB4F8))
        Card(Modifier.fillMaxWidth()){
            Column(Modifier.padding(12.dp),horizontalAlignment=Alignment.CenterHorizontally){
                AnimatedPetSprite(context,animation,Modifier.size(128.dp))
                Text("Level "+p.level,fontSize=21.sp)
                Text("EXP "+p.exp+"/100   Wins "+p.wins)
            }
        }
        Stat("HP",p.hp);Stat("Hunger",p.hunger);Stat("Happiness",p.happiness);Stat("Energy",p.energy);Stat("Clean",p.clean)
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(6.dp)){
            Button({action("FEED")},Modifier.weight(1f)){Text("Feed")}
            Button({action("TRAIN")},Modifier.weight(1f)){Text("Train")}
        }
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(6.dp)){
            Button({action("SLEEP")},Modifier.weight(1f)){Text("Sleep")}
            Button({action("CLEAN")},Modifier.weight(1f)){Text("Clean")}
        }
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(6.dp)){
            Button(battle,Modifier.weight(1f)){Text("⚔ BATTLE")}
            OutlinedButton(evolution,Modifier.weight(1f)){Text("Evolution")}
        }
    }
}

@Composable fun Stat(n:String,v:Int){
    Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically){
        Text(n,Modifier.width(90.dp))
        LinearProgressIndicator(progress={v/100f},Modifier.weight(1f))
        Text(" "+v)
    }
}

@Composable
fun Battle(p:PetData,animation:PetAnimation,back:()->Unit,finishWin:(PetAnimation)->Unit){
    val context=LocalContext.current
    var hp by remember{mutableIntStateOf(p.hp)}
    var cpu by remember{mutableIntStateOf(100)}
    var over by remember{mutableStateOf(false)}
    var localAnimation by remember{mutableStateOf(animation)}
    Column(Modifier.fillMaxSize().padding(16.dp),verticalArrangement=Arrangement.spacedBy(14.dp)){
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){
            Text("⚔ OFFLINE BATTLE",fontSize=22.sp,fontWeight=FontWeight.Bold)
            TextButton(back){Text("Home")}
        }
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(8.dp)){
            Fighter(context,"YOU",hp,localAnimation,Modifier.weight(1f))
            Fighter(context,"CPU",cpu,PetAnimation.IDLE,Modifier.weight(1f))
        }
        Row(horizontalArrangement=Arrangement.spacedBy(6.dp)){
            Button({
                localAnimation=PetAnimation.ATTACK
                cpu=(cpu-12).coerceAtLeast(0)
                if(cpu==0){over=true;finishWin(PetAnimation.VICTORY)}
                else {hp=(hp-8).coerceAtLeast(0); if(hp==0){over=true;finishWin(PetAnimation.DEFEAT)}}
            },enabled=!over){Text("Attack")}
            Button({
                localAnimation=PetAnimation.SPECIAL
                cpu=(cpu-22).coerceAtLeast(0)
                if(cpu==0){over=true;finishWin(PetAnimation.VICTORY)}
                else {hp=(hp-8).coerceAtLeast(0); if(hp==0){over=true;finishWin(PetAnimation.DEFEAT)}}
            },enabled=!over){Text("Special")}
            Button({localAnimation=PetAnimation.HAPPY;hp=(hp+10).coerceAtMost(100)},enabled=!over){Text("Heal")}
        }
    }
}

@Composable
fun Fighter(context:Context,n:String,hp:Int,animation:PetAnimation,m:Modifier){
    Column(m.background(Color(0xFF1B1B22),RoundedCornerShape(14.dp)).padding(12.dp),horizontalAlignment=Alignment.CenterHorizontally){
        Text(n,fontWeight=FontWeight.Bold)
        AnimatedPetSprite(context,animation,Modifier.size(90.dp))
        Text("HP "+hp+"/100")
        LinearProgressIndicator(progress={hp/100f},Modifier.fillMaxWidth())
    }
}

@Composable
fun EvolutionPage(p:PetData,back:()->Unit,play:(PetAnimation)->Unit){
    val engine=remember{EvolutionEngine()}
    val current=engine.current(p.level)
    val next=engine.next(p.level,p.wins,p.happiness)
    Column(Modifier.fillMaxSize().padding(16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){
        Text("Evolution",fontSize=28.sp,fontWeight=FontWeight.Bold)
        Text("Current: "+current.name)
        Text("Level "+p.level+" • Wins "+p.wins+" • Happiness "+p.happiness)
        if(next!=null) {
            Text("Next: "+next.name)
            Text("Requirements: Level "+next.requiredLevel+", Wins "+next.requiredWins+", Happiness "+next.requiredHappiness)
            Button({play(PetAnimation.EVOLUTION)},enabled=engine.canEvolve(next,p.level,p.wins,p.happiness)){Text("Evolve")}
        } else Text("No evolution available yet.")
        OutlinedButton(back){Text("Back")}
    }
}
