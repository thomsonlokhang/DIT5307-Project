# THEi HR System

## Overview
THEi HR System is a Java EE 8 enterprise application developed for the DIT5307 assignment. It functions as an HR and Training Enrollment portal that allows administrators to:
- Manage employee records securely.
- Create and organize training programs.
- Enroll multiple employees into training programs.
- Track fee payment records for these programs.
- Automatically flag overdue payments using an EJB Timer Service 30 days before a course begins (or as configured).

## Technologies Used
- **Backend:** Java EE 8, EJB (Stateless/Singleton/Timer), JPA (EclipseLink), CDI.
- **Frontend:** JSF (JavaServer Faces) with Facelets, Bootstrap 5 for responsive UI.
- **Database:** Derbey / JavaDB (via standard GlassFish DataSource).
- **Server:** GlassFish 5.0.1.

## Getting Started
1. Clone the repository to your local machine.
2. Open the project in Apache NetBeans or your IDE of choice.
3. Ensure GlassFish 5.0.1 is configured as the target application server.
4. Clean, Build, and Deploy the application.
5. Navigate to `http://localhost:8080/THEi-HR-System/` in your browser.

## Credentials
For demonstration purposes, the system uses the following default credentials:
- **Username:** hr
- **Password:** password

## Features
- **Dashboard:** Overview of active enrollments and system-generated payment cancellation warnings.
- **Employee Management:** Full CRUD operations on employee profiles, complete with JSF front-end and Bean Validation on the back-end (HKID, Email, Phone formatting).
- **Enrollment Management:** Link multiple employees to training programs in a single pass (ManyToMany). Track and append multi-part installment payments (OneToMany).
- **EJB Timer Service:** Automatically scans for unpaid enrollments nearing their start date and issues Warnings.