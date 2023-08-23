
from Resume_process.constant import phone_regex, digit_regex, Exceptions_for_experience, date_regex, date_regex_in_twodigit, currently_working, years_data, month_data
from datetime import datetime, date
import re
import pandas as pd
import logging


class ExperienceExtractor:

    @staticmethod
    def extract_experience(data):
        """ return experience of employee """
        try:
            def experience_of_current_working():
                if index_of_currently_working > 5:
                    length_of_year = index_of_currently_working - 7  # it contains limited length before currntly working related words
                    currently_working_date = i[length_of_year:index_of_currently_working]
                    if '/' in currently_working_date:
                        remove_sign = currently_working_date.replace('/', '   ')
                        present.append(remove_sign)
                    else:
                        present.append(currently_working_date)
            split_data = data.split('\n')
            remove_space_from_data = [item.rstrip() for item in split_data]  # remove right space from data
            to_uppercase = []

            for data1 in remove_space_from_data:
                # return all data in uppercase
                plane_data = re.sub(phone_regex, '', data1)  # remove mobile number from data
                to_uppercase.append(plane_data.upper())
            if 'PROJECT EXPERIENCE' in to_uppercase:
                to_uppercase.remove('PROJECT EXPERIENCE')
            year = []
            month = []
            for line in to_uppercase:
                split_line = line.split(',')
                for i in split_line:
                    if 'YEARS' in i or 'YEAR' in i or 'YRS' in i or 'EXPERIENCE' in i:  # to extract year where mention year in the form of ' ' year
                        for years in years_data:
                            if years in i:
                                index_of_year = i.index(years)
                                if index_of_year == 2 or index_of_year == 3:
                                    reg = re.findall(digit_regex, i[:index_of_year])
                                    p = " ".join(reg)
                                    if reg:
                                        year.append(float(p))
                                        break
                                elif index_of_year > 4:
                                    length_of_year = index_of_year - 5
                                    before = i[length_of_year:index_of_year]
                                    if re.search(digit_regex, before) is not None:
                                        for catch in re.finditer(digit_regex, before):
                                            if ',' in catch[0]:
                                                continue
                                            else:
                                                if float(catch[0]) not in year:
                                                    year.append(float(catch[0]))

                    elif 'MONTHS' in i or 'MONTH' in i:  # to extract month where mention year in the form of ' ' month
                        try:
                            for i in month_data:
                                if i in line:
                                    index_of_month= line.index(i)
                                    if index_of_month > 4:
                                        length_of_month = index_of_month - 5
                                        before = line[length_of_month:index_of_month]
                                        if re.search(digit_regex, before) is not None:
                                            for catch in re.finditer(digit_regex, before):
                                                if ',' in catch[0]:
                                                    continue
                                                else:
                                                    month.append(float(catch[0]))
                        except Exception as e:
                            logging.error("Error to get experience in months: %s", str(e))

            experience = []
            try:
                for i in range(len(to_uppercase)):
                    if 'FRESHER' in to_uppercase[i]:
                        year.append("fresher")  # if fresher word in resume then print experience as a fresher
                    elif 'EXPERIENCE' in to_uppercase[i]:
                        experience.append(to_uppercase[i:])  # all data after word 'experience'
                    elif 'POSITIONS OF RESPONSIBILITY' in to_uppercase[i]:
                        experience.append(to_uppercase[i:])  # all data after word 'positions of responsibility'
            except Exception as e:
                logging.error("Error to append data into experience list: %s", str(e))

            data_list = []  # to convert list into string
            try:
                for line in experience:
                    for data in line:
                        if data in Exceptions_for_experience:
                            break
                        else:
                            if data not in data_list:
                                data_list.append(data)
            except Exception as e:
                logging.error("Error to append data into data_list: %s", str(e))

            exp_in_year_list = []  # it contains experience in year
            try:
                for line in data_list:
                    res = re.findall(date_regex, line)  # find year in experience
                    if len(res) > 1:
                        index1 = res[0][0]
                        index2 = res[1][0]
                        x = "-"
                        y = " "
                        year1 = index2.maketrans(x, y)
                        year2 = index1.maketrans(x, y)  # remove '-' sign from string
                        y1 = index1.translate(year2)
                        y2 = index2.translate(year1)
                        for i in ('%m/%Y', '%Y-%m-%d', '%d.%m.%Y'):  # date formats
                            if i == '%m/%Y':
                                try:
                                    dat1 = datetime.strptime(y1, i)
                                    dat2 = datetime.strptime(y2, i)
                                    # print('from',dat1,'to',dat2)
                                    df = pd.DataFrame({'date': [dat1, dat2]})
                                    df['year'] = df['date'].dt.year
                                    df['year'] = df['year'].diff()
                                    exp_in_year_list.append(df['year'].iloc[-1])
                                except ValueError:
                                    pass
                            elif i == '%Y-%m-%d':
                                try:
                                    ans = int(y2) - int(y1)
                                    exp_in_year_list.append(ans)
                                    break
                                except ValueError:
                                    pass

                            elif i == '%d.%m.%Y':
                                try:
                                    ans = int(y2) - int(y1)
                                    exp_in_year_list.append(ans)
                                    break
                                except ValueError:
                                    pass
                            else:
                                try:
                                    ans = int(y2) - int(y1)
                                    exp_in_year_list.append(ans)
                                    break
                                except ValueError:
                                    pass
                    else:
                        continue
            except Exception as e:
                logging.error("Error to get experience in years: %s", str(e))

            present = []
            joined_strings = []
            to_join = ''.join(data_list)
            joined_strings.append(to_join)
            for i in joined_strings:  # calculate experience who is currently working
                if 'REPRESENT' in i:
                    continue
                for working in currently_working:
                    if working in i:
                        index_of_currently_working=i.index(working)
                        experience_of_current_working()
            for j in present:
                if re.findall(date_regex, j):
                    match = re.findall(date_regex, j)
                    if match:
                        pre = match[0][0]
                        today = date.today()
                        current_year = today.year  # current year
                        final = int(current_year) - int(pre)
                        exp_in_year_list.append(final)
                elif re.findall(date_regex_in_twodigit, j):
                    match = re.findall(date_regex_in_twodigit, j)
                    if match:
                        pre = match[0][0]
                        word = '20' + str(pre)
                        final = word.replace(' ', '')
                        today = date.today()
                        current_year = today.year
                        final = int(current_year) - int(final)
                        exp_in_year_list.append(final)
                    else:
                        continue
            result = 0
            for expe in month:
                if expe != 0:
                    result += expe
                else:
                    continue
            sum = 0
            for exp in exp_in_year_list:
                if exp != 0:
                    sum += exp
                else:
                    continue
            fresher = []
            add = 0
            for exper in year:
                if exper == 'fresher':
                    fresher.append('FRESHER')
                elif exper != 0:
                    add += exper
                    break
                else:
                    continue

            if add != 0:
                return int(add)
            elif sum != 0:
                return int(sum)
            elif len(fresher) != 0:
                return 0
            elif result != 0:
                return int(result)
            else:
                return 0

        except Exception as e:
            logging.error("Error to get experience from resume: %s", str(e))

