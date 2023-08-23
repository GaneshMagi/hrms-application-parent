
# path constants

Word_path = 'E://New folder//Resume/*.docx'
Pdf_path = 'E://New folder//Resume/*.pdf'
doc_path = "E://New folder//Resume/*.doc"

# all regular expression which used in main program
email_regex = r'\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b'
phone_regex = r'[\+\(]?[1-9][0-9\\(\)]{8,}[0-9]'
url_regex = r"(https?://\S+)"
date_regex = r'(((Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|June?|July?|Aug(ust)?|Sep(tember)?|Nov(ember)?|Dec(ember)?)|(\d{1,2}\/){0,1})[- ]?\d{4}?)'
digit_regex = '[\d]+[.,\d]+|[\d]*[.][\d]+|[\d]+'
date_regex_in_twodigit = '(((Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|June?|July?|Aug(ust)?|Sep(tember)?|Nov(ember)?|Dec(ember)?)|(\d{1,2}\/){0,2})[- ]?\d{2}?)'
date_format1 = r'(?:19\d{2}|20[01][0-9]|2020)[-/.](?:0[1-9]|1[012])[-/.](?:0[1-9]|[12][0-9]|3[01])\b'
date_format2 = r'(?:0[1-9]|[12][0-9]|3[01])[-/.](?:0[1-9]|1[012])[-/.](?:19\d{2}|20[01][0-9]|2020)\b'
date_format3 = r"^([0-2][0-9]|3[01]) (January|February|March|April|May|June|July|August|September|October" \
               r"|November|December) \d{4}$"
date_format4 = r"\b\d{1,2} [A-Z]{3,9} \d{4}\b"
date_format5 = r"\b\d{1,2}(?:ST|ND|RD|TH) [A-Z]{3,9} \d{4}\b"
date_format6 = r"\b\d{1,2}  [A-Z]{3} \d{4}\b"
date_format7 = r"\b\d{1,2} [A-Z]{3} \d{4}\b"


date_format_list = ["%b, %d %Y", "%b. %d %Y", "%d %B %Y", "%d %b %Y", "%d/%m/%Y"]

# list which is used to match words in resume

Education_list = ["EDUCATION","ACADEMIC HISTORY", "ACADEMIC", "ACADEMIC QUALIFICATION", "ACADEMIC PROFILE",
                  'TRAINING AND EDUCATION', "ACADEMIC CREDENTIALS", "E D U C A T  I ON", "ACADEMICS",
                  'E D U C A T I O N', 'YEAR OF PASSING']

Exceptions_list = ['EXTRACURRICULAR ACTIVITIES', 'WORK EXPERIENCE:', 'PROJECTS UNDERTAKEN', 'RESPONSIBILITIES:',
                   'EXTRA CURRICULAR', 'EXPERIENCE', 'PROFESSIONAL EXPERIENCE', 'WORK EXPERIENCE DETAILS',
                   'ACHIEVMENT & COMPETITION', 'PERSONAL DETAILS :', 'PUBLICATION', 'SUMMARY:', 'MOBILE NUMBER',
                   'PERSONAL DETAILS', 'WORK EXPERIENCE', 'PERSONAL DETAIL', 'PERSONAL PROFILE',
                   'PERSONAL DETAILS:', 'OTHER', 'PERSONAL PROJECTS', 'CAREER SUMMARY', 'TECHNICAL PROJECTS',
                   'SIDE PROJECTS', 'PROJECTS', 'PROJECT', 'SELF PROJECT', 'PERSONAL INFORMATION', 'PROFILE:',
                   'CERTIFICATIONS', 'ACHIEVEMENTS', 'ACADEMIC PROJECT', 'ADDRESS', 'CERTIFICATION', 'LANGUAGE',
                   'PROJECTS SUMMARY', 'HARD SKILLS', 'PRESENT ADDRESS', 'PHONE:']

Exceptions_for_experience = ['EXTRACURRICULAR ACTIVITIES', 'TECHNICAL PROJECTS', 'SIDE PROJECTS', 'PROJECTS', 'PROJECT',
                             'ADDITIONAL SKILLS:', 'EDUCATION AND CERTIFICATIONS', 'SKILLS', 'EDUCATION',
                             'CERTIFICATIONS', 'ACADEMIC PROJECT', 'CERTIFICATION', 'LANGUAGES', 'LANGUAGE',
                             'EDUCATIONAL', 'PROJECTS SUMMARY', 'HARD SKILLS', 'ACADEMIC QUALIFICATION']

qualification_descreptive_words = ['ACADEMIC PROFILE', 'ACADEMIC CREDENTIALS', 'EDUCATIONAL BACKGROUND', 'ACADEMIC',
                                   'E D U C A T I O N A L Q U A L I F I C A T I O N', 'ACADEMIC HISTORY ', 'EDUCATION',
                                   'ACADEMIC DETAILS', 'EDUCATIONL DETAILS', 'QUALIFICATIONS', 'QUALIFICATION',
                                   'E D U C A T I O N A L Q U A L I F I C A T I O N S :']

List_of_qualification_to_clean = ['MASTERS IN ELECTRICAL ENGINEERING', 'MBA', 'M.C.A',
                                  'DIPLOMA IN COMPUTER SCIENCE ENGINEERING', 'BACHELOR OF COMPUTER APPLICATION',
                                  'MASTER OF COMPUTER APPLICATION', 'EVENT MANAGEMENT', 'B-TECH', 'BE(CSE)',
                                  'BACHELOR  IN  MECHANICAL  ENGINEERING', 'PG-DAC', 'B.SC', 'BE CIVIL ENGINEERING',
                                  'BE(IT)', 'BTECH', 'ELECTRICAL ENGINEERING', 'BACHELOR OF COMPUTER SCIENCE',
                                  'BE INFORMATION TECHNOLOGY', 'M.TECH', 'BACHELORS ENGINEERING',
                                  'BACHELOR OF BUSINESS ADMINISTRATION', 'MASTER OF BUSINESS ADMINISTRATION', 'M.SC IT',
                                  'BSC', 'MSC', 'BACHELOR IN COMPUTER', 'BE COMPUTER', 'BBA', 'N.I.E.M',
                                  'MASTER OF ENGINEERING', 'M.B.A', 'MASTER OF DIGITAL MARKETING',
                                  'MASTER OF DATA SCIENCE', "MASTER OF BUSINESS ADMINISTRATOR",
                                  "BACHELOR OF ENGINEERING", 'B.SC', 'BCA', 'MCA', 'B.TECH', "BACHELOR OF ENGINEERING",
                                  "BACHELOR OF SCIENCE", 'BACHELOR OF TECHNOLOGY', 'B. TECH', 'BACHELOR OF COMMERCE',
                                  'B.E']
qualification_matching_words = ['B. TECH.', 'BACHELOR', 'MASTER', 'DIPLOMA', 'B A C H E L O R', 'ELECTRONIC', 'BACHLOR']
skill_matching_words = ['SKILL', 'C O M P E T E N C Y', 'COMPETENCY', 'H A R D S K I L L S']

name_match_words_list = ['', ' ', 'CV', 'RESUME', 'SUMMARY', 'JAVA DEVELOPER', 'NAME','CONTACT','PROFILE', 'NAME:',
                        'CURRICULUM VITAE', ':', 'PORTFOLIO', 'MOB', '+91-', '+91', '+91 ', 'Bengaluru, Karnataka',
                        'PERSONAL','INFORMATION', 'MOBILE:', ',', 'FEBRUARY 2021 - PRESENT', 'PUNE', 'PERSONAL PROFILE',
                        'HUMAN RESOURCE RECRUITING FOR', 'EVENT & ITI AND ED-TECH  COMPANY', 'FOR LAST 6 YEARS']
dob_related_words = ["DATE OF BIRTH", "BIRTH DATE", "DOB"]
address_words = ["address", 'personal', 'residence']
currently_working = ['ONGOING', 'PRESENT', 'TILL DATE', 'CURRENT', 'CURRENT DATE']
years_data = ['YEARS', 'YEAR', 'YRS', 'EXPERIENCE']
month_data = ['MONTHS', 'MONTH']
regex_mob_no = r'[\+\(]?[1-9][0-9\\(\)]{8,}[0-9]'
regex_email = r'\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b'
regex_url = r"(?i)\b((?:https?://|www\d{0,3}[.]|[a-z0-9.\-]+[.][a-z]{2,4}/)(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]" \
            r"+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'\".,<>?«»“”‘’]))"
regex_email2 = r'\b[0-9._%+-]+@[A-Za-z0-9.-]{2,}\b'
LIST = ['ME', 'BE', 'BA', 'SE', 'HE']

