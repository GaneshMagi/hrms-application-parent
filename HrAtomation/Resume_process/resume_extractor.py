
from Resume_process.experience import ExperienceExtractor
from Resume_process.passingYear import PassingYearExtractor
from Resume_process.qualification import QualificationExtractor
from Resume_process.skill import SkillExtractor
from Resume_process.data_extractor import DataExtractor
from Resume_process.Birth_Date import BirthExtractor
from Resume_process.city import CandidateCity
from Database.module import Candidate
from Resume_process.move_folder import Folders
from Database.database_constants import skill_ref_id_query, mob_no_query

import logging
logging.basicConfig(level=logging.INFO)
logging.basicConfig(level=logging.ERROR)


class ResumeProcessor:
    """these function adds all extracted  details of candidates into database """

    @staticmethod
    def add_data_in_table(data, schema_name):
        try:
            name = DataExtractor().extract_name(data)
            email = DataExtractor().extract_email(data)
            phone_no = DataExtractor().extract_mob_no(data)
            alternate_mob_no = DataExtractor().extract_alt_mob_no(data)
            skills = SkillExtractor.extract_skills(data, schema_name)
            qualification = QualificationExtractor.extract_qualification(data, schema_name)
            linked_url = DataExtractor().extract_url(data)
            experience = ExperienceExtractor.extract_experience(data)
            birth_date = BirthExtractor.extract_birthdate(data)
            list_mob_no = Candidate().get_data_in_list(mob_no_query, schema_name)
            year_of_passing = PassingYearExtractor.extract_passing_year(data)
            city_id = CandidateCity().get_city_name(data, schema_name)
            cv_path_url = Folders(schema_name).move_file()

            status_id = 1
            # get list of mobile numbers from table

            if phone_no not in list_mob_no:
                #  add details into table
                Candidate().add_candidate_details(name, phone_no, alternate_mob_no, qualification, email, linked_url, experience, birth_date, year_of_passing, cv_path_url, city_id, status_id, schema_name)
                # get emp id
                emp_id = Candidate().get_id(schema_name)
                # get skill name and skill id in dict format
                skill_dict = Candidate().get_skills_in_json(schema_name)
                # get list of skill ref id list to search ref id is present in skill table or not
                skl_ref_id_list = Candidate().get_data_in_list(skill_ref_id_query, schema_name)
                for skill_name in skills:
                    for s_id, s_name in skill_dict.items():
                        if s_name == skill_name and emp_id not in skl_ref_id_list:
                            Candidate().add_skills(emp_id, s_id, schema_name)
                            print("skill id added")
                if emp_id:
                    return emp_id
            else:
                return f" Profile of {name} already present in table"

        except Exception as e:
            logging.error("Error to add resume details into table: %s", str(e))