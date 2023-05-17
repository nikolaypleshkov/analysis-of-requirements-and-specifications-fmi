package com.stu.fmi.utils;

import com.stu.fmi.model.Customer;

import java.util.Random;

public class MockCustomer extends Customer {
    private String verificationCode = "1234";

    public MockCustomer(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
    }

    public MockCustomer(String firstName, String email) {
        super(firstName, email);
    }

    @Override
    public String sendVerificationCode() {
        verificationCode = generateVerificationCode();
        return "Кода е изпратен: " + verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int codeLength = 6;

        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            int digit = random.nextInt(10);
            codeBuilder.append(digit);
        }

        return codeBuilder.toString();
    }
}