package org.ramees.kmp.data

import dev.zacsweers.metro.Inject

@Inject
class ChatRepo (private val chatApi: ChatApi){

    suspend fun getRecommendation(prompt: String): String  = chatApi.get(prompt)
}