package com.demo.microservices.service

import com.demo.microservices.model.User
import com.demo.microservices.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class UserServiceTest {

    private val repository = mock(UserRepository::class.java)
    private val service = UserService(repository)

    @Test
    fun `findAll deve retornar lista de usuarios`() {
        val users = listOf(User(id = 1, name = "Maria", email = "maria@example.com"))
        `when`(repository.findAll()).thenReturn(users)

        val result = service.findAll()
        assertEquals(1, result.size)
        assertEquals("Maria", result[0].name)
    }

    @Test
    fun `findById deve retornar usuario existente`() {
        val user = User(id = 1, name = "Joao", email = "joao@example.com")
        `when`(repository.findById(1L)).thenReturn(Optional.of(user))

        val result = service.findById(1L)
        assertEquals("Joao", result.name)
    }

    @Test
    fun `findById deve lançar excecao se nao encontrado`() {
        `when`(repository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(RuntimeException::class.java) {
            service.findById(99L)
        }
    }

    @Test
    fun `save deve persistir usuario`() {
        val user = User(name = "Ana", email = "ana@example.com")
        `when`(repository.save(user)).thenReturn(user.copy(id = 1))

        val result = service.save(user)
        assertNotNull(result.id)
        assertEquals("Ana", result.name)
    }

        @Test
    fun `delete deve chamar deleteById`() {
        `when`(repository.findById(1L)).thenReturn(Optional.of(User(id = 1, name = "Ana", email = "ana@example.com")))
        service.delete(1L)
        verify(repository, times(1)).deleteById(1L)
    }
