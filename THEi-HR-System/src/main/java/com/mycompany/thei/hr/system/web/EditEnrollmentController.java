package com.mycompany.thei.hr.system.web;

import com.mycompany.thei.hr.system.ejb.CrudServiceBean;
import com.mycompany.thei.hr.system.ejb.TimerServiceBean;
import com.mycompany.thei.hr.system.entity.Employee;
import com.mycompany.thei.hr.system.entity.FeePaymentRecord;
import com.mycompany.thei.hr.system.entity.TrainingEnrollment;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("editEnrollmentController")
@ViewScoped
public class EditEnrollmentController implements Serializable {

    @EJB
    private CrudServiceBean crudService;

    private Long enrollmentId;
    private TrainingEnrollment enrollment;
    private List<Employee> allEmployees;
    private List<Long> selectedEmployeeIds;

    @PostConstruct
    public void init() {
        this.allEmployees = crudService.getAllEmployees();
        this.selectedEmployeeIds = new ArrayList<>();
    }

    public void loadEnrollment() {
        if (enrollmentId != null && enrollment == null) {
            this.enrollment = crudService.findEnrollmentById(enrollmentId);
            if (this.enrollment != null) {
                // Populate selected employees
                if (this.enrollment.getEnrolledEmployees() != null) {
                    for (Employee emp : this.enrollment.getEnrolledEmployees()) {
                        selectedEmployeeIds.add(emp.getId());
                    }
                }
            }
        }
    }

    public String updateEnrollment() {
        if (enrollment != null) {
            // Clear current employees to rebuild the list
            if(enrollment.getEnrolledEmployees() != null) {
                enrollment.getEnrolledEmployees().clear();
            }

            if (selectedEmployeeIds != null && !selectedEmployeeIds.isEmpty()) {
                for (Long empId : selectedEmployeeIds) {
                    Employee employee = crudService.findEmployeeById(empId);
                    if (employee != null) {
                        enrollment.addEmployee(employee);
                    }
                }
            }

            // Recalculate total paid
            double totalPaid = 0.0;
            if (enrollment.getFeePaymentRecords() != null) {
                for (FeePaymentRecord record : enrollment.getFeePaymentRecords()) {
                    totalPaid += record.getAmountPaid();
                }
            }
            
            if (enrollment.getTotalTrainingFee() <= totalPaid) {
                enrollment.setFullyPaid(true);
                enrollment.setPaymentOverdue(false);
                crudService.deactivateWarningForEnrollment(enrollment.getId());
            } else {
                enrollment.setFullyPaid(false);
            }

            crudService.updateTrainingEnrollment(enrollment);
        }
        return "enrollmentDetail?id=" + enrollmentId + "&faces-redirect=true";
    }

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }

    public TrainingEnrollment getEnrollment() { return enrollment; }
    public void setEnrollment(TrainingEnrollment enrollment) { this.enrollment = enrollment; }

    public List<Employee> getAllEmployees() { return allEmployees; }
    public void setAllEmployees(List<Employee> allEmployees) { this.allEmployees = allEmployees; }

    public List<Long> getSelectedEmployeeIds() { return selectedEmployeeIds; }
    public void setSelectedEmployeeIds(List<Long> selectedEmployeeIds) { this.selectedEmployeeIds = selectedEmployeeIds; }
}