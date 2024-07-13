package com.thienphu.mytodolistapp.ui.list


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thienphu.mytodolistapp.R
import com.thienphu.mytodolistapp.ui.theme.MediumGray


@Composable
fun EmptyContent() {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = painterResource(id = R.drawable.ic_sad_emoji), contentDescription = "Sad Face Icon",Modifier.size(120.dp), tint = MediumGray)
        Text("No Tasks Found", color = MediumGray, fontWeight = FontWeight.Bold, fontSize = 26.sp)
    }
}

@Composable
@Preview
fun EmptyContentPreview() {
    EmptyContent()
}