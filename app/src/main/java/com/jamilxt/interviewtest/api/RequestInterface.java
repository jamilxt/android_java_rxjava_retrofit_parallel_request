package com.jamilxt.interviewtest.api;

import com.jamilxt.interviewtest.model.Employee;
import com.jamilxt.interviewtest.model.Student;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("/v2/5cdf27653000002b00430d14")
    Observable<List<Employee>> getEmployeeList();

    @GET("/v2/5cdf2f353000004600430d29")
    Observable<List<Student>> getStudentList();
}