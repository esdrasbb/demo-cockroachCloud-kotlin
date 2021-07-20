import com.jetbrains.handson.httpapi.module
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import kotlin.test.assertEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CustomerTest {

    @Test
    fun a_testCustomerNotFound() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/customer").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }

    @Test
    fun b_testCustomerInsert() {
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

    @Test
    fun c_testCustomerFound() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/customer").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

}