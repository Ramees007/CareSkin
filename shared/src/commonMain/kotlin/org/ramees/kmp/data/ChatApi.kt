package org.ramees.kmp.data

import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.ramees.kmp.BuildKonfig
import org.ramees.kmp.expects.client

class ChatApi {

    suspend fun get(prompt: String): String = runCatching {
        val response = client.post("https://openrouter.ai/api/v1/responses") {
            header(
                "Authorization",
                "Bearer ${BuildKonfig.OPEN_ROUTER_KEY}"
            )
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "model" to "deepseek/deepseek-chat-v3-0324", // or any model you use
                    "input" to prompt
                )
            )
        }

        val json = response.body<JsonObject>()
        return json["output"]?.jsonArray?.first()?.jsonObject?.get("content")
            ?.jsonArray?.first()?.jsonObject?.get("text")?.jsonPrimitive?.content ?: "No output"
    }.getOrNull() ?: "Error getting data"


}