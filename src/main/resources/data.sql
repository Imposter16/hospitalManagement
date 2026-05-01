DELETE FROM patient;
		
INSERT INTO patient 
(name, date_of_birth, email, mobile_number, gender, blood_group, updated_date)
VALUES 

('Amit Sharma', '1998-05-12', 'amit.sharma@gmail.com', 9876543210, 'Male', 'B+', NOW()),

('Priya Verma', '2000-09-25', 'priya.verma@gmail.com', 9123456780, 'Female', 'A+', NOW()),

('Rahul Singh', '1995-03-18', 'rahul.singh@gmail.com', 9988776655, 'Male', 'O+', NOW()),

('Neha Gupta', '2002-11-08', 'neha.gupta@gmail.com', 9871234567, 'Female', 'AB+', NOW()),

('Arjun Mehta', '1997-07-30', 'arjun.mehta@gmail.com', 9012345678, 'Male', 'O-', NOW());

DELETE FROM doctor;

INSERT INTO doctor (name, specilization, email)
VALUES 

('Dr. Rajesh Kumar', 'Cardiologist', 'rajesh.kumar@gmail.com'),

('Dr. Anjali Singh', 'Dermatologist', 'anjali.singh@gmail.com'),

('Dr. Vivek Sharma', 'Orthopedic', 'vivek.sharma@gmail.com'),

('Dr. Sneha Verma', 'Pediatrician', 'sneha.verma@gmail.com'),

('Dr. Arjun Gupta', 'Neurologist', 'arjun.gupta@gmail.com');