package com.example.hci_mobile.api.data.model

import java.util.Date

data class User(
    var id: Int?,
    var firstName: String,
    var lastName: String,
    var email: String,
    var birthDate: Date,
    var password: String?
){
    override fun equals(other: Any?): Boolean {
        return (
                other is User &&
                        other.email == email
                )
    }

    override fun hashCode(): Int {  //TODO: es necesario??
        var result = id ?: 0
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + birthDate.hashCode()
        result = 31 * result + (password?.hashCode() ?: 0)
        return result
    }
}