from Resume_process.constant import Education_list, Exceptions_list, phone_regex, url_regex, date_regex
import re
import logging
import glob
from Resume_process.constant import Pdf_path, Word_path
from pdfminer.high_level import extract_text


class PassingYearExtractor:
    @staticmethod
    def extract_passing_year(data):
        try:
            split_text = data.split('\n')
            new_list = [item.rstrip() for item in split_text]
            to_uppercase = []
            for qualification in new_list:
                to_uppercase.append(qualification.upper())
            education = []
            for i in range(len(to_uppercase)):
                for q_word in Education_list:
                    if q_word in to_uppercase[i]:
                        education.append(to_uppercase[i:])
            if education:
                education_list = []
                for line in education[0]:
                    strip = str(line).strip()
                    if strip in Exceptions_list:
                        break
                    else:
                        education_list.append(strip)
                years_list = []
                for word in education_list:
                    r_data = word.replace('-', " ")
                    phone = re.findall(phone_regex, r_data)
                    url = re.findall(url_regex, r_data)
                    pattern = re.findall(date_regex, r_data)
                    years = tuple(pattern)
                    if phone:
                        continue
                    elif url:
                        continue
                    elif pattern:
                        first_found_yr = years[0][0]
                        year = re.findall(r'\d{4}$', first_found_yr)
                        if len(years) > 1:
                            p_year = years[0] + years[1]
                            for yr in p_year:
                                reg1 = re.findall(r'\d{4}$', yr)
                                passing_yr = str(reg1)[1:-1]
                                years_list.append(passing_yr)
                        else:
                            if year:
                                years_list.append(year[0])
                new_list = [x.lstrip(" ") for x in years_list]
                if not new_list:
                    return None
                else:
                    cleaned_string = max(new_list).strip("'")
                    integer_number = int(cleaned_string)

                    return integer_number
        except Exception as e:
            logging.error("Error to get passing year from resume: %s", str(e))


"""list_of_files = glob.glob(Pdf_path)
for file in list_of_files:
    data = extract_text(file)
    obj = PassingYearExtractor().extract_passing_year(data)
    print(obj)"""

