package com.example.utils.utils_interface;

import org.springframework.lang.NonNull;

public interface ObjectMapper<E, D> {

    @NonNull
    E mapToEntity(D objectDTO);

    D mapToDTO(E objectEntity);
}
