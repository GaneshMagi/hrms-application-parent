from typing import List
from Email_process.google_apis import create_service
from Email_process.constants import CLIENT_FILE, API_NAME, API_VERSION, SCOPES
from Email_process.exception import NoEmailFound,DataNotFoundError
import base64
import logging


class ExtractEmail:
    def __init__(self):

        self.service = create_service(CLIENT_FILE, API_NAME, API_VERSION, SCOPES)

    def __search_gmail(self, query_string: str, label_ids: List = 'INBOX', pageToken=None):
        try:
            message_list_response = self.service.users().messages().list(
                userId='me',
                labelIds=label_ids,
                q=query_string,
                pageToken=pageToken
            ).execute()
            message_items = message_list_response.get('messages')
            next_page_token = message_list_response.get('nextPageToken')
            return message_items, next_page_token
        except Exception as e:
            logging.error("Error to search gmail in inbox", str(e))

    def search_emails(self, query_string: str, label_ids: List = 'INBOX'):
        try:
            message_items, next_page_token = self.__search_gmail(query_string)

            while next_page_token:
                message_data, next_page_token = self.__search_gmail(query_string=query_string, label_ids=label_ids,
                                                                    pageToken=next_page_token)

                message_items.extend(message_data)

            return message_items
        except Exception as e:
            logging.error("Error to search emails in inbox", str(e))
            raise NoEmailFound('No emails returned')


class EmailExtractData:

    def __init__(self):
        self.service = create_service(CLIENT_FILE, API_NAME, API_VERSION, SCOPES)
    try:
        def get_file_detail(self, message_id, attachment_id, file_name, save_location):
            try:
                response = self.service.users().messages().attachments().get(
                    userId='me',
                    messageId=message_id,
                    id=attachment_id
                ).execute()

                file_data = base64.urlsafe_b64decode(response.get('data').encode('UTF-8'))
                return file_data
            except Exception as e:
                logging.error("Error to get file details", str(e))

        def get_message_data(self, message_id, msg_format='metadata', metadata_headers: List = None):
            try:
                message_detail = self.service.users().messages().get(
                    userId='me',
                    id=message_id,
                    format=msg_format,
                    metadataHeaders=metadata_headers
                ).execute()
                return message_detail
            except Exception as e:
                logging.error("Error to get message data from email service", str(e))
    except Exception as e:
        raise DataNotFoundError()