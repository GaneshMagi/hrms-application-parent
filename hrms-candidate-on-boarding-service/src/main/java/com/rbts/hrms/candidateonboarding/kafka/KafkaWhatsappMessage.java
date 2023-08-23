package com.rbts.hrms.candidateonboarding.kafka;

import lombok.Data;

@Data
public class KafkaWhatsappMessage {


    String Notification_Type;
    String Message;
    String  From;
    String  To;
    String Image_path;
}
