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

    public void createTrainingEnrollment(TrainingEnrollment enrollment) {
        em.persist(enrollment);
    }
    
    public void updateTrainingEnrollment(TrainingEnrollment enrollment) {
        em.merge(enrollment);
    }

    public TrainingEnrollment findEnrollmentById(Long id) {
        return em.find(TrainingEnrollment.class, id);
    }

    public Employee findEmployeeById(Long id) {
        return em.find(Employee.class, id);
    }

    public Employee findEmployeeByEmail(String emailAddress) {
        try {
            TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employee e WHERE e.emailAddress = :email", Employee.class);
            q.setParameter("email", emailAddress);
            return q.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    public Employee findEmployeeByHkid(String hkidNumber) {
        try {
            TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employee e WHERE e.hkidNumber = :hkid", Employee.class);
            q.setParameter("hkid", hkidNumber);
            return q.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    public void createEmployee(Employee employee) {
        em.persist(employee);
    }

    public List<Employee> getAllEmployees() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }

    public List<TrainingEnrollment> getAllEnrollments() {
        return em.createQuery("SELECT t FROM TrainingEnrollment t", TrainingEnrollment.class)
                 .setHint("javax.persistence.cache.storeMode", "REFRESH")
                 .getResultList();
    }

    public void addPaymentRecord(FeePaymentRecord record) {
        em.persist(record);
    }

    public void createWarning(PaymentWarning warning) {
        em.persist(warning);
    }

    public List<PaymentWarning> getActiveWarnings() {
        TypedQuery<PaymentWarning> q = em.createQuery("SELECT w FROM PaymentWarning w WHERE w.active = true", PaymentWarning.class);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return q.getResultList();
    }

    public void deactivateWarningForEnrollment(Long enrollmentId) {
        em.createQuery("UPDATE PaymentWarning w SET w.active = false WHERE w.associatedEnrollment.id = :eid")
          .setParameter("eid", enrollmentId)
          .executeUpdate();
    }
}
