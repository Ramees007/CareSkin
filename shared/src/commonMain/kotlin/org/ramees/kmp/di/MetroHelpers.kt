package org.ramees.kmp.di

import dev.zacsweers.metro.createGraphFactory

fun createAppGraph() = createGraphFactory<AppGraph.Factory>().create()