from Resume_process.constant import qualification_descreptive_words, qualification_matching_words\
 , List_of_qualification_to_clean, LIST
from Database.database_constants import qualification_query
from Database.module import Candidate
import re
import logging
import unicodedata


class QualificationExtractor:

    @staticmethod
    def extract_qualification(data, schema_name):
        """ return qualification of employee"""
        try:
            u_list = []
            list_of_plane_data = []
            plane_data_list = []
            search_qualification_data = []
            qualification = []
            final_qualification = []
            clean_qualification_list = []
            # to get qualification list from database
            qualification_list = Candidate().get_qualification_list(qualification_query, schema_name)
            # this function find out data below qualification_descriptive_words to extract qualification

            def extract_qualification_data():
                try:
                    for LINE in new_list:
                        u_list.append(LINE.upper())
                    for i in range(len(u_list)):
                        for words in qualification_descreptive_words:
                            if words in u_list[i]:
                                search_qualification_data.append(u_list[i:])
                except Exception as error:
                    logging.error("Error to extract short word qualification: %s", str(error))

            # function remove unexpected characters from string
            def remove_unexpected_characters(q_data):
                try:
                    for right in q_data:
                        plane_data = right.rstrip(')')
                        list_of_plane_data.append(plane_data)
                    for comma in q_data:
                        plane_data = re.sub(",", "", comma)
                        list_of_plane_data.append(plane_data)
                    for left in list_of_plane_data:
                        plane_data = left.lstrip('(')
                        plane_data_list.append(plane_data)
                except Exception as ex:
                    logging.error("Error to remove unexpected characters: %s", str(ex))

            # to extract one word qualifications
            try:
                text = data.split()
                new_list = [item.strip() for item in text]
                extract_qualification_data()
                if len(search_qualification_data) == 0:
                    remove_unexpected_characters(u_list)
                else:
                    # to find qualification in all resume if no qualification matching data found

                    remove_unexpected_characters(search_qualification_data[0])
                for qua in plane_data_list:  # to match resume qualifications with qualification list in database
                    for word in qualification_list:
                        if len(qua) <= 5:
                            if qua == word and qua not in qualification:
                                qualification.append(qua)
            except Exception as e:
                logging.error("error occurs to find one word qualification: %s", str(e))

            # FOR LONG SENTENCE

            qa_data = data.split('\n')
            u_list = []
            new_list = [item.strip() for item in qa_data]
            full_list = []
            search_qualification_data = []
            extract_qualification_data()

            def extract_long_qualification(q_list):
                try:
                    for item in q_list:
                        char = str(item)
                        for words in qualification_matching_words:
                            if words in char and char not in full_list:
                                full_list.append(char)
                            for edu_cation in qualification_list:
                                if len(edu_cation) > 5 and edu_cation in char or 'BACHELOR OF ENGINEERING(B.E)' in char \
                                        and edu_cation not in qualification:
                                    qualification.append(edu_cation)

                except Exception as ero:
                    logging.error("error to match education full form", str(ero))
            try:
                extract_long_qualification(search_qualification_data[0]) if len(search_qualification_data) > 0 else\
                    extract_long_qualification(u_list)
                if not qualification:
                    extract_long_qualification(u_list)
            except Exception as err:
                logging.error('error occurs to calculate length of search_qualification_data:%s', str(err))

            for qualification in full_list:
                qualification = qualification.split(",")
                if qualification[0] not in qualification:
                    qualification.append(qualification[0])

            try:
                """ remove unexpected words from qualification list that mentioned in above list """
                for word in LIST:
                    if word in qualification and len(qualification) > 1:
                        qualification.remove(word)
            except Exception as er:
                logging.error("Error to remove unexpected words from qualification list: %s", str(er))

            def remove_unicode_data(string):
                """ these function remove unicode data from string """
                return unicodedata.category(string) == 'Co'

            try:
                for word in qualification:
                    c_education = "".join([char for char in word if not remove_unicode_data(char)])
                    c_education = c_education.strip()
                    if c_education not in final_qualification and len(c_education):
                        final_qualification.append(c_education)
            except Exception as e:
                logging.error("error to remove unicode data like \uf0b7", str(e))

            for rough_qualification_data in final_qualification:
                for data in List_of_qualification_to_clean:
                    if data in rough_qualification_data and data not in clean_qualification_list:
                        clean_qualification_list.append(data)
            if len(clean_qualification_list) == 0:
                if len(final_qualification) > 0:
                    return final_qualification[0]
            else:
                return clean_qualification_list[0]
        except Exception as e:
            logging.error("Error to extract qualification from resume: %s", str(e))

