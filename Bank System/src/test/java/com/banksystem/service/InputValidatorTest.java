package com.banksystem.service;

import com.banksystem.util.InputValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class InputValidatorTest {

    @Test
    void testValidEmail() {
        assertTrue(InputValidator.isValidEmail("test@gmail.com"));
        assertFalse(InputValidator.isValidEmail("invalid-email"));
    }

    @Test
    void testValidPhone() {
        assertTrue(InputValidator.isValidPhone("0791234567"));
        assertFalse(InputValidator.isValidPhone("0781234"));
    }

    @Test
    void testPositiveAmount() {
        assertTrue(InputValidator.isPositiveAmount(new BigDecimal("100.00")));
        assertFalse(InputValidator.isPositiveAmount(BigDecimal.ZERO));
    }

    @Test
    void testPassword() {
        assertTrue(InputValidator.isValidPassword("Aa123456"));
        assertFalse(InputValidator.isValidPassword("weak"));
    }
    @Test
    void testValidAccountNumber() {
        assertTrue(InputValidator.isValidAccountNumber("123456789012"));
        assertFalse(InputValidator.isValidAccountNumber("123ABC789012"));
        assertFalse(InputValidator.isValidAccountNumber("12345"));
    }

}
