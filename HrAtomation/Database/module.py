
from Database.database_constants import add_skill_query, add_candidate_details_query, query_to_get_city_id, \
    select_id_query, query_for_skill_id_and_skill, database_name, user_name, password, port, host
import logging
import psycopg2


class Candidate:
    def __init__(self):
        self.conn = psycopg2.connect(
            database=database_name,
            user=user_name,
            password=password,
            host=host,


            port=port
        )
        self.cursor = self.conn.cursor()

    def add_candidate_details(self, name, mob, alt_mob, education, email, linked_in_url, experience, birth_date,
                              year_of_passing, cv_path_url, city_id, status_id, schema_name):

        try:

            # Execute the SQL statement
            self.cursor.execute(add_candidate_details_query.format(schema_name=schema_name), (
                name, mob, alt_mob, education, email, linked_in_url, experience, birth_date, year_of_passing,
                cv_path_url, city_id, status_id))

            self.conn.commit()
            self.cursor.close()
            self.conn.close()
        except Exception as e:
            logging.exception("Error to add candidate details: %s", str(e))

    def get_cities_in_json(self, schema_name):
        try:
            # Build the SQL SELECT statement
            self.cursor.execute(query_to_get_city_id.format(schema_name=schema_name))
            # Fetch the result
            result = self.cursor.fetchone()
            # Extract the JSON data from the result
            city_json_data = result[0]
            self.cursor.close()
            self.conn.close()
            return city_json_data
        except Exception as e:
            logging.error("Error to get list of cities: %s", str(e))

    def get_id(self, schema_name):
        try:
            # Build the SQL SELECT statement
            self.cursor.execute(select_id_query.format(schema_name=schema_name))  # query stored in constant
            rows = self.cursor.fetchall()
            if rows:
                ids_list = [row[0] for row in rows]
                self.cursor.close()
                self.conn.close()
                if len(ids_list) > 0:
                    return ids_list[-1]
        except Exception as e:
            logging.error("Error to get list of ids from table: %s", str(e))

    def add_skills(self, candidate_id, skill_id, schema_name):
        try:
            self.cursor.execute(add_skill_query.format(schema_name=schema_name), (candidate_id, skill_id))
            self.conn.commit()
            self.cursor.close()
            self.conn.close()
        except Exception as e:
            logging.error("Error to add skills into skill_table: %s", str(e))

    def get_skills_in_json(self, schema_name):
        try:
            # Build the sql query SELECT statement
            self.cursor.execute(query_for_skill_id_and_skill.format(schema_name=schema_name))
            # Fetch the result
            result = self.cursor.fetchone()
            # Extract the JSON data from the result
            city_json_data = result[0]
            self.cursor.close()
            self.conn.close()
            return city_json_data
        except Exception as e:
            logging.error("Error to get list of skills :%s", str(e))

    def get_data_in_list(self, query, schema_name):
        try:
            # Build the sql query SELECT statement
            self.cursor.execute(query.format(schema_name=schema_name))
            rows = self.cursor.fetchall()
            data_list = [row[0] for row in rows]
            self.cursor.close()
            self.conn.close()
            return data_list
        except Exception as e:
            logging.error("Error to get data from tables in list :%s", str(e))

    def get_qualification_list(self, query, schema_name):
        try:
            # Build the sql query SELECT statement
            self.cursor.execute(query.format(schema_name=schema_name))
            rows = self.cursor.fetchall()
            data_list = [item for row in rows for item in row]
            self.cursor.close()
            self.conn.close()
            return data_list
        except Exception as e:
            logging.error("Error retrieving data from tables in list: %s" + str(e))


"""j = Candidate().get_data_in_list("SELECT skill_name FROM {schema_name}.skill;", "wipro")
print(j)"""

"""skill_ref_id_query = "SELECT candidatedetails_id FROM redberyltech.candidate_details_skill;"
sql = "SELECT contact_no FROM redberyltech.candidate_details;"
qualification_query = "SELECT name,description FROM redberyltech.qualifications;"
obj = Candidate().get_qualification_list(skill_query)
print(obj)"""





