package org.ramees.kmp.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import org.ramees.kmp.HomeViewModelFactory

@DependencyGraph(AppScope::class)
interface AppGraph {

    val homeViewModelFactory: HomeViewModelFactory

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(): AppGraph
    }
}