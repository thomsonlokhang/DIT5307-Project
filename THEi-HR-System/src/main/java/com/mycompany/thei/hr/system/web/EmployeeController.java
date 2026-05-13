package com.mycompany.thei.hr.system.web;

import com.mycompany.thei.hr.system.ejb.CrudServiceBean;
import com.mycompany.thei.hr.system.entity.Employee;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("employeeController")
@ViewScoped
public class EmployeeController implements Serializable {

    @EJB
    private CrudServiceBean crudService;

    private List<Employee> employeeList;
    private Employee newEmployee;

    /**
     * Bootstraps the view by creating an empty employee entity for the registration form
     * and fetching the latest known list of employees for the data table.
     */
    @PostConstruct
    public void init() {
        employeeList = crudService.getAllEmployees();
        newEmployee = new Employee();
    }

    /**
     * Executes strict business logic constraints before persisting a new employee.
     * Prevents database corruption by halting the cascade if an identical Email or HKID already exists.
     * 
     * @return A JSF navigation redirect on success, or null to halt navigation and display error messages.
     */
    public String saveEmployee() {
        if (newEmployee.getEmailAddress() != null && !newEmployee.getEmailAddress().trim().isEmpty()) {
            if (crudService.findEmployeeByEmail(newEmployee.getEmailAddress()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "An employee with this email address already exists.", null));
                return null; // Stay on the same page, don't save
            }
        }
        
        if (newEmployee.getHkidNumber() != null && !newEmployee.getHkidNumber().trim().isEmpty()) {
            if (crudService.findEmployeeByHkid(newEmployee.getHkidNumber()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "An employee with this HKID number already exists.", null));
                return null; // Stay on the same page, don't save
            }
        }
        
        crudService.createEmployee(newEmployee);
        // Redirect to the same page to see the updated list
        return "employees?faces-redirect=true";
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public Employee getNewEmployee() {
        return newEmployee;
    }

    public void setNewEmployee(Employee newEmployee) {
        this.newEmployee = newEmployee;
    }
}
