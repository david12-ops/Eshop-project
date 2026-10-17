package com.example.utils.utils_interface;

public interface ObjectMapper<E, D> {
    E mapToEntity(D objectDTO);

    D mapToDTO(E objectEntity);
}
