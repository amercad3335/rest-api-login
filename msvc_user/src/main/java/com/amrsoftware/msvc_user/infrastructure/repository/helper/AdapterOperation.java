package com.amrsoftware.msvc_user.infrastructure.repository.helper;

import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;

@SuppressWarnings("unchecked")
public abstract class AdapterOperation<E, D, R> {
    protected final R repository;
    protected final ObjectMapper mapper;
    protected Class<E> entity;
    protected Class<D> dto;

    public AdapterOperation(R repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        entity = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        dto = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected E toEntity(D dto) {
        return mapper.map(dto, entity);
    }

    protected D toDto(E entity) {
        return mapper.map(entity, dto);
    }
}
