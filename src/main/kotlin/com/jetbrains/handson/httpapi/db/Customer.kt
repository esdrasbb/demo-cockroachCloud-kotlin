package com.jetbrains.handson.httpapi.db

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Customer : Table("customer") {
    val id: Column<Long> = long("id").autoIncrement()
    val firstName: Column<String> = varchar("first_name", 256)
    val lastName: Column<String> = varchar("last_name", 256)
    val email: Column<String> = varchar ("e_mail", 256)
    override val primaryKey = PrimaryKey(id)
}