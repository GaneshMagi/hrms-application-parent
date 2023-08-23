package com.rbts.hrms.candidateonboarding.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class KafkaEmailMessage {

    String Notification_Type;
    String From;
    String To;
    String Cc;
    String Bcc;
    String Subject;
    String Body;
    String Attachment_path;


    public KafkaEmailMessage(String emailNotification, String s, String s1, String test, String test1, String nothing, String ok, String s2) {
    }

    public KafkaEmailMessage() {
    }
}
