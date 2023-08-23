package com.rbts.hrms.candidateonboarding.kafka;

import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;

public interface KafkaWhatsappFacade {
    void sendWhatsappNotification(KafkaWhatsappMessage w, NotificationDetails notificationDetails);
}
