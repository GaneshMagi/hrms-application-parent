package com.rbts.hrms.candidateonboarding.kafka;

import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.rbts.hrms.candidateonboarding.kafka.AppConstants.TOPIC_NAME_WHATSAPP;

@Service("kafkaWhatsappFacade")
public class KafkaWhatsappFacadeImpl implements KafkaWhatsappFacade{

    @Autowired
    KafkaTemplate<Object, KafkaWhatsappMessage> kafkaTemplate1;

    @Override
    public void sendWhatsappNotification(KafkaWhatsappMessage w, NotificationDetails notificationDetails) {

        w.setNotification_Type(TOPIC_NAME_WHATSAPP);
        w.setMessage(notificationDetails.getMessage());
        w.setFrom(notificationDetails.getFromMail());
        w.setTo(notificationDetails.getToMail());
        w.setImage_path(notificationDetails.getAttachment_path());
        kafkaTemplate1.send(TOPIC_NAME_WHATSAPP,w);

    }
}
