package com.reservation.api.config.support.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisExecutor {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public Optional<String> findByKey(String key) {
        try {
            if (StringUtils.isBlank(key) || Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
                return Optional.empty();
            }

            return Optional.ofNullable(String.valueOf(redisTemplate.opsForValue().get(key)));
        } catch (Exception e) {
            printExceptionLog("Redis Exception", e);
            return Optional.empty();
        }
    }

    public <T> Optional<T> findByKey(String key, Class<T> returnType) {
        try {
            if (StringUtils.isBlank(key) || Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
                return Optional.empty();
            }

            String value = String.valueOf(redisTemplate.opsForValue().get(key));
            return Optional.ofNullable(objectMapper.readValue(value, returnType));
        } catch (JsonProcessingException e) {
            printExceptionLog("Fail ObjectMapper from String", e);
            return Optional.empty();
        } catch (Exception e) {
            printExceptionLog("Redis Exception", e);
            return Optional.empty();
        }
    }

    public <T> void setValue(String key, T value) {
        try {
            if (StringUtils.isBlank(key) || value == null) {
                throw new RuntimeException("key or value is null");
            }

            String setValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, setValue);
        } catch (JsonProcessingException e) {
            printExceptionLog("Fail ObjectMapper to String", e);
        } catch (Exception e) {
            printExceptionLog("Redis Exception", e);
        }
    }

    public <T> void setValue(String key, T value, Duration ttl) {
        try {
            if (StringUtils.isBlank(key) || value == null) {
                throw new RuntimeException("key or value is null");
            }

            String setValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, setValue, ttl);
        } catch (JsonProcessingException e) {
            printExceptionLog("Fail ObjectMapper to String", e);
        } catch (Exception e) {
            printExceptionLog("Redis Exception", e);
        }
    }

    private void printExceptionLog(String message, Exception e) {
        log.error("{}: {}", message, e.getMessage());
    }
}
