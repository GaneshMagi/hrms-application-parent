
CREATE TABLE role
(
    id bigint PRIMARY KEY,
    name character varying(20)
);
CREATE SEQUENCE roleSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY role.id;

insert into role values(1,'ADMIN');
insert into role values(2,'HR');
insert into role values(3,'VENDOR');
insert into role values(4,'PANEL');
insert into role values(5,'HIRING_MANAGER ');


CREATE TABLE permission
(
    id bigint PRIMARY KEY,
    name character varying(20)
);
CREATE SEQUENCE permissionSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY permission.id;

insert into permission values(1,'REGISTER_MASTER');
insert into permission values(2,'REGISTER_USER');
insert into permission values(3,'REGISTER_CANDIDATE');
insert into permission values(4,'SCHEDULE_INTERVIEW');
insert into permission values(5,'ASSIGN_PANEL');
insert into permission values(6,'PERMIT_ALL');
insert into permission values(7,'UPDATE_FEEDBACK');
insert into permission values(8,'POSITION_UPDATE');
insert into permission values(9,'POSITION_ASSIGNMENT');
insert into permission values(10,'UPDATE_CANDIDATE');
insert into permission values(11,'VIEW_FEEDBACK');
insert into permission values(12,'DEFAULT');



CREATE TABLE role_permission
(

    role_id bigint ,
    permission_id bigint,
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)


);

insert into role_permission values(1,6);
insert into role_permission values(2,3);
insert into role_permission values(2,4);
insert into role_permission values(2,7);
insert into role_permission values(2,9);
insert into role_permission values(2,10);
insert into role_permission values(2,11);
insert into role_permission values(2,5);
insert into role_permission values(3,12);
insert into role_permission values(4,4);
insert into role_permission values(4,7);
insert into role_permission values(5,8);
insert into role_permission values(5,11);





CREATE TABLE user_details
(
    id bigint PRIMARY KEY,
    contact_no character varying(255) NOT NULL UNIQUE,
    display_name character varying(255) ,
    employee_id character varying(255) ,
    full_name character varying(255),
    password character varying(255)NOT NULL ,
    activation boolean,
    username character varying(255) NOT NULL UNIQUE

);

CREATE SEQUENCE userSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY user_details.id;
CREATE TABLE user_roles
(
    user_id bigint,
    role_id bigint ,
    FOREIGN KEY (user_id) REFERENCES user_details(id),
	FOREIGN KEY (role_id) REFERENCES role(id)

);


CREATE TABLE refresh_token
(
    id bigint NOT NULL PRIMARY KEY,
    expiry_date timestamp without time zone NOT NULL,
    token character varying(255)  NOT NULL UNIQUE,
    user_id bigint,
    FOREIGN KEY (user_id) REFERENCES user_details(id)

);

CREATE SEQUENCE tokenSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY refresh_token.id;


CREATE TABLE login_details
(
    id bigint NOT NULL  PRIMARY KEY,
    active boolean NOT NULL,
    login_time timestamp without time zone NOT NULL,
    logout_time timestamp without time zone,
    username character varying(255)  NOT NULL
);
CREATE SEQUENCE loginSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY login_details.id;



CREATE TABLE password
(
    id bigint NOT NULL PRIMARY KEY ,
    passwords character varying(255) ,
    user_id bigint,
    created_date timestamp without time zone,
    FOREIGN KEY (user_id) REFERENCES user_details(id)
);

CREATE SEQUENCE passwordSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY password.id;







CREATE TABLE  city
(
    id bigint PRIMARY KEY,
    city_name character varying(255) NOT NULL UNIQUE,
    is_active boolean NOT NULL
);
CREATE SEQUENCE sequencegenerator_city
 START WITH 2000
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 1000000
    CACHE 1;


copy "city" from 'D:/New folder/cities.csv' delimiter ',' csv header;



CREATE TABLE skill
(
    id bigint PRIMARY KEY ,
    skill_name character varying(255)NOT NULL UNIQUE,
    is_active boolean NOT NULL

);
CREATE SEQUENCE sequencegenerator
 START WITH 2000
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 1000000
    CACHE 1;

copy "skill" from 'D:/New folder/skill_excelshit.csv' delimiter ',' csv header;



CREATE TABLE status
(
    id bigint NOT NULL PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255) ,
    last_modified_date timestamp without time zone,
    is_active boolean NOT NULL,
    status_name character varying(255) ,
    status_type character varying(255)
);
CREATE SEQUENCE statusSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY status.id;


CREATE TABLE candidate_details
(
    id bigint NOT NULL PRIMARY KEY,
    created_by character varying(255) ,
    created_date timestamp without time zone,
    last_modified_by character varying(255) ,
    last_modified_date timestamp without time zone,
    alt_contact_no character varying(255) ,
    birth_date date ,
    contact_no character varying(255) UNIQUE ,
    current_company_name character varying(255),
    email character varying(255) UNIQUE ,
    experience_in_years double precision ,
    full_name character varying(255) ,
    address character varying(255) ,
    gender character varying(255) ,
    linked_in_url character varying(255) ,
    passing_year integer ,
    profile_referance character varying(255) ,
    profile_scanned_on date,
    qualification character varying(255) ,
    resume_url character varying(255),
    whatsapp_no character varying(255),
    feedback_status character varying(255),
    city_id bigint ,
    statusid_id  bigint,
    FOREIGN KEY (city_id) REFERENCES city(id) ,
    FOREIGN KEY (statusid_id) REFERENCES status(id)

);


CREATE SEQUENCE candidateSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY candidate_details.id;


CREATE TABLE candidate_details_skill
(
    candidatedetails_id bigint NOT NULL,
    skill_id bigint NOT NULL,

    FOREIGN KEY (candidatedetails_id) REFERENCES candidate_details(id),
    FOREIGN KEY (skill_id) REFERENCES skill(id)
);



CREATE TABLE employee
(
    id bigint NOT NULL PRIMARY KEY  ,
    created_by character varying(255) ,
    created_date timestamp without time zone,
    last_modified_by character varying(255) ,
    last_modified_date timestamp without time zone,
    alt_contact_no character varying(255),
    contact_no character varying(255)UNIQUE NOT NULL,
    designation character varying(255),
    email_id character varying(255)UNIQUE NOT NULL ,
    emp_id character varying(255)UNIQUE NOT NULL,
    full_name character varying(255) ,
    whatsapp_no character varying(255),
    statusid_id bigint,
     FOREIGN KEY (statusid_id) REFERENCES status(id)


);

CREATE SEQUENCE employeeSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY employee.id;


CREATE TABLE candidate_details_employee
(
    id bigint NOT NULL PRIMARY KEY  ,
    candidatedetails_id bigint NOT NULL,
    employee_id bigint NOT NULL,

    FOREIGN KEY (candidatedetails_id) REFERENCES candidate_details(id),
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);
CREATE SEQUENCE candidateEmployeeSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY candidate_details_employee.id;






CREATE TABLE interview_level
(
    id bigint NOT NULL PRIMARY KEY ,
    created_by character varying(255) ,
    created_date timestamp without time zone,
    last_modified_by character varying(255) ,
    last_modified_date timestamp without time zone,
    level_name character varying(255) NOT NULL,
    preference int UNIQUE NOT NULL,
    predecessor character varying(255),
    statusid_id bigint,
   FOREIGN KEY (statusid_id) REFERENCES status(id)
);

CREATE SEQUENCE interviewlevelSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY interview_level.id;


CREATE TABLE  panel_details
(
    id bigint NOT NULL PRIMARY KEY ,
    created_by character varying(255) ,
    created_date timestamp without time zone,
    last_modified_by character varying(255) ,
    last_modified_date timestamp without time zone,
    employee_id bigint NOT NULL,
    status_id_id bigint,
    FOREIGN KEY (status_id_id) REFERENCES status(id),
    FOREIGN KEY (employee_id) REFERENCES employee(id)

);

CREATE SEQUENCE paneldetailsSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY panel_details.id;


CREATE TABLE panel_skill
(
    id bigint NOT NULL PRIMARY KEY ,
    created_by character varying(255) ,
    created_date timestamp without time zone,
    last_modified_by character varying(255) ,
    last_modified_date timestamp without time zone,
    interviewlevel_id bigint NOT NULL,
    panel_id_id bigint NOT NULL,
    status_id bigint,
    skill_id bigint,

    FOREIGN KEY (status_id) REFERENCES status(id),
     FOREIGN KEY (skill_id) REFERENCES skill(id),
    FOREIGN KEY (interviewlevel_id) REFERENCES interview_level(id),
    FOREIGN KEY (panel_id_id) REFERENCES panel_details(id)
);

CREATE SEQUENCE panelskillSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY panel_skill.id;


--CREATE TABLE panel_skill_skill
--(
--    panelskill_id bigint NOT NULL,
--    skill_id bigint NOT NULL,
--     FOREIGN KEY (panelskill_id) REFERENCES panel_skill(id),
--     FOREIGN KEY (skill_id) REFERENCES skill(id)
--);

CREATE TABLE message_type
(
    id bigint NOT NULL PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255) ,
    last_modified_date timestamp without time zone,
    type character varying(255) NOT NULL UNIQUE
);
CREATE SEQUENCE messagetypeSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY message_type.id;

CREATE TABLE events
(
    id bigint PRIMARY KEY,
    name character varying(255) UNIQUE NOT NULL,
    is_active boolean NOT NULL

);

CREATE SEQUENCE eventSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY events.id;


CREATE TABLE notification_template
(
    id bigint NOT NULL PRIMARY KEY,
    created_by character varying(255) ,
    created_date timestamp without time zone,
    last_modified_by character varying(255) ,
    last_modified_date timestamp without time zone,
    name character varying(255)NOT NULL UNIQUE ,
    type character varying(255) ,
    velocity_file character varying(255),
    image_url character varying(255),
    is_active boolean NOT NULL,
    messageid_id  bigint NOT NULL,
    statusid_id bigint,
    events_id bigint,
    FOREIGN KEY (statusid_id) REFERENCES status(id),
    FOREIGN KEY (messageid_id ) REFERENCES message_type(id),
    FOREIGN KEY (events_id ) REFERENCES events(id)

);

CREATE SEQUENCE notificationtemplateSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY notification_template.id;




CREATE TABLE notification_details
(
    id bigint NOT NULL PRIMARY KEY,
    created_by character varying(255) ,
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    attachment_path character varying(255) ,
    module character varying(255) ,
    notification_module_id bigint ,
    bcc character varying(255) ,
    body character varying(255) ,
    cc character varying(255),
    from_mail character varying(255) ,
    message character varying(255) ,
    subject character varying(255),
    to_mail character varying(255),
    templateid_id bigint NOT NULL,
   FOREIGN KEY (templateid_id) REFERENCES notification_template(id)
);
CREATE SEQUENCE notificationdetailsSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY notification_details.id;



CREATE TABLE candidate_feedback
(
    id bigint NOT NULL PRIMARY KEY,
    created_by character varying(255) ,
    created_date timestamp without time zone,
    last_modified_by character varying(255) ,
    last_modified_date timestamp without time zone,
    behavourial_feedback character varying(255) ,
    current_ctc character varying(255),
    detail_feedback character varying(255) ,
    earliest_availability character varying(255) ,
    expected_ctc character varying(255) ,
    feedback_summary character varying(255),
    final_status character varying(255) ,
    interview_date date,
    notice_period integer,
    offer_letter timestamp without time zone,
    technical_feedback character varying(255) ,
    candidateid_id bigint NOT NULL,
    interviewlevelid_id bigint NOT NULL,


  FOREIGN KEY (interviewlevelid_id) REFERENCES interview_level(id),

  FOREIGN KEY (candidateid_id) REFERENCES candidate_details(id)
);
CREATE SEQUENCE feedbackSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY candidate_feedback.id;

CREATE TABLE candidate_feedback_paneldetails
(
    candidatefeedback_id bigint NOT NULL,
    paneldetails_id bigint NOT NULL,
    FOREIGN KEY (candidatefeedback_id) REFERENCES candidate_feedback(id),
    FOREIGN KEY (paneldetails_id) REFERENCES panel_details(id)
);




CREATE TABLE offer_letter
(
    id bigint PRIMARY KEY,
    created_by character varying(255) ,
    created_date timestamp without time zone,
    last_modified_by character varying(255) ,
    last_modified_date timestamp without time zone,
    date timestamp without time zone,
    date_of_joining timestamp without time zone,
    email_id character varying(255) ,
    notice_period character varying(255) ,
	employment_type character varying(255) ,
    position character varying(255) ,
    probationary_period character varying(255),
    ref_code character varying(255),
    place_of_joining character varying(255),
    grade character varying(255),
    designation  character varying(255),
     compensation  double precision,
    candidatefeedback_id  bigint NOT NULL,

    FOREIGN KEY (candidatefeedback_id ) REFERENCES candidate_feedback(id)

);

CREATE SEQUENCE offerletterSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY offer_letter.id;


CREATE TABLE inline_images
(
    id bigint PRIMARY KEY,
    created_by character varying(255) ,
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    image_url character varying(255) ,
    notificationdetailsid_id bigint NOT NULL,

   FOREIGN KEY (notificationdetailsid_id) REFERENCES notification_details(id)

);
CREATE SEQUENCE inlineimagesSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY inline_images.id;


CREATE TABLE attachment
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    attachment_url character varying(255) ,
    notificationdetailsid_id bigint NOT NULL,
   FOREIGN KEY (notificationdetailsid_id) REFERENCES notification_details(id)

);
CREATE SEQUENCE attachmentSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY attachment.id;


CREATE TABLE qualifications
(
    id bigint PRIMARY KEY,
    name character varying(255)NOT NULL UNIQUE,
    description character varying(255),
    is_active boolean NOT NULL

);

CREATE SEQUENCE qualificationsSequenceGenerator
START 200
INCREMENT 1
MINVALUE 1
OWNED BY qualifications.id;
copy "qualifications" from 'D:/New folder/new_qualification (1).csv' delimiter ',' csv header;

CREATE TABLE languages
(
    id bigint PRIMARY KEY,
    language character varying(255) NOT NULL UNIQUE,
    is_active boolean NOT NULL

);

CREATE SEQUENCE languagesSequenceGenerator
START 200
INCREMENT 1
MINVALUE 1
OWNED BY languages.id;
copy "languages" from 'D:/New folder/langauges.csv' delimiter ',' csv header;



CREATE TABLE companyprofile
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    profile character varying(255)  NOT NULL UNIQUE,
    logo character varying(255),
    is_active boolean NOT NULL

);
CREATE SEQUENCE companyProfileSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY companyprofile.id;



CREATE TABLE shifts
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    shift_name character varying(255) NOT NULL UNIQUE,
    time_zone character varying(255),
    is_active boolean NOT NULL,
    shift_time character varying(255)

);

CREATE SEQUENCE shiftsSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY shifts.id;


CREATE TABLE priority
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    priority_name character varying(255) NOT NULL UNIQUE,
    is_active boolean NOT NULL,
    sla_time character varying(255)

);

CREATE SEQUENCE prioritySequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY priority.id;




CREATE TABLE jobtype
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    jobtype character varying(255) NOT NULL UNIQUE,
    is_active boolean NOT NULL

);

CREATE SEQUENCE jobtypeSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY jobtype.id;

CREATE TABLE  benefits_and_perks
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    benefits_and_perks character varying(255) NOT NULL UNIQUE,
    is_active boolean NOT NULL
);
CREATE SEQUENCE benefitsAndPerksSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY benefits_and_perks.id;




CREATE TABLE jobdescription
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    job_description character varying(255) ,
    role_and_responsibility character varying(255),
    skills_required character varying(255) NOT NUll UNIQUE,
     job_location character varying(255),
     must_to_have character varying(255),
     nice_to_have character varying(255),
    salary_range character varying(255),
    experience character varying(255),
    name character varying(255) ,
    is_active boolean NOT NULL,
    jobtype_id  bigint NOT NULL,
    shifts_id  bigint NOT NULL,

        FOREIGN KEY (jobtype_id) REFERENCES jobtype(id),
        FOREIGN KEY (shifts_id) REFERENCES shifts(id)

);

CREATE SEQUENCE jobdescriptionSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY jobdescription.id;



CREATE TABLE jobdescription_skill
(
   jobdescription_id bigint NOT NULL,
    skill_id bigint NOT NULL,

    FOREIGN KEY (jobdescription_id) REFERENCES jobdescription(id),
    FOREIGN KEY (skill_id) REFERENCES skill(id)
);

CREATE TABLE jobdescription_benefits_and_perks
(
   jobdescription_id bigint NOT NULL,
    benefits_and_perks_id bigint NOT NULL,

    FOREIGN KEY (jobdescription_id) REFERENCES jobdescription(id),
    FOREIGN KEY (benefits_and_perks_id) REFERENCES benefits_and_perks(id)
);

CREATE TABLE jobdescription_city
(
   jobdescription_id bigint NOT NULL,
    city_id bigint NOT NULL,

    FOREIGN KEY (jobdescription_id) REFERENCES jobdescription(id),
    FOREIGN KEY (city_id) REFERENCES city(id)
);


CREATE TABLE jobdescription_qualifications
(
   jobdescription_id bigint NOT NULL,
    qualifications_id bigint NOT NULL,

    FOREIGN KEY (jobdescription_id) REFERENCES jobdescription(id),
    FOREIGN KEY (qualifications_id) REFERENCES qualifications(id)
);


CREATE TABLE jobdescription_languages
(
   jobdescription_id bigint NOT NULL,
    languages_id bigint NOT NULL,

    FOREIGN KEY (jobdescription_id) REFERENCES jobdescription(id),
    FOREIGN KEY (languages_id) REFERENCES languages(id)
);


CREATE TABLE vendor_details
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    vendor_name character varying(255) ,
    contact_person character varying(255),
    alt_contact_no character varying(255) ,
    contact_no character varying(255)  ,
    cin character varying(255)NOT NULL UNIQUE,
    gstn character varying(255) ,
    address character varying(255),
    is_active boolean NOT NULL



);

CREATE SEQUENCE vendordetailsSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY vendor_details.id;





CREATE TABLE position_status
(
    id bigint PRIMARY KEY,
    name character varying(255) UNIQUE NOT NULL


);

CREATE SEQUENCE positionStatusSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY position_status.id;



CREATE TABLE position_type
(
    id bigint PRIMARY KEY,
    name character varying(255)UNIQUE NOT NULL

);

CREATE SEQUENCE positionTypeSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY position_Type.id;


CREATE TABLE position
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    name character varying(255),
    noOfPosition integer,
    jobdescription_id  bigint NOT NULL,
    is_active boolean NOT NULL,
    priority_id  bigint NOT NULL,
    panel_details_id_level1 bigint,
    panel_details_id_level2 bigint,
    open_on  date,
    close_on date,
    positionType_id bigint,
    positionStatus_id bigint,
    employee_id bigint,


    FOREIGN KEY (jobdescription_id) REFERENCES jobdescription(id),
    FOREIGN KEY (panel_details_id_level1) REFERENCES panel_details(id),
    FOREIGN KEY (panel_details_id_level2) REFERENCES panel_details(id),
    FOREIGN KEY (priority_id) REFERENCES priority(id),
    FOREIGN KEY(positionType_id) REFERENCES position_Type(id),
    FOREIGN KEY(positionStatus_id) REFERENCES position_status(id),
    FOREIGN KEY(employee_id) REFERENCES employee(id)


);

CREATE SEQUENCE positionSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY position.id;

CREATE TABLE position_second_level_panel
(
    position_id bigint,
    panel_details_id bigint ,
    FOREIGN KEY (position_id) REFERENCES position(id),
	FOREIGN KEY (panel_details_id) REFERENCES panel_details(id)

);

CREATE TABLE position_first_level_panel
(
    position_id bigint,
    panel_details_id bigint ,
    FOREIGN KEY (position_id) REFERENCES position(id),
	FOREIGN KEY (panel_details_id) REFERENCES panel_details(id)

);




CREATE TABLE position_assignment
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    position_id  bigint NOT NULL,
    is_active boolean NOT NULL,
    user_id bigint,
    local_date timestamp without time zone,
    FOREIGN KEY (position_id) REFERENCES  position(id)
);

CREATE SEQUENCE positionassignmentSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY position_assignment.id;


CREATE TABLE position_profiles
(
    id bigint PRIMARY KEY,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp without time zone,
    candidate_id_id bigint NULL,
    is_active boolean NOT NULL,
    position_id bigint,
    FOREIGN KEY (position_id) REFERENCES position(id),
    FOREIGN KEY (candidate_id_id) REFERENCES candidate_details(id)

);

CREATE SEQUENCE positionprofileSequenceGenerator
START 1
INCREMENT 1
MINVALUE 1
OWNED BY position_profiles.id;









