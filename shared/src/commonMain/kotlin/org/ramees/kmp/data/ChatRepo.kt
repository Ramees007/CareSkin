package org.ramees.kmp.data

class ChatRepo (private val chatApi: ChatApi = ChatApi()){

    suspend fun getRecommendation(prompt: String): String  = chatApi.get(prompt)
}