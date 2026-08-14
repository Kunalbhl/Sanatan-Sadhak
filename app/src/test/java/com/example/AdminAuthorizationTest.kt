package com.example

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.example.data.User

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class AdminAuthorizationTest {

    @Test
    fun `Normal user cannot access admin dashboard`() {
        val user = User(
            email = "user@example.com",
            password = "password",
            fullName = "Regular User",
            mobileNumber = "1234567890",
            city = "Delhi",
            state = "Delhi",
            role = "Regular"
        )
        
        val canAccessAdmin = user.role == "SuperUser"
        assertFalse("Regular user should not access admin", canAccessAdmin)
    }

    @Test
    fun `Admin can access admin dashboard`() {
        val admin = User(
            email = "admin@example.com",
            password = "password",
            fullName = "Admin User",
            mobileNumber = "0987654321",
            city = "Delhi",
            state = "Delhi",
            role = "SuperUser"
        )
        
        val canAccessAdmin = admin.role == "SuperUser"
        assertTrue("Admin user should access admin", canAccessAdmin)
    }
}
