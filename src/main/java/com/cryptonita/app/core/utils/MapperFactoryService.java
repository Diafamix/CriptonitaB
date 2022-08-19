package com.cryptonita.app.core.utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapperFactoryService {

    /**
     * Utility method to easily map attributes that
     * match to one POJO to another POJO
     *
     * NOTE: the fields should be final, if not, an exception
     * will we throw
     *
     * @param t the POJO to get
     * @param r the POJO to
     */
    @SneakyThrows
    public static <T, R> void doMap(T t, R r) throws RuntimeException {
        Map<String, Field> fieldsMap = new HashMap<>();
        for (Field declaredField : r.getClass().getDeclaredFields())
            fieldsMap.put(declaredField.getName(), declaredField);

        for (Field declaredField : t.getClass().getDeclaredFields()) {
            Field anotherField;
            if ((anotherField = fieldsMap.get(declaredField.getName())) == null) continue;
            if (!anotherField.getType().equals(declaredField.getType())) continue;

            anotherField.setAccessible(true);
            anotherField.set(r, declaredField.get(t));
        }

    }

}
