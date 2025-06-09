package com.reservation.api.config.aop;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartFileIgnoreSerializer extends JsonSerializer<MultipartFile> {
    private static final String DEFAULT_MULTIPART_FILE_STRING = "multipart file";
    private static final String EMPTY_MULTIPART_FILE_STRING = "empty multipart file";

    @Override
    public void serialize(MultipartFile multipartFile, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            if (multipartFile == null || multipartFile.isEmpty()) {
                jsonGenerator.writeString(EMPTY_MULTIPART_FILE_STRING);
            } else {
                jsonGenerator.writeString(multipartFile.getOriginalFilename());
            }
        } catch (Exception e) {
            jsonGenerator.writeString(DEFAULT_MULTIPART_FILE_STRING);
        }
    }

    @Override
    public Class<MultipartFile> handledType() {
        return MultipartFile.class;
    }

    public static SimpleModule getModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(MultipartFile.class, new MultipartFileIgnoreSerializer());
        return module;
    }
}
