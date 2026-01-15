package com.dev.taskflow.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    PENDENTE,
    EM_ANDAMENTO,
    CONCLUIDO;

    @JsonCreator
    public static TaskStatus fromString(String status) {
        if (status == null) return null;
        try {
            return TaskStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status não encontrado: " + status);
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
