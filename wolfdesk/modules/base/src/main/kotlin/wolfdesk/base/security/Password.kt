package wolfdesk.base.security

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import jakarta.persistence.Embeddable
import wolfdesk.base.support.sha256Encrypt

private class PasswordDeserializer : JsonDeserializer<Password>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext) = Password(parser.text)
}

private class PasswordSerializer : JsonSerializer<Password>() {
    override fun serialize(password: Password, genernator: JsonGenerator, serializers: SerializerProvider) {
        genernator.writeString(password.value)
    }
}

@JsonSerialize(using = PasswordSerializer::class)
@JsonDeserialize(using = PasswordDeserializer::class)
@Embeddable
data class Password(
    var value: String
) {
    init {
        require(value.isNotBlank()) { "Password cannot be blank" }
        value = sha256Encrypt(value)
    }

    companion object {
        const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*[!@#\$%^&*()_+{}:<>?])[A-Za-z\\d!@#\$%^&*()_+{}:<>?]{10,24}$"
    }
}
