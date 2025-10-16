package org.wangsit.learningkitcm.data.di

import io.ktor.client.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class HttpClientFactory {

//    val client = HttpClient(CIO){
//        install(Logging)
//    }

    fun create(): HttpClient {
        return HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 60000
                requestTimeoutMillis = 60000
            }
        }
    }
}
