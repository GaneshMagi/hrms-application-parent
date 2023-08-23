package com.rbts.hrms.candidateonboarding.kafka;

import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;

public interface KafkaSmsFacade {
    void sendSmsNotification(KafkaSmsMessage m, NotificationDetails notificationDetails);
}
