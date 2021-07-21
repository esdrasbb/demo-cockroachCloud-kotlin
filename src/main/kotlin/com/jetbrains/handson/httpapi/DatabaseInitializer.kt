package com.jetbrains.handson.httpapi

import com.jetbrains.handson.httpapi.DatabaseInitializer.createSchemaAndTestData
import com.jetbrains.handson.httpapi.db.Customer
import com.jetbrains.handson.httpapi.dto.CustomerDto
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object DatabaseInitializer {
    private val logger: Logger = LoggerFactory.getLogger(DatabaseInitializer::class.java)

    fun createSchemaAndTestData() {
        logger.info("Creating/Updating schema")

        transaction {
            SchemaUtils.createMissingTablesAndColumns(Customer)
        }

        val totalCustomers = transaction {
            Customer.selectAll().count()
        }

        if (totalCustomers > 0) {
            logger.info("There appears to be data already present, not inserting test data!")
            return
        }

        logger.info("Inserting customers")

        val johny = CustomerDto(null, "Johny", "Silva", "johny.silva@company.com")
        val bartolomeu = CustomerDto(null, "Bartolomeu", "Guimaraens", "bartolomeu.guimaraes@company.com")
        val miguelito = CustomerDto(null, "Miguelito", "Patropi", "miguelito.patropi@company.com")

        val customers = listOf(
            johny,
            bartolomeu,
            miguelito
        )

        transaction {
            SchemaUtils.create(Customer)

            // batch insert items
            Customer.batchInsert(customers) {
                this[Customer.firstName] = it.firstName
                this[Customer.lastName] = it.lastName
                this[Customer.email] = it.email
            }
        }
    }
}

//Database settings
val url = "jdbc:postgresql://<HOST>:26257/<DATABASE_NAME>?sslmode=verify-full&sslrootcert=<CRT_PATH>"
val user = "<USER>"
val password = "<PASSWORD>"
val driver = "org.postgresql.Driver"

fun databaseInit() {

    val db = Database.connect(url, driver, user, password)
    db.useNestedTransactions = true // see https://github.com/JetBrains/Exposed/issues/605

    createSchemaAndTestData()
}