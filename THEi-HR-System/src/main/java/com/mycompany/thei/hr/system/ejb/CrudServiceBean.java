package com.mycompany.thei.hr.system.ejb;

import com.mycompany.thei.hr.system.entity.Employee;
import com.mycompany.thei.hr.system.entity.FeePaymentRecord;
import com.mycompany.thei.hr.system.entity.PaymentWarning;
import com.mycompany.thei.hr.system.entity.TrainingEnrollment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CrudServiceBean {

    @PersistenceContext(unitName = "THEi_HR_PU")
    private EntityManager em;

    /**
     * Persists a newly created training enrollment to the database.
     * @param enrollment The TrainingEnrollment to create
     */
    public void createTrainingEnrollment(TrainingEnrollment enrollment) {
        em.persist(enrollment);
    }
    
    /**
     * Merges an updated training enrollment back into the persistent context.
     * @param enrollment The TrainingEnrollment to update
     */
    public void updateTrainingEnrollment(TrainingEnrollment enrollment) {
        em.merge(enrollment);
    }

    /**
     * Finds a training enrollment by its primary key ID.
     * @param id The enrollment ID
     * @return The corresponding TrainingEnrollment, or null if not found
     */
    public TrainingEnrollment findEnrollmentById(Long id) {
        return em.find(TrainingEnrollment.class, id);
    }

    /**
     * Finds an employee by their primary key ID.
     * @param id The employee ID
     * @return The corresponding Employee, or null if not found
     */
    public Employee findEmployeeById(Long id) {
        return em.find(Employee.class, id);
    }

    /**
     * Queries the database to find an employee by their exact email address.
     * @param emailAddress The email address to search for
     * @return The Employee matching the email, or null if no result
     */
    public Employee findEmployeeByEmail(String emailAddress) {
        try {
            TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employee e WHERE e.emailAddress = :email", Employee.class);
            q.setParameter("email", emailAddress);
            return q.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    /**
     * Queries the database to find an employee by their unique HKID number.
     * @param hkidNumber The HKID to search for
     * @return The Employee matching the HKID, or null if no result
     */
    public Employee findEmployeeByHkid(String hkidNumber) {
        try {
            TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employee e WHERE e.hkidNumber = :hkid", Employee.class);
            q.setParameter("hkid", hkidNumber);
            return q.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    /**
     * Persists a newly created employee to the database.
     * @param employee The Employee entity to create
     */
    public void createEmployee(Employee employee) {
        em.persist(employee);
    }

    /**
     * Retrieves all employees currently stored in the database.
     * @return A list of all Employee entities
     */
    public List<Employee> getAllEmployees() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }

    /**
     * Retrieves all training enrollments, skipping the JPA cache for live data.
     * @return A list of all TrainingEnrollment entities
     */
    public List<TrainingEnrollment> getAllEnrollments() {
        return em.createQuery("SELECT t FROM TrainingEnrollment t", TrainingEnrollment.class)
                 .setHint("javax.persistence.cache.storeMode", "REFRESH")
                 .getResultList();
    }

    /**
     * Persists a new fee payment record to the database.
     * @param record The FeePaymentRecord to save
     */
    public void addPaymentRecord(FeePaymentRecord record) {
        em.persist(record);
    }

    /**
     * Persists a newly triggered system warning.
     * @param warning The PaymentWarning entity to save
     */
    public void createWarning(PaymentWarning warning) {
        em.persist(warning);
    }

    /**
     * Retrieves all active payment warnings, explicitly bypassing the JPA cache.
     * @return A list of PaymentWarning entities where active = true
     */
    public List<PaymentWarning> getActiveWarnings() {
        TypedQuery<PaymentWarning> q = em.createQuery("SELECT w FROM PaymentWarning w WHERE w.active = true", PaymentWarning.class);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return q.getResultList();
    }

    /**
     * Deactivates (dismisses) all active warnings associated with a specific enrollment ID using a bulk update query.
     * @param enrollmentId The ID of the targeted training enrollment
     */
    public void deactivateWarningForEnrollment(Long enrollmentId) {
        em.createQuery("UPDATE PaymentWarning w SET w.active = false WHERE w.associatedEnrollment.id = :eid")
          .setParameter("eid", enrollmentId)
          .executeUpdate();
    }
}
