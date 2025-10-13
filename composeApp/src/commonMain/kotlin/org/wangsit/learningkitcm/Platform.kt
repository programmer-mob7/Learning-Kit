package org.wangsit.learningkitcm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform