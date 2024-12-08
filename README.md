# Employee Appraisal System - Database Setup

## Appraisal Table
## Employee Table
## Review Table
## Band Table
 
### Appraisal Table  

CREATE TABLE IF NOT EXISTS public.appraisal (
    emp_id integer NOT NULL,
    emp_name text COLLATE pg_catalog."default",
    emp_review integer,
    emp_band text COLLATE pg_catalog."default",
    current_salary numeric(15,3),
    appraisal_percentage double precision,
    appraised_salary numeric(15,3),
    CONSTRAINT "Appraisal_pkey" PRIMARY KEY (emp_id)
);

### Sample Data For Appraisal Table  

INSERT INTO public.appraisal(emp_id, emp_name, emp_review, emp_band, current_salary, appraisal_percentage, appraised_salary)
VALUES
(1, 'Amit Sharma', 4, 'A1', 60000, 10, 66000),
(2, 'Priya Desai', 3, 'B2', 50000, 8, 54000),
(3, 'Rajesh Kumar', 1, 'A2', 70000, 12, 78400),
(4, 'Sita Verma', 2, 'C1', 45000, 5, 47250),
(5, 'Ravi Patel', 4, 'B2', 55000, 9, 60000);


### Employee Table


CREATE TABLE IF NOT EXISTS public.employee (
    emp_id INTEGER NOT NULL,
    emp_name TEXT COLLATE pg_catalog."default",
    emp_review INTEGER,
    emp_band TEXT COLLATE pg_catalog."default",
    emp_salary NUMERIC(15,3),
    CONSTRAINT employee_pkey PRIMARY KEY (emp_id)
);

### Sample Data For Employee Table

INSERT INTO public.employee(emp_id, emp_name, emp_review, emp_band, emp_salary)
VALUES
(1, 'Amit Sharma', 4, 'A1', 60000),
(2, 'Priya Desai', 3, 'B2', 50000),
(3, 'Rajesh Kumar', 1, 'A2', 70000),
(4, 'Sita Verma', 2, 'C1', 45000),
(5, 'Ravi Patel', 4, 'B2', 55000),
(6, 'Nina Gupta', 3, 'B1', 48000),
(7, 'Vikram Yadav', 1, 'A2', 75000),
(8, 'Meena Iyer', 4, 'B1', 52000),
(9, 'Anjali Reddy', 3, 'C1', 47000),
(10, 'Karan Singh', 4, 'A2', 65000);

### Review Table

Defination:
Defines review levels and their impact on appraisal.

CREATE TABLE IF NOT EXISTS public.review (
    rev_id INTEGER NOT NULL,
    rev_mul DOUBLE PRECISION,
    CONSTRAINT review_pkey PRIMARY KEY (rev_id)
);

### Sample Data For Review Table

INSERT INTO public.review(rev_id, rev_mul)
VALUES
(1, 0.5),
(2, 0.7),
(3, 0.8),
(4, 1.0);
(5, 1.0);

### Band Table

Description
Maps employee bands to appraisal multipliers.

Schema
 CREATE TABLE IF NOT EXISTS public.band (
    band_id TEXT COLLATE pg_catalog."default" NOT NULL,
    band_mul DOUBLE PRECISION,
    CONSTRAINT band_pkey PRIMARY KEY (band_id)
);

### Sample Data For Band Table

INSERT INTO public.band(band_id, band_mul)
VALUES
('A1', 1.2),
('A2', 1.1),
('B1', 1.0),
('B2', 0.9),
('C1', 0.8);

