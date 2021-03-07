package com.avalon.vflapp.util

import java.util.*

/* ClaimsMapper might be updated. */

object RoleTranslators {

    fun fromEnglish(role: String): String = when (role.toLowerCase(Locale.ROOT)) {
        "admin" -> "Yönetici"
        "superadmin" -> "Üst yönetici"
        "teacher" -> "Öğretmen"
        "parent" -> "Veli"
        "student" -> "Öğrenci"
        else -> "Tanımsız"
    }

    fun toEnglish(role: String): String = when(role.toLowerCase(Locale.ROOT)) {
        "yönetici" -> "admin"
        "üst yönetici" -> "superadmin"
        "öğretmen" -> "teacher"
        "veli" -> "parent"
        "öğrenci" -> "student"
        else -> "undefined"
    }

}


object ClaimsMapper {
    fun fromClaims(claims: Map<String, Any>): String {
        val isAdmin = claims["admin"] as Boolean?
        val isSuperAdmin = claims["superadmin"] as Boolean?
        val isTeacher = claims["teacher"] as Boolean?
        val isParent = claims["parent"] as Boolean?
        val isStudent = claims["student"] as Boolean?
        if (isAdmin == true) {
            return "admin"
        }
        if (isSuperAdmin == true) {
            return "superadmin"
        }
        if (isTeacher == true) {
            return "teacher"
        }
        if (isParent == true) {
            return "parent"
        }
        if (isStudent == true) {
            return "student"
        }
        return "undefined"
    }

}