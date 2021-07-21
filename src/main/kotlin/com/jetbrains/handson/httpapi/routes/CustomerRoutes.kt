package com.jetbrains.handson.httpapi.routes

import com.jetbrains.handson.httpapi.databaseInit
import com.jetbrains.handson.httpapi.db.Customer
import com.jetbrains.handson.httpapi.dto.CustomerDto
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.customerRouting() {
    route("/customer") {
        get {
            val customersDto = transaction {
                // uses the connections initialized via Database.connect() in main!
                Customer.selectAll().map { mapToCustomerDto(it) }
            }
            if (customersDto.isNotEmpty()) {
                call.respond(customersDto)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.NotFound)
            }
        }
        get("{id}") {
            val customerId = call.parameters["id"]?.toLongOrNull() ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val customersDto = transaction {
                // uses the connections initialized via Database.connect() in main!
                Customer.select { Customer.id eq customerId }.map { mapToCustomerDto(it) }
            }
            if (customersDto.isEmpty()) {
                call.respondText(
                    "No customer with id $customerId",
                    status = HttpStatusCode.NotFound
                )
            } else {
                call.respond(customersDto)
            }
        }
        post {
            val customer = call.receive<CustomerDto>()
            val insertedCustomerId = transaction {
                Customer.insert {
                    it[firstName] = customer.firstName
                    it[lastName] = customer.lastName
                    it[email] = customer.email
                } get Customer.id // fetches the auto generated ID
            }
            call.respondText("Customer stored correctly -> $insertedCustomerId", status = HttpStatusCode.Created)
        }
        delete("{id}") {
            val customerId = call.parameters["id"]?.toLongOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)

            val deleteCount = transaction { Customer.deleteWhere { Customer.id eq customerId } }
            if (deleteCount > 0) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}

fun mapToCustomerDto(it: ResultRow) = CustomerDto(
    id = it[Customer.id],
    firstName = it[Customer.firstName],
    lastName = it[Customer.lastName],
    email = it[Customer.email]
)


fun Application.registerCustomerRoutes() {
    databaseInit()
    routing {
        customerRouting()
    }
}

