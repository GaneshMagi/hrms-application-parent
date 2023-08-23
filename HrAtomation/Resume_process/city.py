import glob
from Resume_process.constant import Pdf_path, Word_path
from pdfminer.high_level import extract_text
from Database.module import Candidate
from Resume_process.constant import address_words
import logging


class CandidateCity:
    @staticmethod
    def get_city_name(data, schema_name):
        try:
            city_list = []
            # GET CITIES AND THEIR IDS IN JSON
            city_dict = Candidate().get_cities_in_json(schema_name)
            text = data.split()
            new_list = [item.strip() and item.lower() for item in text]

            for index in range(len(new_list)):
                for word in address_words:
                    if word in new_list[index]:
                        for city_id, city_name in city_dict.items():
                            for char in new_list[index:]:
                                if city_name.lower() == char and city_id not in city_list:
                                    city_list.append(city_id)
                else:
                    for city_id, city_name in city_dict.items():
                        if city_name.lower() in new_list[index] and city_id not in city_list:
                            city_list.append(city_id)

            if len(city_list) > 0:
                return city_list[0]
        except Exception as e:
            logging.error("Error to add city id: %s", str(e))


"""list_of_files = glob.glob(Pdf_path)
for file in list_of_files:
    data = extract_text(file)
    obj = CandidateCity().get_city_name(data)
    print(obj)"""