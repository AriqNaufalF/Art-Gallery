package com.example.artgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artgallery.ui.theme.ArtGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtGalleryTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ArtGalleryScreen()
                }
            }
        }
    }
}

@Composable
fun ArtGalleryScreen() {
    var currentArt by remember { mutableStateOf(1) }
    val (description, title, creator, createdAt) = when(currentArt) {
        1 -> stringArrayResource(R.array.art_1_data)
        2 -> stringArrayResource(R.array.art_2_data)
        3 -> stringArrayResource(R.array.art_3_data)
        4 -> stringArrayResource(R.array.art_4_data)
        else -> stringArrayResource(R.array.art_5_data)
    }
    val imageResource = when(currentArt) {
        1 -> R.drawable.art_1
        2 -> R.drawable.art_2
        3 -> R.drawable.art_3
        4 -> R.drawable.art_4
        else -> R.drawable.art_5
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ArtAndLabel(
            modifier = Modifier
                .weight(2f)
                .padding(vertical = 24.dp),
            painter = imageResource,
            description = description,
            title = title,
            creator = creator,
            createdAt = createdAt
        )
        NextAndPrevious(
            onPrevious = {
                if (currentArt < 2) {
                    currentArt = 5
                } else {
                    currentArt--
                }
            },
            onNext = {
                if (currentArt > 4) {
                    currentArt = 1
                } else {
                    currentArt++
                }
            },
        )
    }
}

@Composable
fun ArtAndLabel(
    modifier: Modifier = Modifier,
    @DrawableRes painter: Int,
    description: String,
    title: String,
    creator: String,
    createdAt: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .weight(2f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(painter),
                contentDescription = description,
                modifier = Modifier
                    .shadow(8.dp)
                    .background(MaterialTheme.colors.background)
                    .border(BorderStroke(4.dp, Color.Gray))
                    .padding(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .shadow(3.dp)
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            Text(text = title, fontSize = 20.sp)
            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
                    append(creator)
                }
                append(" ($createdAt)")
            })
        }
    }
}

@Composable
fun NextAndPrevious(
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Button(onClick = onPrevious, modifier = Modifier.weight(1f)) {
            Text(text = "Previous")
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(onClick = onNext, modifier = Modifier.weight(1f)) {
            Text(text = "Next")
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        ArtGalleryScreen()
    }
}
