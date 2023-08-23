package com.rbts.hrms.candidateonboarding.kafka;

import lombok.Data;

@Data
public class KafkaSmsMessage {

   String Notification_Type;
   String to_number;
   String  messages;
}
