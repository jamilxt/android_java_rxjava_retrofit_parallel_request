package com.jamilxt.interviewtest.model;

public class Data {
    public int type; // 1 is employee and 2 is student
    public Employee employee;
    public Student student;

    public Data(int type, Employee employee, Student student) {
        this.type = type;
        this.employee = employee;
        this.student = student;
    }
}
