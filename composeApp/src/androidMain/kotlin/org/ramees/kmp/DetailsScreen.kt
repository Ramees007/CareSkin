package org.ramees.kmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
internal fun DetailsScreen(data: String) {

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Text("Details")

        MarkdownText(data)
    }
}