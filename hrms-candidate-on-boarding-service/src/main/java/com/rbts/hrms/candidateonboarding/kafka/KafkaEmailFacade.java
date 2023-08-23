package com.rbts.hrms.candidateonboarding.kafka;

import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;

public interface KafkaEmailFacade {

    void sendMailNotification(KafkaEmailMessage message, NotificationDetails notificationDetails);
}
