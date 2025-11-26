package org.ramees.kmp.model

data class Trait(val name: String, val isSelected: Boolean = false)

data class Refinement(val name: String, val rows: List<List<Trait>>)

data class HomeState(
    val title: String = "",
    val refinements: List<Refinement> = emptyList(),
    val loading: Boolean = false
)

sealed class HomeEffect {

    data class NavigateToDetailPage(val response: String) : HomeEffect()
}