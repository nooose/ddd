package wolfdesk.support

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val DATE_TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

fun LocalDateTime.simpleFormat(): String {
    return this.format(DATE_TIME_FORMAT)
}
