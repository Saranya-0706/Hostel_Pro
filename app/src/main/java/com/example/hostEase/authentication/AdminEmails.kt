package com.example.hostEase.authentication

class AdminEmails {
    companion object{
        private val  adminEmails = listOf(
            "saranya@gmail.com",
            "admin00@gmail.com"
        )
        fun isAdminEmail(email : String): Boolean{
            return email in adminEmails
        }

    }
}