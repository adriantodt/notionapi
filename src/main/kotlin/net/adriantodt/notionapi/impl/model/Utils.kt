package net.adriantodt.notionapi.impl.model

import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset.UTC

internal fun String.tryParseDate(): Pair<OffsetDateTime?, LocalDate?> {
    return runCatching {
        OffsetDateTime.parse(this) to null
    }.getOrElse {
        runCatching {
            Instant.parse(this).atOffset(UTC) to null
        }.getOrElse {
            runCatching {
                null to LocalDate.parse(this)
            }.getOrNull() ?: throw IllegalStateException("Could not parse Date from '$this'.")
        }
    }
}

internal fun String.toOffsetDateTime(): OffsetDateTime {
    return runCatching {
        OffsetDateTime.parse(this)
    }.getOrElse {
        runCatching {
            Instant.parse(this).atOffset(UTC)
        }.getOrElse {
            runCatching {
                LocalDate.parse(this).atStartOfDay().atOffset(UTC)
            }.getOrNull() ?: throw IllegalStateException("Could not parse OffsetDateTime from '$this'.")
        }
    }
}

internal fun String.toLocalDate(): LocalDate {
    return runCatching {
        LocalDate.parse(this)
    }.getOrElse {
        runCatching {
            OffsetDateTime.parse(this).toLocalDate()
        }.getOrElse {
            runCatching {
                LocalDate.ofInstant(Instant.parse(this), UTC)
            }.getOrNull() ?: throw IllegalStateException("Could not parse LocalDate from '$this'.")
        }
    }
}
