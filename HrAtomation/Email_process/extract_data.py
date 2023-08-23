import logging
import os
from Email_process.email_reader import ExtractEmail, EmailExtractData
from Email_process.constants import QUERY_STRING, DOWNLOADS, CLIENT_FILE, API_NAME, API_VERSION, SCOPES
from Email_process.google_apis import create_service
import time


class EmailData:

    def __init__(self):
        self.email_messages = None
        self.save_location = None
        self.service = None

    def read_message(self, email_subject):
        try:
            self.service = create_service(CLIENT_FILE, API_NAME, API_VERSION, SCOPES)
            self.save_location = os.path.abspath(DOWNLOADS)
            self.email_messages = ExtractEmail().search_emails(QUERY_STRING)
            for email_message in self.email_messages:
                message_detail = EmailExtractData().get_message_data(email_message['id'], msg_format='full',
                                                                     metadata_headers=['parts'])
                message_detail_payload = message_detail.get('payload')
                headers = message_detail_payload.get('headers')
                subject_name = email_subject
                for data in headers:
                    if data['name'] == 'Subject':
                        subject_data = data['value']
                        if subject_data in subject_name:
                            print("subject matched")
                        else:
                            logging.warning(f'subject:{subject_data} no matched')
                            continue
                        for subject_data in message_detail_payload['parts']:
                            file_name = subject_data['filename']
                            body = subject_data['body']
                            if 'attachmentId' in body:
                                attachment_id = body['attachmentId']
                                attachment_content = EmailExtractData().get_file_detail(email_message['id'],
                                                                                        attachment_id,
                                                                                        file_name,
                                                                                        self.save_location)

                                with open(os.path.join(self.save_location, file_name), 'wb') as _f:
                                    _f.write(attachment_content)
                                print(f'File {file_name} is saved at {self.save_location}')

            for email_message in self.email_messages:
                self.service.users().messages().modify(userId='me', id=email_message['id'],
                                                       body={
                                                           'removeLabelIds': ['IMPORTANT', 'INBOX'],
                                                           'addLabelIds': ['Label_7237080672968549314']
                                                       }).execute()
            time.sleep(0.5)
        except Exception as e:
            logging.error('Error occurs to read messages from email', str(e))

# EmailData().read_message("resume")
