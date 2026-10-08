package com.boboiboy.vpet.offline

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun AnimatedPetSprite(context:Context,animation:PetAnimation,modifier:Modifier=Modifier) {
    val repo=remember(context){SpriteAssetRepository(context)}
    val frames=remember(animation){repo.animationFrames(animation)}
    var index by remember(animation){mutableIntStateOf(0)}
    LaunchedEffect(animation,frames) {
        index=0
        if(frames.isNotEmpty()) while(true) { delay(120); index=(index+1)%frames.size }
    }
    Box(modifier,contentAlignment=Alignment.Center) {
        if(frames.isEmpty()) Text("●",fontSize=64.sp)
        else {
            val bitmap=remember(frames,index) {
                runCatching { context.assets.open(frames[index]).use { BitmapFactory.decodeStream(it)?.asImageBitmap() } }.getOrNull()
            }
            if(bitmap!=null) Image(bitmap,null,modifier=Modifier.fillMaxSize()) else Text("●",fontSize=64.sp)
        }
    }
}
