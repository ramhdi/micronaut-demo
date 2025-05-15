package com.example

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License

@OpenAPIDefinition(
	info = Info(
		title = "Books API",
		version = "1.0",
		description = "Books API Documentation",
		license = License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
		contact = Contact(name = "API Support", email = "support@example.com")
	)
)
object Application {
	@JvmStatic
	fun main(args: Array<String>) {
		Micronaut.run(Application::class.java, *args)
	}
}