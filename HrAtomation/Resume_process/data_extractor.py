from Resume_process.constant import email_regex, phone_regex, url_regex, name_match_words_list, regex_mob_no, \
    regex_email, regex_url, regex_email2
import glob
from Resume_process.constant import Pdf_path, Word_path
from pdfminer.high_level import extract_text
import re, os
import logging


class DataExtractor:

    @staticmethod
    def extract_email(data):
        """ return emails in resume """
        try:
            email = re.findall(email_regex, data)
            return email[0] if email else None
        except Exception as e:
            logging.error("Error to get email id from resume", str(e))

    @staticmethod
    def extract_mob_no(data):
        """ return mob no.in resume """
        try:
            phone = re.findall(phone_regex, data)
            return phone[0] if phone else None
        except Exception as e:
            logging.error("Error to get mobile number from resume", str(e))

    @staticmethod
    def extract_alt_mob_no(data):
        try:
            alt_phone = re.findall(phone_regex, data)
            if len(alt_phone) > 1:
                return alt_phone[1]
            else:
                return None
        except Exception as e:
            logging.error("Error to get alternative mobile number", str(e))

    @staticmethod
    def extract_url(data):
        """ return urls in resume"""
        try:
            url = re.findall(url_regex, data)
            return url[0] if url else None
        except Exception as e:
            logging.error("Error to get url in resume", str(e))

    @staticmethod
    def extract_name(data):
        """ return name of student/employee """

        try:
            text = data.split('\n')
            new_text = [item.strip() for item in text]
            name = []
            without = []

            for item in new_text:
                res = re.sub(' +', '  ', item)
                split_list = res.split("  ") and res.split("EMAIL")
                for char in split_list:
                    if ' ' in char:
                        count = char.count(' ')
                        if count >= 3:
                            space = char.replace('  ', "  ")
                            name.append(space)
                if item in name_match_words_list:
                    item.replace(':', "")
                    continue
                else:
                    name.append(item)
            for text in name:
                phone_no = re.findall(regex_mob_no, text)
                email = re.findall(regex_email, text)
                url = re.findall(regex_url, text)
                email2 = re.findall(regex_email2, text)
                if email or url or email2 or phone_no:
                    continue
                else:
                    without.append(text)
            try:
                name_list = []
                myList = str(without[0])
                split_list = myList.split()
                for ele in split_list:
                    if ele in name_match_words_list:
                        continue
                    else:
                        name_list.append(ele)
                final = name_list[0:3]
                return ' '.join(final).upper()
            except Exception as e:
                logging.error('exception occur ', str(e))

        except Exception as e:
            logging.error("Error occurs to get candidate name from resume", str(e))

    @staticmethod
    def extract_file_path(file):
        try:
            path = os.path.basename(file)
            return path
        except Exception as e:
            logging.error("Error to get file name of resume", str(e))


"""list_of_files = glob.glob(Pdf_path)
for file in list_of_files:
    data = extract_text(file)
    
    obj = DataExtractor().extract_name(data)
    print(obj)"""