package org.example.bankapp;

import org.springframework.boot.SpringApplication;

public class TestBankappApplication {

    public static void main(String[] args) {
        SpringApplication.from(BankappApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
