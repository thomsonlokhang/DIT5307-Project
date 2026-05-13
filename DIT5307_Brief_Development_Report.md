# DIT5307 Brief Development Report: THEi HR System

**Student 1 Name:** Chu Lok Hang Thomson
**Student 1 ID:** 250511652  

**Student 2 Name:** Yeung Chun Kit
**Student 2 ID:** 250276567  


## 1. Introduction
The THEi HR System is a robust Java EE 8 enterprise application designed to streamline internal human resources operations. We implemented this secure administrative portal to facilitate the management of employee profiles, orchestrate complex training program enrollments, record fee installment payments, and automatically enforce administrative compliance through scheduled payment warnings. 

## 2. Enterprise Session Beans
The business logic layer strictly employs Enterprise JavaBeans (EJB) to handle data operations and scheduled tasks reliably.

*   **`CrudServiceBean` (Stateless Session Bean):** 
    This bean serves as the primary data access object for the application, handling all localized CRUD (Create, Read, Update, Delete) operations regarding Employees, Enrollments, Payments, and active Warnings. We elected to make this a `@Stateless` bean because these database queries and updates are isolated, atomic operations. Maintaining conversational state with the client across method invocations is unnecessary for these tasks. Utilizing stateless beans allows the GlassFish container to efficiently pool instances, dramatically improving system memory usage and concurrent scalability.
*   **`TimerServiceBean` (Singleton Session Bean):** 
    This bean encapsulates the `@Timeout` logic used to automatically scan for outstanding training fees as course start dates approach. We defined it as a `@Singleton` (along with the `@Startup` annotation) to guarantee that precisely one instance of the timer service runs across the entire Java Virtual Machine. This prevents race conditions, redundant database queries, and duplicate warning creations that could occur if multiple bean instances were instantiated concurrently.

## 3. Transaction Management
In this project, we rely entirely on Container-Managed Transactions (CMT), which are inherently provided by our EJB architecture. 

Transactions are utilized within the `CrudServiceBean` for all methods performing database modifications (e.g., `createEmployee()`, `updateTrainingEnrollment()`, `addPaymentRecord()`). We implemented these required transactional boundaries to preserve the ACID (Atomicity, Consistency, Isolation, Durability) properties of the PostgreSQL/JavaDB persistence context. For example, when a user allocates a partial fee payment, the container ensures that generating the `FeePaymentRecord` and updating the `TrainingEnrollment` parent balance either securely completes together or safely rolls back. This prevents catastrophic data corruption constraints, such as orphaned payment records.

## 4. JSF Pages and Backing Beans
The presentation layer leverages JavaServer Faces (JSF) with Facelets and Bootstrap 5 components, driven by `@ViewScoped` and `@RequestScoped` backing controllers.

*   **`login.xhtml` & `LoginController`:** Serves as the security entry point, validating administrator credentials against a hardcoded, SHA-256 hashed verification system.
*   **`index.xhtml` & `DashboardController`:** Acts as the main hub. It processes live database feeds, calculating days-to-cancellation for courses and visualizing critical automated warnings triggered by the EJB Timer.
*   **`employees.xhtml` & `EmployeeController`:** Provides the interface for personnel management. The controller heavily applies Jakarta Bean Validation (e.g., strict mandatory fields, Regex patterns for HKID and Email formatting) before passing entities to the EJB layer.
*   **`enrollment.xhtml` & `EnrollmentController`:** Facilitates the creation of new training programs. The controller manages a transactional flow where multiple employees can be selected and linked to a single course using a Many-to-Many entity relationship.
*   **`editEnrollment.xhtml` & `EditEnrollmentController`:** Dedicated views for safely modifying existing upcoming schedules and descriptions.
*   **`enrollmentDetail.xhtml` & `EnrollmentDetailController`:** Represents a specific training portal. This controller dynamically computes total financial balances, accepts custom installment inputs, and displays historical ledger entries.

## 5. System Design
The structural architecture of the data model is defined by standard JPA entity relationships. Below is the UML Class Diagram illustrating the primary data dependencies:

![UML Class Diagram for THEi HR System](/DIT5307-Project/image/DIT5307%20Project%20UML%20Diagram.svg)  
*Figure 1: UML Class Diagram for the THEi HR System*

## 6. Assumptions
During the development life cycle, we proceeded with the following systemic assumptions to bridge gaps naturally left unspecified in the project brief:
1.  **Authentication Scope:** We assumed a single, secure administrative user (`hr`) is sufficient for the scope of this departmental application, rather than building a comprehensive role-based access control (RBAC) database.
2.  **Demonstration Convenience over Real-Time Scaling:** We assumed examiners would prefer immediate feedback. Therefore, the EJB Timer schedule was programmed with a shortened expiration (e.g., 30 seconds rather than real-time 30 days) to facilitate rapid scenario testing without altering physical server host clocks.
3.  **Monetary Formatting:** We assumed all localized financial values operate cleanly under standard decimal structures, explicitly bypassing multi-currency conversion frameworks to focus purely on business persistence logic.
4.  **Local Timestamps:** We assumed the Hong Kong timezone (`Asia/Hong_Kong`) should rigidly dictate JSF view rendering (`<f:convertDateTime>`) to avoid visual shifting based on the hosting machine's locale.

## 7. References
[1] Apache Software Foundation, "Apache Maven project documentation," 2023. [Online]. Available: https://maven.apache.org/
[2] Eclipse Foundation, "Jakarta Enterprise Beans 3.2," 2019. [Online]. Available: https://jakarta.ee/specifications/enterprise-beans/3.2/
[3] Eclipse Foundation, "Jakarta Persistence 2.2," 2019. [Online]. Available: https://jakarta.ee/specifications/persistence/2.2/
[4] Eclipse Foundation, "Jakarta Server Faces (JSF) 2.3 specification," 2020. [Online]. Available: https://jakarta.ee/specifications/faces/2.3/
[5] Eclipse Foundation, "GlassFish server open source edition 5.0 documentation," [Online]. Available: https://glassfish.org/docs/5.0/
[6] Oracle Corporation, "Using the timer service," in *The Java EE 7 tutorial*, 2014. [Online]. Available: https://docs.oracle.com/javaee/7/tutorial/ejb-basicexamples004.htm
[7] Oracle Corporation, "Java EE 8 tutorial," 2017. [Online]. Available: https://javaee.github.io/tutorial/