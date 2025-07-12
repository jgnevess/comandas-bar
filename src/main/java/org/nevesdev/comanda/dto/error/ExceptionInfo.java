package org.nevesdev.comanda.dto.error;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionInfo {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;

    public ExceptionInfo(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
