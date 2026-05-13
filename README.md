# THEi HR System (DIT5307 Project)

## Overview
THEi HR System is a Java EE 8 enterprise application developed for the DIT5307 assignment. It functions as an HR and Training Enrollment portal that allows administrators to:
- Manage employee records securely with JSF constraint validation.
- Create and organize training programs (with dynamic fee math and partial payments).
- Enroll multiple employees into training programs simultaneously.
- Track fee payment records, automatically calculating total paid and remaining balances.
- Automatically flag overdue payments using an asynchronous EJB Timer Service.

## Technologies Used
- **Backend:** Java EE 8, EJB (Stateless/Singleton/Timer), JPA (EclipseLink), CDI.
- **Frontend:** JSF (JavaServer Faces) with Facelets, Bootstrap 5 for responsive UI.
- **Security:** SHA-256 Password Hashing.
- **Database:** Derby / JavaDB (via standard GlassFish DataSource).
- **Server / IDE:** GlassFish 5.0.1 / Apache NetBeans.
- **Java Version:** JDK 8 Strict (fully compatible, no newer Java features used).

## Getting Started
1. Clone or extract the repository to your local machine.
2. Open the `THEi-HR-System` project in Apache NetBeans.
3. Ensure **GlassFish 5.0.1** is configured as the target application server and JavaDB/Derby is running.
4. Clean, Build, and Run the application.
5. Navigate to `http://localhost:8080/THEi-HR-System/` in your browser.

## Credentials
For demonstration purposes, the system uses the following default credentials (secured via SHA-256 hashing internally):
- **Username:** hr
- **Password:** password

## Assignment Features Implemented
- **Dashboard:** Live overview of active enrollments with real-time active warnings mapped explicitly from the database (JPA Cache refreshed automatically).
- **Employee Management:** Full CRUD operations on employee profiles, complete with strict JSF UI constraints, mandatory field enforcement, and regex matching for HKIDs and Email addresses.
- **Enrollment Management:** Link multiple employees to training programs. Tracks multi-part partial fee payments with exact currency formatting.
- **Asynchronous EJB Timer:** Configured for demonstration purposes to scan for unpaid enrollments. (Note: The timer has been configured to fire a warning 30 seconds after enrollment creation for easy examiner grading and testing without waiting days).
- **Codebase Documentation:** Fully integrated JavaDoc method annotations and HTML block architectures on all UI components to guarantee developer maintainability.