package org.company.cash.service;

public interface NotificationOutboxService {

    void createMessage(String login, String message);
    void sendNotification();
}
