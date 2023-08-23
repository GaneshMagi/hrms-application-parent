
# database connection constants
database_name = 'centraldb'
user_name = 'postgres'
password = 'root'
host = 'localhost'
port = 5432

# query constants

skill_query = "SELECT skill_name FROM {schema_name}.skill;"
skill_ref_id_query = "SELECT candidatedetails_id FROM {schema_name}.candidate_details_skill;"
mob_no_query = "SELECT contact_no FROM {schema_name}.candidate_details;"
qualification_query = "SELECT name,description FROM {schema_name}.qualifications;"
add_skill_query = "INSERT INTO {schema_name}.candidate_details_skill (candidatedetails_id, skill_id) VALUES (%s, %s)"
add_candidate_details_query = """INSERT INTO {schema_name}.candidate_details (id,full_name, contact_no, 
alt_contact_no, qualification, email,linked_in_url, experience_in_years, birth_date, passing_year, resume_url,
 city_id, statusid_id)VALUES (nextval('{schema_name}.candidateSequenceGenerator'),%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"""
query_to_get_city_id = "SELECT json_object_agg(id::text, city_name) AS json_data FROM {schema_name}.city;"
select_id_query = "SELECT id FROM {schema_name}.candidate_details;"
query_for_skill_id_and_skill = "SELECT json_object_agg(id::text, skill_name) AS json_data FROM {schema_name}.skill;"

