package org.company.exchange.constant;

public enum CurrencyEnum {
    RUB("RUB"), USD("USD"), CNY("CNY");

    final String name;

    CurrencyEnum(String name) {
        this.name = name;
    }
}
