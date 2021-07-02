package com.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class YamlUtils {

    private static final YAMLFactory YAML_FACTORY = new YAMLFactory();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(YAML_FACTORY);

    static {
        OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
    }

    public static <T> List<T> readObjectsFromFile(InputStream stream, Class<T> objectType) throws IOException {
        return OBJECT_MAPPER.readValues(YAML_FACTORY.createParser(stream), objectType).readAll();
    }
}
