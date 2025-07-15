package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Department {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;


    private String name;
    private String location;

    //@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Employee> employees;

    // Constructors
    public Department() {}

    public Department(String id, String name, String location, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.employees = employees;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public void setDepartmentId(String departmentId) {
        this.id = departmentId;
    }

    public String getDepartmentId() {
        return id;
    }

    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
}
