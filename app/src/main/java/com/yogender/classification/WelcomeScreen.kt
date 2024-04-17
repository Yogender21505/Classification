package com.yogender.classification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun WelcomScreen(navController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background Image
            Image(
                painter = painterResource(R.drawable.welcome_img),
                contentDescription = "Welcome Image",
                modifier = Modifier.size(200.dp).align(Alignment.Center),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Welcome To App", color = Color.Black, fontSize = 30.sp)
                Text("Identify Fruit Banana Apple and Orange", color = Color.Black, fontSize = 20.sp)

            }
            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = {navController.navigate("Choose_screen")  },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(Color(0xFFFCD240)),
                    modifier = Modifier.size(80.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "Open Slider",
                        tint = Color.Black
                    )
                }
//                Button(modifier = Modifier
//                    .fillMaxWidth()
//                    ,colors = ButtonDefaults.buttonColors(Color.White),
//                    onClick = {
//                        navController.navigate("rotation_screen")
//                    },
//                    content ={
//                        mOrientation.startListening(mainActivity)
//                        Text("Start Rotation", color = Color.Black)
//                    }
//                )
            }
        }
    }
}
