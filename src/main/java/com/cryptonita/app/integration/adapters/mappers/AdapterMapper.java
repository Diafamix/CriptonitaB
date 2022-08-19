package com.cryptonita.app.integration.adapters.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface AdapterMapper <T> {

    T mapToDto(String s);

    List<T> mapManyToDto(String s);

}
