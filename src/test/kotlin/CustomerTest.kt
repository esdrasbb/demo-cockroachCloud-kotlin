import com.jetbrains.handson.httpapi.module
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class CustomerTest {

    @Test
    fun testCustomerNotFound() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/customer").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }

    @Test
    fun testCustomerInsert() {
        withTestApplication({ module(testing = true) }) {
            with(handleRequest(HttpMethod.Post, "/customer"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("{\"id\":\"200\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"email\":\"john.smith@company.com\"}")
            }) {
                assertEquals("Customer stored correctly", response.content)
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }
}