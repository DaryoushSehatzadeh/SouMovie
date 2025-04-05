package com.example.soumovie.pages

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.example.soumovie.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditions(navController: NavController) {
    val scrollState = rememberScrollState()

    // Check if user is at the bottom
    val isAtBottom = scrollState.value == scrollState.maxValue

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Ensures background is black
            .windowInsetsPadding(WindowInsets.systemBars) // Prevents overlap with status bar
            .padding(16.dp)
    ) {
        // **Top App Bar (Title)**
        TopAppBar(
            title = { Text("Terms & Conditions", color = Color.White) },
            modifier = Modifier.background(Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp)) // Space after title

        // **Scrollable Terms & Conditions Text**
        Box(
            modifier = Modifier
                .weight(1f) // Takes available space
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.terms_and_conditions),
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 24.sp,  // Ensures proper spacing between lines
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Space before button

        // **Accept Button (Inactive until scrolled to bottom)**
        Button(
            onClick = { navController.navigate("landing") },
            modifier = Modifier.fillMaxWidth()
                .navigationBarsPadding(),
            enabled = isAtBottom // Button becomes enabled only if scrolled to the bottom
        ) {
            Text("Accept")
        }
    }
}
