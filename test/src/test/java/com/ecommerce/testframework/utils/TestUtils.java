package com.ecommerce.testframework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

public class TestUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public static String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static ResultActions performPost(MockMvc mockMvc, String url, Object requestBody) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(requestBody)));
    }

    public static ResultActions performGet(MockMvc mockMvc, String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url));
    }

    public static ResultActions performPut(MockMvc mockMvc, String url, Object requestBody) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(requestBody)));
    }

    public static ResultActions performDelete(MockMvc mockMvc, String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(url));
    }

    public static Map<String, Object> createOrderRequest(String email, String amount) {
        return Map.of(
            "customerEmail", email,
            "amount", amount
        );
    }

    public static Map<String, Object> createPaymentRequest(Long orderId, String amount, String paymentMethod) {
        return Map.of(
            "orderId", orderId,
            "amount", amount,
            "paymentMethod", paymentMethod
        );
    }

    public static Map<String, String> createStatusUpdateRequest(String status) {
        return Map.of("status", status);
    }
}
