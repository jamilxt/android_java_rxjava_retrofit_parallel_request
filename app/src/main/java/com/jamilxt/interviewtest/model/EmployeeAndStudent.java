package com.jamilxt.interviewtest.model;

import java.util.List;

public class EmployeeAndStudent {

    private List<Employee> employeeList;
    private List<Student> studentList;

    public EmployeeAndStudent(List<Employee> employeeList, List<Student> studentList) {
        this.employeeList = employeeList;
        this.studentList = studentList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

}
