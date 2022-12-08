package ru.itmo.soa.lab.shop.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.FileInputStream
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

@Component
class SslSettings(
    @Value("\${server.ssl.key-store}")
    private val keyStore: String,

    @Value("\${server.ssl.key-store-password}")
    private val keyPassword: String
) {
    private fun getKeyStore(): KeyStore {
        val keyStoreFile = FileInputStream(keyStore)
        val keyStorePassword = (keyPassword).toCharArray()
        val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(keyStoreFile, keyStorePassword)
        return keyStore
    }

    private fun getTrustManagerFactory(): TrustManagerFactory? {
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(getKeyStore())
        return trustManagerFactory
    }

    fun getSslContext(): SSLContext? {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, getTrustManagerFactory()?.trustManagers, null)
        return sslContext
    }
}
