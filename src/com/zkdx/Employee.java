package com.zkdx;

public class Employee {
    private int id;
    private String name, password, departmentName, job;
    private int salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswrod(String password) {
        this.password = password;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Employee(int id, String name, String password, String departmentName, String job, int salary) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
        this.departmentName = departmentName;
        this.job = job;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", password=" + password + ", departmentName=" + departmentName
            + ", job=" + job + ", salary=" + salary + "]";
    }

}
