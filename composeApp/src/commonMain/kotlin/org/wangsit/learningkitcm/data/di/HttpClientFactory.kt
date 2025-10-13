package org.wangsit.learningkitcm.data.di

import io.ktor.client.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class HttpClientFactory {
    fun create(): HttpClient {
        return HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            // PERBAIKAN: Tambahkan blok timeout
            install(HttpTimeout) {
                connectTimeoutMillis = 60000 // Waktu tunggu koneksi, naikkan jadi 60 detik
                requestTimeoutMillis = 60000 // Waktu tunggu total request, naikkan jadi 60 detik
            }
        }
    }
}
