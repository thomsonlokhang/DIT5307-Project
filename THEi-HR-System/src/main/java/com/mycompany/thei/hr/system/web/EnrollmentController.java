package com.mycompany.thei.hr.system.web;

import com.mycompany.thei.hr.system.ejb.CrudServiceBean;
import com.mycompany.thei.hr.system.ejb.TimerServiceBean;
import com.mycompany.thei.hr.system.entity.Employee;
import com.mycompany.thei.hr.system.entity.TrainingEnrollment;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("enrollmentController")
@ViewScoped
public class EnrollmentController implements Serializable {

    @EJB
    private CrudServiceBean crudService;

    @EJB
    private TimerServiceBean timerService;

    private TrainingEnrollment enrollment;
    private List<Employee> allEmployees;
    private List<Long> selectedEmployeeIds;

    /**
     * Initializes a fresh enrollment instance and loads available employees for the selection list.
     */
    @PostConstruct
    public void init() {
        this.enrollment = new TrainingEnrollment();
        this.allEmployees = crudService.getAllEmployees();
        this.selectedEmployeeIds = new ArrayList<>();
    }

    /**
     * Saves the new training enrollment form. 
     * Iterates through the list of checked UI checkboxes to link employees via their ID.
     * Also delegates to the TimerService to begin calculating upcoming payment deadlines.
     * 
     * @return JSF navigation string to redirect back to the home dashboard.
     */
    public String saveEnrollment() {
        // Add selected employees to the enrollment entity
        if (selectedEmployeeIds != null && !selectedEmployeeIds.isEmpty()) {
            for (Long empId : selectedEmployeeIds) {
                Employee employee = crudService.findEmployeeById(empId);
                if (employee != null) {
                    enrollment.addEmployee(employee);
                }
            }
        }
        
        if (enrollment.getEnrollmentDate() == null) {
            enrollment.setEnrollmentDate(new java.util.Date());
        }
        
        // Auto-set to fully paid if total fee is $0
        if (enrollment.getTotalTrainingFee() == 0) {
            enrollment.setFullyPaid(true);
        }
        
        crudService.createTrainingEnrollment(enrollment);
        timerService.createPaymentWarningTimer(enrollment);
        return "index?faces-redirect=true";
    }

    public TrainingEnrollment getEnrollment() { return enrollment; }
    public void setEnrollment(TrainingEnrollment enrollment) { this.enrollment = enrollment; }

    public List<Employee> getAllEmployees() { return allEmployees; }
    public void setAllEmployees(List<Employee> allEmployees) { this.allEmployees = allEmployees; }

    public List<Long> getSelectedEmployeeIds() { return selectedEmployeeIds; }
    public void setSelectedEmployeeIds(List<Long> selectedEmployeeIds) { this.selectedEmployeeIds = selectedEmployeeIds; }
}
