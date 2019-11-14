package com.jamilxt.interviewtest.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jamilxt.interviewtest.R;
import com.jamilxt.interviewtest.model.Data;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeStudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int STUDENT_LAYOUT = 1;
    public static final int EMPLOYEE_LAYOUT = 2;

    private Context context;
    private List<Data> combinedList;

    public EmployeeStudentAdapter(Context context, List<Data> combinedList) {
        this.context = context;
        this.combinedList = combinedList;
    }

    @Override
    public int getItemViewType(int position) {

        switch (combinedList.get(position).type) {
            case 1:
                return EMPLOYEE_LAYOUT;
            case 2:
                return STUDENT_LAYOUT;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return combinedList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {

            case EMPLOYEE_LAYOUT:
                View employeeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_layout, parent, false);
                return new EmployeeLayout(employeeView);

            case STUDENT_LAYOUT:
                View studentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_layout, parent, false);
                return new StudentLayout(studentView);

            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (combinedList.get(position).type) {
            case 1:
                String ep_name = combinedList.get(position).employee.getEmployee_name();
                String salary = combinedList.get(position).employee.getEmployee_salary();
                String age = combinedList.get(position).employee.getEmployee_age();
                String profile_image = combinedList.get(position).employee.getProfile_image();

                ((EmployeeLayout) holder).setData(ep_name, salary, age, profile_image);
                break;

            case 2:
                String roll = String.valueOf(combinedList.get(position).student.getRoll());
                String name = combinedList.get(position).student.getName();
                String location = combinedList.get(position).student.getLocaltion();
                String tagline = combinedList.get(position).student.getTag_line();

                ((StudentLayout) holder).setDate(roll, name, location, tagline);
                break;


            default:

        }
    }

    class EmployeeLayout extends RecyclerView.ViewHolder {

        TextView tv_name, tv_salary, tv_age;
        ImageView iv_profile_image;

        EmployeeLayout(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.name);
            tv_salary = itemView.findViewById(R.id.salary);
            tv_age = itemView.findViewById(R.id.age);
            iv_profile_image = itemView.findViewById(R.id.profile_image);
        }

        private void setData(String name, String salary, String age, String profile_image) {

            tv_name.setText(name);
            tv_salary.setText(salary);
            tv_age.setText(age);
            Picasso.get().load(profile_image).into((iv_profile_image));

        }

    }

    class StudentLayout extends RecyclerView.ViewHolder {

        TextView tv_roll, tv_name, tv_location, tv_tagline;

        StudentLayout(View itemView) {
            super(itemView);
            tv_roll = itemView.findViewById(R.id.roll);
            tv_name = itemView.findViewById(R.id.name);
            tv_location = itemView.findViewById(R.id.location);
            tv_tagline = itemView.findViewById(R.id.tagline);
        }

        private void setDate(String roll, String name, String location, String tagline) {
            tv_roll.setText(roll);
            tv_name.setText(name);
            tv_location.setText(location);
            tv_tagline.setText(tagline);
        }

    }
}
