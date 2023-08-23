import re
from Resume_process.constant import *
import logging
import glob
from Resume_process.constant import Pdf_path, Word_path
from pdfminer.high_level import extract_text
from datetime import datetime


class BirthExtractor:
    @staticmethod
    def extract_birthdate(data):
        """ to extract birth date from resumes"""
        try:
            data = data.split('\n')
            new1 = [item.rstrip() for item in data]
            to_uppercase = []  # upper case list

            for data1 in new1:
                """ return all data in uppercase """
                to_uppercase.append(data1.upper())

            regex = [date_format1, date_format2, date_format3, date_format4,
                     date_format5, date_format6, date_format7]

            for i in range(len(to_uppercase)):
                for birth_date in dob_related_words:
                    if birth_date in to_uppercase[i]:
                        birth = to_uppercase[i:]
                        for regx in regex:
                            reg = re.findall(regx, str(birth))
                            if reg:
                                date_string = reg[0]
                                # Remove the ordinal suffix from the day component
                                date_string = date_string.replace("TH", "").replace("ST", "").replace("ND", "").replace(
                                    "RD", "")
                                for format_string in date_format_list:
                                    try:
                                        date_object = datetime.strptime(date_string, format_string)
                                        formatted_date = date_object.strftime("%Y-%m-%d")
                                        return formatted_date
                                    except ValueError as e:
                                        logging.exception("Error to convert into formatted date: %s", str(e))
                                else:
                                    date_string = reg[0]
                                    date = date_string.replace("/", '-')
                                    date_object = datetime.strptime(date, format_string)
                                    formatted_date = date_object.strftime("%Y-%m-%d")
                                    return formatted_date
                        else:
                            try:
                                split_list = to_uppercase[i].split()
                                for i in range(len(split_list)):
                                    if 'BIRTH' in split_list[i]:
                                        dob = ' '.join(split_list[i+1:])
                                        try:
                                            if not dob.isspace():
                                                date_string = dob.lstrip(":").strip()
                                                date_string = date_string.replace("ST", "").replace("ND", "").replace(
                                                    "RD", "").replace("TH", "")
                                                for format_string in date_format_list:
                                                    try:
                                                        date_object = datetime.strptime(date_string, format_string)
                                                        formatted_date = date_object.strftime("%Y-%m-%d")
                                                        return formatted_date
                                                    except ValueError:
                                                        pass


                                        except Exception as e:
                                            logging.error("Error to find out birth date: %s" + str(e))
                                        return None
                            except Exception as e:
                                logging.error("error in birth date: %s", str(e))

        except Exception as e:
            logging.error("Error to get birth date from resume", str(e))


"""list_of_files = glob.glob(Pdf_path)
for file in list_of_files:
    data = extract_text(file)
    obj = BirthExtractor().extract_birthdate(data)
    print(obj)"""
