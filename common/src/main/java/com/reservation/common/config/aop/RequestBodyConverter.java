package com.reservation.common.config.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestBodyConverter {
    private static final Set<Class<? extends Annotation>> TARGET_ANNOTATIONS;
    private static final Set<HttpMethod> TARGET_METHODS;

    static {
        TARGET_ANNOTATIONS = Set.of(RequestBody.class, ModelAttribute.class);
        TARGET_METHODS = Set.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH);
    }

    public static Map<String, Object> convert(HttpServletRequest request, JoinPoint joinPoint) {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Object[] methodArguments = joinPoint.getArgs();
        Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();

        for (int argumentIdx = 0, totalSize = parameterAnnotations.length; argumentIdx < totalSize; argumentIdx++) {
            Annotation[] annotation = parameterAnnotations[argumentIdx];
            putBodyWhenTargetAnnotationExists(HttpMethod.valueOf(request.getMethod()), annotation, methodArguments[argumentIdx], requestBody);
        }

        return requestBody;
    }

    private static void putBodyWhenTargetAnnotationExists(HttpMethod httpMethod, Annotation[] parameterAnnotation, Object argument, Map<String, Object> requestBody) {
        if (containsAnyTargetAnnotation(parameterAnnotation) && isTargetHttpMethod(httpMethod)) {
            String simpleName = argument.getClass().getSimpleName();
            requestBody.put(simpleName, argument);
        }
    }

    private static boolean containsAnyTargetAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .anyMatch(annotation -> TARGET_ANNOTATIONS.contains(annotation.annotationType()));
    }

    private static boolean isTargetHttpMethod(HttpMethod httpMethod) {
        return TARGET_METHODS.contains(httpMethod);
    }
}
