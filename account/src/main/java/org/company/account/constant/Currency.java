package org.company.account.constant;

public enum Currency {
    RUB("RUB"), USD("USD"), CNY("CNY");

    final String name;

    Currency(String name) {
        this.name = name;
    }
}
