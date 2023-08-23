from Resume_process.constant import skill_matching_words
from Database.module import Candidate
from  Database.database_constants import skill_query
import re
import logging


class SkillExtractor:

    """ return skills of employee"""
    @staticmethod
    def extract_skills(data, schema_name):
        try:
            text = data.split('\n')
            new_list = [item.strip() for item in text]
            u_list = [LINE.upper() for LINE in new_list]
            search_skill_data = []

            for i in range(len(u_list)):
                for word in skill_matching_words:
                    if word in u_list[i]:
                        if 'EXPERIENCE' in u_list[i]:
                            continue
                        else:
                            search_skill_data.append(u_list[i:])
            skill_list = Candidate().get_data_in_list(skill_query, schema_name)  # get list of  skills from database
            skill = []

            def get_skills(data_list):
                # to match 1 word skill
                try:
                    for skil in data_list:
                        chara = str(skil)
                        char = chara.split()
                        for skil in char:
                            skil = re.sub(",", "", skil)
                            skl = skil.rstrip('.')
                            for skil in skill_list:
                                if len(skil) <= 4:
                                    if skil == chara and skil not in skill:
                                        skill.append(skil)
                                else:
                                    if skl in skill_list and skl not in skill:
                                        skill.append(skl)
                except Exception as e:
                    logging.error("error occurs to extract one word skill: %s", str(e))

                # to match long sentence skill
                try:
                    for skl in data_list:
                        char = str(skl)
                        for skil in skill_list:
                            if len(skil) > 3:
                                if skil in char and skil not in skill:
                                    skill.append(skil)
                except Exception as er:
                    logging.error("error in descriptive skills: %s", str(er))

            if len(search_skill_data) > 0:
                get_skills(search_skill_data[0])
            else:
                get_skills(u_list)
            return skill

        except Exception as e:
            logging.error("Error occurs to find skills from resume: %s", str(e))

