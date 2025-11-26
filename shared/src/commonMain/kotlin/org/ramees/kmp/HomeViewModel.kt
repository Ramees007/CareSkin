package org.ramees.kmp

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.ramees.kmp.base.KmpViewModel
import org.ramees.kmp.model.HomeEffect
import org.ramees.kmp.model.Refinement
import org.ramees.kmp.model.HomeState
import org.ramees.kmp.model.Trait

class HomeViewModel() :
    KmpViewModel() {

    private val useCase: GetRecommendationUseCase = GetRecommendationUseCase()

    private val _state = MutableStateFlow(HomeState())

    @NativeCoroutinesState
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val mutableEffect: Channel<HomeEffect> = Channel()
    @NativeCoroutines
    val effect: Flow<HomeEffect> = mutableEffect.receiveAsFlow()


    init {
        setData()
    }

    fun handleTraitClick(trait: String, refinement: String) {
        val refinements = _state.value.refinements.map {
            if (it.name == refinement) {
                it.copy(
                    rows = it.rows.map { row ->
                        row.map { t ->
                            if (t.name == trait) {
                                t.copy(isSelected = !t.isSelected)
                            } else {
                                t
                            }
                        }
                    }
                )
            } else {
                it
            }
        }
        _state.value = _state.value.copy(
            refinements = refinements,
            loading = false
        )
    }

    fun handleSubmit() = scope.launch {
        val selection = _state.value.refinements.associate {
            val values: List<String> = it.rows.flatMap { it.map { it.name } }
            it.name to values
        }
        _state.value = _state.value.copy(loading = true)
//        val res =
//            useCase.get("Provide skincare recommendations based on the following traits: $selection, Be very brief - below 100 words.")
        delay(1000)
        navigate("")
    }

    fun navigate(rec: String) {
        scope.launch {
            mutableEffect.send(HomeEffect.NavigateToDetailPage(response = rec))
        }
    }

    private fun setData() {
        _state.value = HomeState(
            title = "Skin Questionnaire",
            refinements = listOf(
                Refinement(
                    name = "Skin Type",
                    rows = listOf(
                        Trait("Dry"),
                        Trait("Oily"),
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
            )
        )
    }

}