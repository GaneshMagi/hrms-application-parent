package com.rbts.hrms.candidateonboarding.kafka;

import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.rbts.hrms.candidateonboarding.kafka.AppConstants.TOPIC_NAME_SMS;

@Service("kafkaSmsFacade")
public class KafkaSmsFacadeImpl implements KafkaSmsFacade{

    @Autowired
    KafkaTemplate<Object, KafkaSmsMessage> kafkaTemplate2;

    @Override
    public void sendSmsNotification(KafkaSmsMessage m, NotificationDetails notificationDetails) {
        m.setNotification_Type(TOPIC_NAME_SMS);
        m.setTo_number(notificationDetails.getToMail());
        m.setMessages(notificationDetails.getMessage());
        kafkaTemplate2.send(TOPIC_NAME_SMS,m);
    }
}
