CREATE DATABASE question_db;

CREATE TABLE question (
    question_id INT (20) AUTO_INCREMENT,
    type VARCHAR(20),
    question VARCHAR(255),
    PRIMARY KEY(question_id)
);

CREATE TABLE question_option (
    id INT (20) AUTO_INCREMENT,
    option_id VARCHAR (1),
    description VARCHAR(255),
    sequence int(1),
    question_id INT(20),
    PRIMARY KEY (id),
    CONSTRAINT fk_question_option_question_id FOREIGN KEY (question_id) REFERENCES question (question_id),
    CONSTRAINT uk_option_id_question_id UNIQUE (option_id, question_id)
);


CREATE TABLE answer (
    answer_id INT (20) AUTO_INCREMENT ,
    answer VARCHAR(1),
    question_id INT(20),
    PRIMARY KEY (answer_id),
    CONSTRAINT fk_answer_question_id FOREIGN KEY (question_id) REFERENCES question (question_id)

);


