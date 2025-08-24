package org.company.transfer.service;

public interface NotificationOutboxService {

    void createMessage(String login, String message);
    void sendNotification();
}
