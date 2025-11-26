package org.ramees.kmp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ramees.kmp.model.HomeEffect
import org.ramees.kmp.model.HomeState
import org.ramees.kmp.model.Refinement
import org.ramees.kmp.model.Trait

@Composable
internal fun HomeScreen(onNavigateToDetails: () -> Unit) {
    val viewModel: HomeViewModel = remember { HomeViewModel() }
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is HomeEffect.NavigateToDetailPage -> {
                    onNavigateToDetails()
                }
            }
        }
    }

    Content(
        state = state.value,
        onTraitClick = viewModel::handleTraitClick,
        onSubmit = viewModel::handleSubmit
    )
}

@Composable
private fun Content(
    state: HomeState,
    onTraitClick: (trait: String, refinement: String) -> Unit,
    onSubmit: () -> Unit
) {
    LazyColumn(modifier = Modifier.background(Color.White)) {
        stickyHeader(key = "header", contentType = 0) {
            Text(
                text = state.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(16.dp)
            )
        }

        items(items = state.refinements, key = { it.name }) { refinement ->
            RefinementItem(refinement, onTraitClick)
        }

        item(key = "submit", contentType = 1) {
            if (state.loading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(40.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

            } else {
                Button(
                    onClick = onSubmit,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Submit")
                }
            }

        }
    }
}

@Composable
private fun RefinementItem(
    refinement: Refinement,
    onTraitClick: (trait: String, refinement: String) -> Unit
) = Column {
    Text(
        text = refinement.name,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
            .padding(16.dp)
    )

    refinement.rows.forEach { row ->
        Row {
            row.forEach { trait ->
                TraitItem(
                    trait = trait,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    onTraitClick = {
                        onTraitClick(trait.name, refinement.name)
                    }
                )
            }
        }
    }
}

@Composable
private fun TraitItem(
    trait: Trait, modifier: Modifier = Modifier,
    onTraitClick: (Trait) -> Unit
) {
    val border = if (trait.isSelected) {
        BorderStroke(
            2.dp,
            MaterialTheme.colorScheme.primary
        )
    } else {
        BorderStroke(1.dp, MaterialTheme.colorScheme.inversePrimary)
    }
    Card(
        border = border,
        modifier = modifier.clickable {
            onTraitClick(trait)
        }
    ) {
        Text(
            text = trait.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun AppPreview() {
    MaterialTheme {
        Content(
            HomeState(
                title = "Skin Questionnaire",
                refinements = listOf(
                    Refinement(
                        name = "Skin Type",
                        rows = listOf(
                            Trait("Dry"),
                            Trait("Oily", isSelected = true),
                            Trait("Combination"),
                            Trait("Normal")
                        ).chunked(2)
                    ),
                    Refinement(
                        name = "Concerns",
                        rows = listOf(
                            Trait("Acne"),
                            Trait("Aging"),
                            Trait("Dark Spots"),
                            Trait("Redness"),
                            Trait("Dullness")
                        ).chunked(2)
                    )
                ),
                loading = true
            ),
            onTraitClick = { _, _ -> },
            onSubmit = {}
        )
    }
}