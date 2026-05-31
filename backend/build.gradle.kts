plugins {
	java
	id("org.springframework.boot") version "4.0.6"
	id("io.spring.dependency-management") version "1.1.7"
	id("nu.studer.jooq") version "9.0"
}

group = "social.marco"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springAiVersion"] = "2.0.0-M8"
val jooqVersion = "3.19.32"
val jooqDbUrl = providers.environmentVariable("JOOQ_DB_URL")
	.orElse("jdbc:postgresql://localhost:5432/dotodo")
val jooqDbUser = providers.environmentVariable("JOOQ_DB_USER")
	.orElse("dotodo")
val jooqDbPassword = providers.environmentVariable("JOOQ_DB_PASSWORD")
	.orElse("securepassword")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-liquibase")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.ai:spring-ai-starter-model-openai")
	jooqGenerator("org.postgresql:postgresql")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-liquibase-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jooq {
	version.set(jooqVersion)
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
	configurations {
		create("main") {
			generateSchemaSourceOnCompilation.set(true)
			jooqConfiguration.apply {
				logging = org.jooq.meta.jaxb.Logging.WARN
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = jooqDbUrl.get()
					user = jooqDbUser.get()
					password = jooqDbPassword.get()
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "public"
					}
					generate.apply {
						isDeprecated = false
						isRecords = true
						isImmutablePojos = true
						isFluentSetters = true
					}
					target.apply {
						packageName = "social.marco.dotodo.jooq"
						directory = "build/generated-src/jooq/main"
					}
				}
			}
		}
	}
}
