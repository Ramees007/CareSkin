package org.ramees.kmp

import org.ramees.kmp.data.ChatRepo

class GetRecommendationUseCase(private val chatRepo: ChatRepo = ChatRepo()) {

    suspend fun get(prompt: String) = chatRepo.getRecommendation(prompt)
}