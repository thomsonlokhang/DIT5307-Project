# Comprehensive Revision Notes for Enterprise Web Application Development (DIT5307) – Beginner to Exam-Ready

Welcome to the ultimate revision guide for DIT5307! If you are starting from absolute scratch, do not worry. We are going to build your understanding step-by-step using everyday analogies, simple explanations, and real-world examples. By the end of these notes, you will be completely prepared for standard questions and the most terrifying exam long-answer questions.

---

## 📑 Table of Contents
1. [What Actually *Is* Java EE? (Enterprise Architecture Essentials)](#1-what-actually-is-java-ee-enterprise-architecture-essentials)
2. [Servlets, Sessions & Cookies (The Waiters of the Web)](#2-servlets-sessions--cookies-the-waiters-of-the-web)
3. [JSP, EL, and JSTL (HTML With Magic)](#3-jsp-el-and-jstl-html-with-magic)
4. [JSF - JavaServer Faces (Component-Based UIs)](#4-jsf---javaserver-faces-component-based-uis)
5. [EJB - Enterprise JavaBeans (The Heavy Lifters)](#5-ejb---enterprise-javabeans-the-heavy-lifters)
6. [JPA & ORM (Database Magic Without SQL)](#6-jpa--orm-database-magic-without-sql)
7. [JMS & MDB (Asynchronous Messaging)](#7-jms--mdb-asynchronous-messaging)
8. [Transactions (Safety First)](#8-transactions-safety-first)
9. [The Modern Alternative: Spring Framework](#9-the-modern-alternative-spring-framework)
10. [Advanced Topics: Filters, Listeners, JAX-RS & CDI](#10-advanced-topics-filters-listeners-jax-rs--cdi)
11. [Master Summary & Most Likely Exam Topics](#11-master-summary--most-likely-exam-topics)

---

## 1. What Actually *Is* Java EE? (Enterprise Architecture Essentials)
If you already know regular Java (Java SE), you know how to build apps that run on your own laptop. But if you want to build amazon.com—a site handling millions of users, interacting with massive databases, and managing secure logins—regular Java isn't enough.

**Java EE (Java Platform, Enterprise Edition)**, recently renamed to **Jakarta EE**, is a **Specification**. It is basically a gigantic rulebook that tells you how to build massive, scalable, secure applications. 
Because it is just a rulebook, you need an **Application Server** to actually run it. Think of the Application Server as the restaurant manager who enforces rules. Examples include **GlassFish**, **WildFly**, and **Apache Tomcat** (which only runs the web portions).

### The Evolution of Architecture (Why we use N-Tier)
Before Java EE, simpler architectures struggled:
*   **1-Tier & 2-Tier:** The client (app) talked *directly* to the database. This was a nightmare to scale, and business logic was permanently locked into the user's software. If a rule changed, everyone had to update their app.
*   **3-Tier & N-Tier (Java EE):** This is what we use today. By isolating the web rendering, business logic, and database servers separately, we can safely scale them up (like adding more chefs to the kitchen without needing to add more waiters).

### The Multi-Tiered Architecture (The Restaurant Analogy)
Java EE splits a website into "Tiers" (layers), so things stay organized. Think of running a restaurant:
*   **Client Tier:** The customer's menu. (Browser, Mobile App using HTML/JS/CSS).
*   **Web Tier (Presentation / View):** The waiters. They take the customer's HTTP request and render the UI. Handled by **Servlets, JSP, and JSF**. 
*   **Business Tier (Controller / Logic):** The chefs. They handle the hard business logic, calculations, and security. Handled by **EJB (Enterprise JavaBeans)**.
*   **EIS Tier (Data Access / Model):** The fridge/pantry. Where data is stored permanently. Handled by **JPA (Databases)** and **JMS (Messaging)**.

> **Key Definitions Box:**
> *   **Java EE / Jakarta EE:** A rulebook (specification) for building distributed, scalable enterprise web apps.
> *   **Application Server:** The software (like GlassFish) that executes your Java EE code safely.
> *   **Tier:** A specific layer in an application separating presentation, logic, and data.

**🌍 Real-World Connection:**
When you buy a concert ticket, your phone app is the Client Tier. The server generating the "Choose Seat" screen is the Web Tier. The code checking if a seat is *actually* available before charging your card is the Business Tier. The database saving your receipt is the EIS Tier.

> **🗝️ Key Takeaways:**
> *   Java EE is a specification, not downloadable software.
> *   It uses a multi-tier model separating the Client, Web, Business, and EIS layers.
> *   Application servers like GlassFish run Java EE apps.

### 📝 Potential Exam Question (Long Answer Practice)
**Q: Describe what is meant by enterprise application software. Draw a diagram of the Java EE multi-tiered application model, showing components in each tier. (10 marks)**
**A:** Enterprise application software is large-scale, distributed software designed to operate in a corporate environment to solve enterprise-wide problems. Its characteristics include extreme scalability, high security, high availability, concurrency handling, and integration with databases.
*(Diagram Description to draw in exam)*: 
*   [Client Machine]: Browsers / Mobile App -> communicates with
*   [Java EE Server - Web Tier]: Servlets, JSP, JSF -> communicates with
*   [Java EE Server - Business Tier]: EJB (Enterprise JavaBeans) -> communicates with
*   [Database Server / EIS Tier]: Relational Database, Legacy systems.

---

## 2. Servlets, Sessions & Cookies (The Waiters of the Web)
A **Servlet** is a Java class that lives on the server. Its only job is to listen for HTTP requests from the browser, figure out what the user wants, and send back an HTTP response. 

### The Servlet Lifecycle
The life of a servlet is highly tested. Memorize this sequence:
1.  **Instantiation:** Web container (like Tomcat/GlassFish) creates the object.
2.  `init(ServletConfig)`: Called exactly **ONCE** when born. Used for setup (e.g., opening a DB connection).
3.  `service(request, response)`: Called on **EVERY single client request**. The server automatically redirects it to `doGet()` (if someone just loads the page) or `doPost()` (if someone submits a form).
4.  `destroy()`: Called **ONCE** before the servlet dies (when the server shuts down).

### Forward vs. Redirect
If a Servlet finishes its job and needs to send the user to a new page, it has two choices:
*   **Forward (`RequestDispatcher.forward()`):** Server-side handover. The waiter hands your order to another waiter in secret. The browser URL **does NOT change**. Any data you attached to the request is kept safely.
*   **Redirect (`response.sendRedirect()`):** Client-side handover. The waiter tells the customer "go to that other restaurant". The browser makes a brand-new request. The URL **CHANGES**. Data attached to the old request is destroyed.

### Sessions vs. Cookies
HTTP usually has "amnesia." Every click is treated like a brand-new user. To fix this:
*   **Cookies:** Tiny text files saved on the *client's browser*. You control them, but they aren't very secure.
*   **Sessions (`HttpSession`):** A secure ledger kept on the *server*. The user is given a randomized token (`JSESSIONID`). Perfect for logins because the server remembers "Oh, this JSESSIONID belongs to Admin!"

> **Key Definitions Box:**
> *   **Servlet:** A Java class extending `HttpServlet` that processes web requests.
> *   **doGet / doPost:** The two main methods inside a Servlet to handle URL loading and form submissions respectively.

**🌍 Real-World Connection:**
When you add an item to an Amazon cart, a Servlet catches your click. Amazon uses a **Session** to remember that item as you browse other pages without forgetting who you are.

> **🗝️ Key Takeaways:**
> *   Servlets follow a strict 4-step lifecycle (Instantiate -> Wait -> Service -> Destroy).
> *   Forwarding keeps the URL the same; Redirecting changes the URL.
> *   Sessions store private data securely on the server; Cookies store data on the browser.

### 📝 Potential Exam Question (Long Answer Practice)
**Q: Refer to the missing source code for `GreetingServlet.java`. Fill in the missing sections (i) to (vi) to process a form and forward it: (6 marks)**
```java
@WebServlet("/greet")
public class GreetingServlet extends HttpServlet {
    @Override
    protected void doPost((i)____________ request, HttpServletResponse response) throws ServletException, IOException {
        String timeText = "morning";
        String inputName = (ii)______________________;
        /* Time validation logic omitted for brevity */
        (iii)___________________((iv)_______, timeText);
        // ...
        getServletContext().(vi)______________________("/greeting.jsp").forward (request, response);
    }
}
```
**A: (Fill in the blanks):**
*   **(i)** `HttpServletRequest` *(The incoming request object)*
*   **(ii)** `request.getParameter("username");` *(Extracts HTML form data)*
*   **(iii)** `request.setAttribute` *(Appends data into the request box to hand to the next waiter)*
*   **(iv)** `"time"` *(The string key name we want to save it under)*
*   **(v)** `"username"` *(The string key name)*
*   **(vi)** `getRequestDispatcher` *(Grabs the internal URL routing engine to initiate the forward)*

---

## 3. JSP, EL, and JSTL (HTML With Magic)
Writing HTML inside a Java Servlet using `out.print("<h1>Hello</h1>")` is painful. **JSP (JavaServer Pages)** flips it around: you write normal HTML, and embed Java code inside of it. 
*Behind the scenes, the server translates every JSP file back into a Servlet anyway!*

### The 4 Data Scopes
Data in Java EE lives in "boxes" of different sizes. From smallest to largest:
1.  **page scope:** Data exists only on this exact current JSP file.
2.  **request scope:** Data exists for the duration of the current HTTP request (survives a `forward`).
3.  **session scope:** Data exists across multiple clicks as long as the user doesn't close the browser.
4.  **application scope:** Data is shared globally with EVERY user on the server.

### The 9 JSP Implicit Objects
Because JSP files turn into Servlets, the server automatically gives you 9 built-in java objects for free, so you don't have to define them. These are:
1. `request` & `response` (Handles HTTP)
2. `out` (Prints to the screen)
3. `session` & `application` (State management)
4. `pageContext`, `config`, `page`, `exception` (Inner engine workings and error handling)

### Rebellious Scriptlets vs. JSTL/EL
In the late 90s, programmers used **Scriptlets** `<% ... %>` to put raw Java in JSP. This led to messy spaghetti code. Today, it is bad practice.
*   **EL (Expression Language):** We replace `<%= session.getAttribute("user") %>` with a clean `${sessionScope.user}`.
*   **JSTL (JSP Standard Tag Library):** We use custom HTML tags to replace Java `if/else` and `for loops`.

> **Key Definitions Box:**
> *   **JSP:** JavaServer Pages, allows embedding Java into HTML.
> *   **JSTL:** Standard tags like `<c:forEach>` avoiding ugly Java scriptlets in views.
> *   **EL:** Syntax like `${var}` for easily printing backend Java variables on the screen.

**🌍 Real-World Connection:**
Your Facebook feed is largely a JSTL `<c:forEach>` loop iterating over a list of `Post` objects fetched by a Servlet, generating thousands of HTML paragraphs automatically.

> **🗝️ Key Takeaways:**
> *   Never use Scriptlets (`<% %>`) in modern web development.
> *   The 4 scopes determine how long a variable survives: Page, Request, Session, Application.
> *   JSTL uses things like `<c:choose>` and `<c:forEach>` to make templates readable.

### 📝 Potential Exam Question (Long Answer Practice)
**Q: Rewrite the following legacy JSP scriptlet code using modern EL and JSTL to remove all `<% %>` blocks. (10 marks)**
*Legacy:*
```jsp
<% List selectedItems=(List)request.getAttribute("items"); 
   for (SelectedItem item : selectedItems) { %>
    <tr>
        <td><%= item.getItemDescription()%></td>
        <td><%= item.getUnitPrice()%></td>
        <td><%= item.getQuantity()%></td>
    </tr>
<% } %>
```
*Exam Answer:*
```jsp
<c:forEach var="item" items="${items}">
    <tr>
        <td>${item.itemDescription}</td>
        <td>${item.unitPrice}</td>
        <td>${item.quantity}</td>
    </tr>
</c:forEach>
```
*(Notice how much cleaner EL is, using just `.itemDescription` instead of calling getter methods!)*

---

## 4. JSF - JavaServer Faces (Component-Based UIs)
While JSP requires you to manually link Servlets to HTML, **JSF (JavaServer Faces)** is a framework that acts like building blocks. You place UI components (like `<h:inputText>`), and they automatically link to a Java class known as a **Managed Bean** (or Backing Bean).

### JSF Scopes
Just like JSP scopes, Managed Beans can be:
*   `@RequestScoped` (Data lasts 1 click)
*   `@ViewScoped` (Data lasts as long as the user stays on this *exact same page* without navigating away)
*   `@SessionScoped` (Data lasts the whole login session)
*   `@ApplicationScoped` (Data is shared instantly with every logged-in user in the entire application, like a global site visitor counter)

### The 6 JSF Lifecycle Phases (MUST MEMORIZE)
When a user clicks a JSF button, the server goes through exactly six strict phases:
1.  **Restore View:** Builds the component tree (the UI in memory).
2.  **Apply Request Values:** Extracts everything the user typed into the form.
3.  **Process Validations:** Checks if the data is legal (e.g., is that an email? Is the password long enough?).
4.  **Update Model Values:** Safely injects the valid data into your Java Managed Bean variables.
5.  **Invoke Application:** Executes your Java methods (e.g., executing the `submitPayment()` method).
6.  **Render Response:** Generates the brand-new HTML page and sends it back to the browser.

> **Key Definitions Box:**
> *   **Managed Bean:** A standard Java class registered to handle data and events for a JSF page.
> *   **Facelets:** The default template system for JSF replacing JSP (using `.xhtml` files).

> **🗝️ Key Takeaways:**
> *   JSF hides the complexity of HTTP requests/responses, letting you bind UI text boxes directly to Java objects.
> *   The 6 lifecycle phases happen sequentially every time the user interacts with the page.

### 📝 Potential Exam Question (Long Answer Practice)
**Q: Rewrite a JSP scriptlet shopping cart table to comply with the Model-View-Controller (MVC) pattern by using JSF Facelets and a backing bean named `ShoppingCart`. (10 marks)**
**A:** (Use standard JSF `<h:dataTable>` and EL `#{...}` to iterate over the items without writing Java scriptlets)
```xml
<h:dataTable value="#{shoppingCart.cartItems}" var="item">
    <h:column>
        <f:facet name="header">Item Description</f:facet>
        #{item.itemDescription}
    </h:column>
    <h:column>
        <f:facet name="header">Unit Price</f:facet>
        #{item.unitPrice}
    </h:column>
    <h:column>
        <f:facet name="header">Quantity</f:facet>
        #{item.quantity}
    </h:column>
</h:dataTable>
```

---

## 5. EJB - Enterprise JavaBeans (The Heavy Lifters)
We now move from the Web Tier to the **Business Tier**. Enter EJBs. The "Chefs" of our restaurant. EJBs do the heavy logic computations, enforce security, and safely talk to the database.

### Types of Session Beans 
This is the most critical part of EJB knowledge:
1.  **Stateless (`@Stateless`):** Does not remember specific clients. The server keeps a "pool" of these beans. When a customer calls, a random bean answers, does the math, and goes back to the pool. Highly efficient and the most commonly used. *(Analogy: A fast-food cashier. They serve you, forget you, and serve the next person).*
2.  **Stateful (`@Stateful`):** Remembers a specific client. It holds "conversational state." *(Analogy: A personal tailor who follows you around the store carrying your clothes).* It must be destroyed manually using `@Remove` when finished.
3.  **Singleton (`@Singleton`):** Only ONE single instance exists for the entire application, shared concurrently by everyone. Good for global counters.

### Local vs. Remote Access
*   **Local:** The Web Tier (Servlet) and the Business Tier (EJB) run on the exact same physical server.
*   **Remote (`@Remote`):** Different servers. Crucially, if you call a `@Remote` EJB, any object it returns is passed by **Value (Copy)**, not by Reference. This means the client cannot accidentally manipulate the EJB's internal private data loops, enforcing encapsulation.

### EJB Interceptors
Ever wanted to automatically log when every EJB method is called without copy-pasting `System.out.println` into every method? You can use an **Interceptor** (`@Interceptors`). They dynamically intercept any calls to the EJB to let you run custom pre-processing or post-processing code behind the scenes.

> **🗝️ Key Takeaways:**
> *   Stateless = no memory, pooled, high performance.
> *   Stateful = conversational memory, dedicated to 1 user, heavy.
> *   Singleton = shared globally.

### 📝 Potential Exam Question (Long Answer Practice)
**Q: Suggest the best type of enterprise bean for an online e-commerce platform offering a shopping cart feature. Explain your suggestion. (3 marks)**
**A:** A **Stateful Session Bean** (`@Stateful`).
**Reasons:** A shopping cart operation involves several steps (method calls) where the user keeps adding items over time. Client-specific conversational state (the items in the cart) must be retained between method calls uniquely for that specific user.

**Q: A currency web portal shows current prices after user enters a code. What bean is best? (3 marks)**
**A:** A **Stateless Session Bean** (`@Stateless`). 
**Reasons:** The task completes in a single call. No client-specific state needs tracking between calls. Stateless beans are pooled, offering better scalability for frequent, high-volume requests.

**Q: Would you return a direct reference to a private List from an EJB method?**
**A:** When using `@Remote` access, method return values are physically copied (return by value). Therefore, we don't have to manually write code to copy the contents ourselves—information hiding and encapsulation is upheld automatically over the network boundary.

---

## 6. JPA & ORM (Database Magic Without SQL)
Writing standard SQL (`INSERT INTO Users...`) is slow and error-prone. Enter **JPA (Java Persistence API)** and **ORM (Object-Relational Mapping)**.
You simply design a normal Java Object (an Entity), and JPA magically writes the SQL behind your back.

### The Entity and Database Mapping
*   `@Entity`: Tells Java "this class represents a table in the database."
*   `@Id`: Designates the primary key.
*   `@GeneratedValue(strategy=GenerationType.IDENTITY)`: Makes the ID Auto-Increment.

### The EntityManager
The `EntityManager` is the tool that physically talks to the database. The server "injects" it for you using `@PersistenceContext`.
Its four holy commands:
*   `em.persist(object)` $\rightarrow$ **INSERT**
*   `em.find(Class, id)` $\rightarrow$ **SELECT**
*   `em.merge(object)` $\rightarrow$ **UPDATE**
*   `em.remove(object)` $\rightarrow$ **DELETE**

### The 4 Stages of an Entity's Life
When interacting with `EntityManager`, objects pass through these states:
1. **Transient:** A brand new java object (`new User()`). The DB has no idea it exists yet.
2. **Managed:** Connected to the DB via the EntityManager. If you do `user.setName()`, the DB auto-updates in the background.
3. **Detached:** The transaction ended, or memory was cleared. It exists in memory but changes are no longer tracked.
4. **Removed:** Scheduled to be deleted from the database upon the next commit.

### Relationships & Fetching
Tables connect; so must Java classes.
*   `@OneToMany` / `@ManyToOne`: e.g., One Person has Many Addresses. 
*   **FetchType.LAZY:** Highly efficient. Does not query the DB for the child objects until you explicitly call the `.getAddresses()` method later on. (Default for collections).
*   **FetchType.EAGER:** Queries everything immediately all at once.
*   **CascadeType:** Tells the database to create a chain reaction. E.g., if you delete a Person, `CascadeType.ALL` will auto-delete their Addresses too.

### Bean Validation
Before JPA saves to the database, we use annotations to validate correctness.
*   `@NotBlank` (can't be empty text), `@NotNull` (can't be null), `@Size(min=2, max=2)`.

> **🗝️ Key Takeaways:**
> *   JPA replaces raw SQL query strings. 
> *   `EntityManager` handles the heavy lifting of saving, finding, and deleting.
> *   Relationships use annotations like `@ManyToOne` to map Foreign Keys.

### 📝 Potential Exam Question (Long Answer Practice)
**Q: An entity class `Address` and `Person` are linked... Fill in the missing source code (i) to (xiii) to map the entities and complete the AddressManager EJB. (16 marks)**
```java
// Focus on Address.java
import javax.(i)____________.*;
(ii)____________
(iii)______________________________ {
    (iv)____________
    private Long id;
    
    @NotBlank
    (v)______________________
    private String district;
    
    @(vi)________((vii)______________, (viii)____________)
    private String territory;
    
    (ix)____________
    private Person person;
}
// Focus on Person.java
    (x)______________________________
    private Collection<Address> addresses;
// Focus on AddressManager.java EJB
    (xi)______________________
    private (xii)______________________ em;

    public void delete(Address address) { em.(xiii)________(address); }
```
**A: (Fill in the blanks):**
*   **(i)** `validation.constraints` *(To use things like `@NotBlank`)*
*   **(ii)** `@Entity` *(Tells JPA this class is a database table)*
*   **(iii)** `public class Address implements Serializable` *(Entities must be serializable)*
*   **(iv)** `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)` *(Makes primary key auto-increment)*
*   **(v)** `@Column(name="district_name")` *(or left blank / `private String district` depending on exact DB schema)*
*   **(vi)** `@Size`  |  **(vii)** `min=2`  |  **(viii)** `max=2` *(Validates exactly 2 characters)*
*   **(ix)** `@ManyToOne` *(Maps many address to one person)*
*   **(x)** `@OneToMany(mappedBy = "person")` *(Completes the reverse relationship)*
*   **(xi)** `@PersistenceContext` *(Injects the DB connection)*
*   **(xii)** `EntityManager` *(The class that runs the queries)*
*   **(xiii)** `remove` *(Deletes the record)*

---

## 7. JMS & MDB (Asynchronous Messaging)
Imagine a user buys a ticket, and you need to send them a PDF receipt format via email. That takes 5 seconds. You do NOT want the user's browser frozen on a loading wheel for 5 seconds.
Solution: **Asynchronous processing via JMS (Java Message Service).**

You drop a "send email" message onto a Queue and instantly return a "Success" webpage to the user. Somewhere in the background, a worker reads the queue and sends it.

*   **Queue:** Point-to-point. One sender, ONE receiver.
*   **Topic:** Publish/subscribe. One sender, broadcast to MANY receivers (like a group text).

### How a Client Produces a JMS Message 
The chain reaction to send an asynchronous message goes exactly like this:
1. Contact the `ConnectionFactory`.
2. Connect to the `Destination` (the Queue or Topic).
3. Open a `Connection`.
4. Create a `Session`.
5. Finally, use the `MessageProducer` to send your payload.

### Message-Driven Beans (MDBs)
An MDB is the invisible background worker (`@MessageDriven`). 

**MDB vs. Stateless EJB (Exam Favorite!):**
*   **Similarities:** Both retain NO conversational state for clients. Both exist in a pool so they can process tasks concurrently. Both handle heavy loads.
*   **Differences:** You can never invoke an MDB directly! It has no client interface. It only runs when a message physically arrives on the Queue it's listening to. 

> **🗝️ Key Takeaways:**
> *   MDBs consume messages asynchronously, which frees up server resources and prevents users from being blocked on slow tasks, drastically improving scalability.

### 📝 Potential Exam Question (Long Answer Practice)
**Q: Compare and contrast a message-driven bean with a stateless session bean. (10 marks)**
**A:**
*   *Similarities:* Both retain NO conversational state for a specific client. Both exist in a pool on the server so they can process tasks concurrently. Both handle heavy loads.
*   *Differences:* You can never invoke an MDB directly! It has no business/client interface. It only activates and consumes messages asynchronously when a message arrives on its listening Queue. Stateless EJBs are invoked synchronously by clients explicitly calling their methods.

**Q: Fill in the code structure for a Message Driven Bean listening to `jms/THEiQueue`. (6 marks)**
```java
(i)____________(activationConfig = {
    @ActivationConfigProperty(
            propertyName = (ii)______________,
            propertyValue = "jms/THEiQueue"
    ),
    @ActivationConfigProperty((iii)______________________________)
})
public class THEiMessageBean (iv)______________________________ {
    @Override
    (v)______________________(Message message) {
        // code for processing message goes here
    }
}
```
**A: (Fill in the blanks):**
*   **(i)** `@MessageDriven`
*   **(ii)** `"destinationLookup"` *(Points the bean to the correct JNDI name)*
*   **(iii)** `propertyName = "destinationType", propertyValue = "javax.jms.Queue"` *(Tells it that it is a point-to-point Queue)*
*   **(iv)** `implements MessageListener` *(Required interface to listen to JMS)*
*   **(v)** `public void onMessage` *(The method triggered instantly when a message arrives)*

---

## 8. Transactions (Safety First)
When transferring $50 from A to B: 
1. Deduct $50 from A.
2. Add $50 to B.
If the server crashes after step 1, A loses money into the void. A **Transaction** binds these steps. They must ALL succeed (Commit), or ALL fail and undo completely (Rollback). This ensures the **ACID** properties (Atomicity, Consistency, Isolation, Durability).

EJB handles this automatically via Container-Managed Transactions (CMT).
**EJB Transaction Attributes:**
*   `REQUIRED` *(Default)*: If client already has a transaction, join it. If not, start a new one gracefully.
*   `REQUIRES_NEW`: Suspend the client's current transaction; fully start a brand-new, isolated one.
*   `MANDATORY`: Client *MUST* already have a transaction started. If they don't, throw an Exception and crash.
*   `NEVER`: Client *MUST NOT* have a transaction running.
*   `SUPPORTS`: If there's an active transaction, join it. If there isn't, just run without one.
*   `NOT_SUPPORTED`: If there's an active transaction, pause it. Run the method without transaction safety.

### 📝 Potential Exam Question (Long Answer Practice)
**Q: Explain how Container-Managed Transactions (CMT) ensure safety in an EJB banking application and provide an example.**
**A:** In CMT, the EJB container intercepts the method call, beginning a transaction before execution and committing it upon successful completion. If an unchecked exception occurs, the transaction rolls back, undoing partial changes (enforcing Atomicity).
```java
@Stateless
public class BankService {
    @PersistenceContext
    private EntityManager em;

    // Uses the default @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transferFunds(Account from, Account to, double amount) {
        from.deduct(amount);
        to.add(amount);
        em.merge(from);
        em.merge(to);
    }
}
```

---

## 9. The Modern Alternative: Spring Framework
Historically, Java EE standard is massive, strict, and heavy. The industry responded by creating the **Spring Framework**, a lightweight ecosystem.

How do they compare?
| Target | Java EE (Jakarta EE) | Spring Framework |
| :--- | :--- | :--- |
| **Nature** | A rigid industry specification rulebook. | A standalone, concrete implementation library. |
| **Footprint** | Heavyweight (mostly requires full App Servers like GlassFish). | Lightweight (runs easily on barebones web containers like Tomcat). |
| **Ecosystem** | Official Oracle/Eclipse standards. | Massive third-party ecosystem (Spring Boot, Spring Security). |
| **Dependency Injection** | CDI (`@Inject`) | Core to its DNA via IoC container (`@Autowired`). |

### Core Spring Annotations to Know
Because Spring uses an Inversion of Control (IoC) container to manage your objects instead of Application Servers, you declare your beans using these component annotations:
*   `@Component`: A generic tag telling Spring to manage this class.
*   `@Service`: Specific for Business Logic (replaces `@Stateless`).
*   `@Repository`: Specific for Data Access (DAOs that talk to the DB).
*   `@Controller` / `@RestController`: Specialized for handling web requests and building APIs.
*   `@Transactional`: Explicitly wraps a method to enforce ACID transaction rules.

### 📝 Potential Exam Question (Long Answer Practice)
**Q: Use a comparison table to show any four differences between Java EE and Spring Framework. (4 marks)**
**A:**
| Feature | Java EE | Spring Framework |
| :--- | :--- | :--- |
| **Footprint** | Heavyweight (runs on full App Servers) | Lightweight (embeddable via Spring Boot/Tomcat) |
| **Nature** | Standardized Specification | Concrete Implementation / Framework |
| **Dependency Injection** | Uses CDI (`@Inject`) | Uses IoC Container (`@Autowired`) |
| **Business Logic** | EJB (`@Stateless`, `@Stateful`) | Spring Beans (`@Service`, `@Component`) |

---

## 10. Advanced Topics: Filters, Listeners, JAX-RS & CDI

*   **Filters (`@WebFilter`):** Act like bouncers. They intercept HTTP requests *before* the Servlet gets them. Perfect for stopping unauthenticated users giving them access to `/admin/*`, or forcing character encodings. Remember to call `chain.doFilter(request, response)` or the request hangs!
*   **Listeners (`@WebListener`):** Invisible background managers. They listen for app lifecycle events (e.g., executing setup code the exact second the Server turns on/off, or logging whenever a user Session is created/destroyed).
*   **JAX-RS (REST APIs):** Creating raw data JSON endpoints for modern mobile apps/React instead of returning HTML. Key annotations: `@Path`, `@GET`, `@POST`, `@Produces(MediaType.APPLICATION_JSON)`.
*   **CDI (Contexts and Dependency Injection):** Instead of manually creating objects `UserService serv = new UserService();`, we use CDI (`@Inject`). The server manages the lifecycle of the object and safely hands it over to you.

---

## 11. Master Summary & Most Likely Exam Topics

You have now reached the end of the journey! Let's do a Master Summary.
**Enterprise web apps are built in tiers:** Browsers hit **Servlets/JSF** (Web tier), which forward data to **EJBs** (Business tier), which use **JPA** (Data tier) to save information securely to a database. You avoid heavy synchronous tasks using **JMS/MDBs**, and Java guarantees financial safety through **Transactions**. 

### 🏆 Most Likely Exam Topics (Based on Past Paper Analysis)
If you study anything, study these 5 areas:

1.  **Stateful vs Stateless vs Singleton EJBs** *(Guaranteed to appear)*
    *   *Must know:* Why Shopping Carts use Stateful, why general calculations use Stateless, why global numbers use Singleton.
2.  **MDB vs Stateless EJBs** *(High Probability)*
    *   *Must know:* Similarities (both pooled, no conversational state) and Differences (MDB is asynchronous, no direct client interaction/business interface).
3.  **JPA Annotations & Coding** *(Guaranteed Coding Question)*
    *   *Must know:* How to construct an `@Entity` class. How to use `@PersistenceContext EntityManager em` alongside `em.persist()` to build a basic code block.
4.  **Legacy Code Translation (JSP scriptlets to JSTL)** *(High Probability)*
    *   *Must know:* Converting `<% for(...) %>` and `<%= var %>` into `<c:forEach>` and `${var}`.
5.  **Multi-Tier Architectural Diagram**
    *   *Must know:* Being able to draw and label the Client, Web Layer, Business Layer, and Database alongside explaining what components go where.

*Best of luck with your revision. Keep following the flow of Data: Web Form -> Servlet -> EJB -> JPA -> Database. You've got this!*