package com.demo.microservices.service

import com.demo.microservices.model.User
import com.demo.microservices.repository.UserRepository
import com.demo.microservices.exception.UserNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.Exception

@Service
class UserService(
    private val repository: UserRepository,
    @Value("\${api.audit.url:http://localhost:8083/audit/log}")
    private val auditApiUrl: String = "http://localhost:8083/audit/log"
) {

    fun findAll(): List<User> = repository.findAll()

    fun findById(id: Long): User =
        repository.findById(id).orElseThrow {
            UserNotFoundException("User not found with id $id")
        }

    fun save(user: User): User {
        val operation = if (user.id == null) "CREATE" else "UPDATE"
        val saved = repository.save(user)
        println(">>> Salvo localmente: $saved")

        sendToAudit(saved, operation)
        return saved
    }

    fun delete(id: Long) {
        val userToDelete = findById(id)
        repository.deleteById(id)
        sendToAudit(userToDelete, "DELETE")
    }

    private fun sendToAudit(user: User, operation: String) {
        try {
            val restTemplate = RestTemplate()
            val payload = mapOf(
                "userId" to user.id,
                "userName" to user.name,
                "operation" to operation
            )

            restTemplate.postForObject(auditApiUrl, payload, Map::class.java)
            println(">>> Auditoria: Evento [$operation] enviado para: $auditApiUrl")
        } catch (e: Exception) {
            println(">>> Erro Auditoria: Falha ao conectar em $auditApiUrl. Erro: ${e.message}")
        }
    }
}
