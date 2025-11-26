package org.ramees.kmp.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class KmpViewModel {

    protected val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun onCleared() {
        scope.cancel()
    }
}