package org.ramees.kmp

import dev.zacsweers.metro.Inject
import org.ramees.kmp.data.ChatRepo

@Inject
class GetRecommendationUseCase(private val chatRepo: ChatRepo = ChatRepo()) {

    suspend fun get(prompt: String) = chatRepo.getRecommendation(prompt)
}