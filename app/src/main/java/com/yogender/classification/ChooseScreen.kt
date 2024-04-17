package com.yogender.classification


import android.annotation.SuppressLint
import com.yogender.classification.APICLASS.imageSize
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.nio.ByteBuffer

@SuppressLint("UnrememberedMutableState")
@Composable
fun ChooseScreen(navHostController: NavHostController) {
    val context = LocalContext.current

    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            photoUri = it
        }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        photoUri?.let {
            if (Build.VERSION.SDK_INT < 28)
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap = ImageDecoder.decodeBitmap(
                    source,
                    ImageDecoder.OnHeaderDecodedListener { decoder, info, source ->
                        decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                        decoder.isMutableRequired = true
                    })
            }
        }
        // Background Image
        ElevatedCard(modifier = Modifier
            .align(Alignment.Center)
            .height(500.dp).width(380.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            content = {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    if(bitmap!=null){
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "gallaryimage",
                                Modifier.size(400.dp)
                            )
                        }
                    }else{
                        Image(
                            painter = painterResource(R.drawable.empty_photo_gallary),
                            contentDescription = "photo_Image",
                            modifier = Modifier
                                .size(300.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val scaledBitmap = bitmap?.let { Bitmap.createScaledBitmap(it, APICLASS.imageSize, APICLASS.imageSize, false) }
                        if (scaledBitmap != null) {
                            var x by remember {mutableStateOf("")}
                            APICLASS.classifyImage(context, scaledBitmap) { fruit ->
                                x = fruit
                                println(fruit)
                            }
                            Text(text = x, color = Color.Black, fontSize = 24.sp)

                        }
                        else{
                            Text("No File selected", color = Color.Black, fontSize = 24.sp)
                        }
                    }
                }
            }
        )
        Spacer(modifier = Modifier.padding(20.dp))
        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(80.dp)
                    .padding(16.dp),
                onClick = {
                    launcher.launch("image/*")
                },
                content = {
                    Text("Choose File", color = Color.Black)
                }
            )
        }
    }
}
