package com.assignment.todo.dto;

import com.assignment.todo.enums.State;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.web.SortDefault;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import static com.assignment.todo.constants.Constants.*;

@RedisHash(TODO)
public record Todo(
        @Min(value= 1, message = ID_ERROR_MESSAGE)
        long id,

        @Min(value= 1, message = AUTHENTICATION_ERROR_MESSAGE)
        long userId,

        @NotEmpty(message = DESCRIPTION_ERROR_MESSAGE)
        String description,

        LocalDate dueDate,

        @NotNull(message = STATE_MISSING_ERROR_MESSAGE)
        State state
) implements Serializable {
}
