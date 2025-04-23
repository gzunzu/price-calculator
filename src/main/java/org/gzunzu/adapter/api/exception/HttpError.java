package org.gzunzu.adapter.api.exception;

import java.time.LocalDateTime;

public record HttpError(LocalDateTime timestamp, int httpValue, String message) {
}
