package com.rbts.hrms.candidateonboarding.kafka;

import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.rbts.hrms.candidateonboarding.kafka.AppConstants.TOPIC_NAME_EMAIL;

@Service("kafkaMailFacade")
public class KafkaEmailFacadeImpl implements KafkaEmailFacade{

    @Autowired
    KafkaTemplate<Object, KafkaEmailMessage> kafkaTemplate;
    @Override
    public void sendMailNotification(KafkaEmailMessage s, NotificationDetails notificationDetails) {

        String topic=TOPIC_NAME_EMAIL;
        s.setNotification_Type(topic);
        s.setTo(notificationDetails.getToMail());
        s.setFrom(notificationDetails.getFromMail());
        s.setCc(notificationDetails.getCc());
        s.setBcc(notificationDetails.getBcc());
        s.setBody(notificationDetails.getBody());
        s.setSubject(notificationDetails.getSubject());
        s.setAttachment_path(notificationDetails.getAttachment_path());

        System.out.println("**************************");
        kafkaTemplate.send(TOPIC_NAME_EMAIL,s);
    }
}
