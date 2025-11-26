package org.ramees.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform