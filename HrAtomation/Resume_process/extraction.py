import glob
import docx2txt
import logging
import os
from Resume_process.constant import Pdf_path, Word_path, doc_path
from pdfminer.high_level import extract_text
import win32com.client as win32
from Resume_process.resume_extractor import ResumeProcessor


class Processor:
    def __init__(self):
        self.ResumeProcessor = ResumeProcessor
        self.msg_list = []

    def __pdf(self, schema_name):
        try:
            self.list_of_files = glob.glob(Pdf_path)
            for file in self.list_of_files:
                data = extract_text(file)
                msg = self.ResumeProcessor().add_data_in_table(data, schema_name)
                self.msg_list.append(msg)

        except Exception as e:
            logging.error("error to get data from pdf format resume", str(e))

    def __word(self, schema_name):
        try:
            self.list_of_files = glob.glob(Word_path)
            for file in self.list_of_files:
                data = docx2txt.process(file)
                msg = self.ResumeProcessor().add_data_in_table(data, schema_name)
                self.msg_list.append(msg)
        except Exception as e:
            logging.error("error to get data from word format resume", str(e))

    def __convert_doc_to_docx(self):
        try:
            self.list_of_files = glob.glob(doc_path)
            for file_path in self.list_of_files:
                normalized_path = os.path.normpath(file_path)
                # Create a new filename for the converted file
                new_file_path = os.path.splitext(normalized_path)[0] + ".docx"

                # Create an instance of the Word application
                word_app = win32.Dispatch("Word.Application")

                # Open the .doc file
                doc = word_app.Documents.Open(normalized_path)

                # Save the .doc file as .docx
                doc.SaveAs2(new_file_path, FileFormat=16)

                # Close the .doc file
                doc.Close()

                # Quit the Word application
                word_app.Quit()

                # remove doc file from folder
                os.remove(normalized_path)
                return new_file_path
        except Exception as e:
            logging.error("Error to convert doc file into docx", str(e))

    def cv_process(self, schema_name):
        try:
            self.__convert_doc_to_docx()

            self.__pdf(schema_name)
            self.__word(schema_name)
            if len(self.msg_list) > 0:
                return self.msg_list[0]
        except Exception as e:
            logging.error("error to get data from pdf and word resumes", str(e))


"""obj = Processor().cv_process()
print(obj)"""


