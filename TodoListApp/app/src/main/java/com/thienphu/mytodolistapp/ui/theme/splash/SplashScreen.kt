package com.thienphu.mytodolistapp.ui.theme.splash

import android.window.SplashScreen
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thienphu.mytodolistapp.R
import com.thienphu.mytodolistapp.ui.theme.Purple500
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navigateToListScreen : () -> Unit
){
    var animation by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if(animation) 0.dp else 100.dp,
        animationSpec = tween(300), label = "off set"
    )

    val alphaState by animateFloatAsState(
        targetValue = if(animation) 1f else 0f,
        animationSpec = tween(300), label = "alpha")

    LaunchedEffect(key1=true) {
        animation = true
        delay(300)
        navigateToListScreen()
    }
    Box(modifier = Modifier.fillMaxSize().background(color = Purple500), contentAlignment = Alignment.Center) {
        Image(modifier = Modifier.size(100.dp).offset(y=offsetState).alpha(alphaState), painter = painterResource(id = getLogo()), contentDescription = "Logo")
    }
}

@Composable
fun getLogo() : Int {
    return R.drawable.ic_todo_list
}


