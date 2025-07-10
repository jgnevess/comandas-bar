package org.nevesdev.comanda.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorInfo {
    private Integer status;
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;

    public ErrorInfo(Integer status, String message, String errorCode) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
