package com.example

import com.example.data.AstrologyService
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val result = AstrologyService.fetchRealPanchang(10, 7, 2026, 6, 0, 0)
    println("Result data: ${result.data}")
    println("Result error: ${result.error}")
}
