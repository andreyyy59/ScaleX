package me.proyecto.scalex.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultTest {

    @Test
    fun `Success holds data`() {
        val result: Result<String> = Result.Success("test")

        assertTrue(result is Result.Success)
        assertEquals("test", (result as Result.Success).data)
    }

    @Test
    fun `Error holds exception`() {
        val exception = Exception("error message")
        val result: Result<String> = Result.Error(exception)

        assertTrue(result is Result.Error)
        assertEquals("error message", (result as Result.Error).exception.message)
    }

    @Test
    fun `getOrNull returns data for Success`() {
        val result: Result<String> = Result.Success("data")

        assertEquals("data", result.getOrNull())
    }

    @Test
    fun `getOrNull returns null for Error`() {
        val result: Result<String> = Result.Error(Exception("error"))

        assertNull(result.getOrNull())
    }

    @Test
    fun `getOrDefault returns data for Success`() {
        val result: Result<String> = Result.Success("data")

        assertEquals("data", result.getOrDefault("default"))
    }

    @Test
    fun `getOrDefault returns default for Error`() {
        val result: Result<String> = Result.Error(Exception("error"))

        assertEquals("default", result.getOrDefault("default"))
    }

    @Test
    fun `map transforms Success data`() {
        val result: Result<Int> = Result.Success(42)
        val mapped = result.map { it * 2 }

        assertTrue(mapped is Result.Success)
        assertEquals(84, (mapped as Result.Success).data)
    }

    @Test
    fun `map propagates Error`() {
        val result: Result<Int> = Result.Error(Exception("error"))
        val mapped = result.map { it * 2 }

        assertTrue(mapped is Result.Error)
    }

    @Test
    fun `onSuccess is called for Success`() {
        val result: Result<String> = Result.Success("hello")
        var called = false

        result.onSuccess { called = true }

        assertTrue(called)
    }

    @Test
    fun `onError is called for Error`() {
        val result: Result<String> = Result.Error(Exception("error"))
        var called = false

        result.onError { called = true }

        assertTrue(called)
    }
}
