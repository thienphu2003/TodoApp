package com.thienphu.mytodolistapp.components




import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    onCloseDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
  if(openDialog){
      AlertDialog(
          title = {
              Text(
                  text = title,
                  fontWeight = FontWeight.Bold,
                  fontSize = 20.sp
              )
          },
          text = {
              Text(
                  text = message,
                  fontWeight = FontWeight.Normal,
                  fontSize = 16.sp
              )
          },
          onDismissRequest = {
              onCloseDialog()
          },
          confirmButton = {
              Button(
                  onClick = {
                      onYesClicked()
                      onCloseDialog()
                  }
              ) {
                  Text("YES")
              }
          },
          dismissButton = {
             OutlinedButton(
                 onClick = {
                     onCloseDialog()
                 }
             ) {
                 Text("NO")
             }
          }
      )
  }
}
