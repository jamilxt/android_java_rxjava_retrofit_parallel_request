package com.jamilxt.interviewtest.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jamilxt.interviewtest.R;
import com.jamilxt.interviewtest.adapter.EmployeeStudentAdapter;
import com.jamilxt.interviewtest.api.RequestInterface;
import com.jamilxt.interviewtest.model.Data;
import com.jamilxt.interviewtest.model.Employee;
import com.jamilxt.interviewtest.model.EmployeeAndStudent;
import com.jamilxt.interviewtest.model.Student;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://www.mocky.io/";

    private RecyclerView mRecyclerView;

    private Disposable disposable;

    private static String TAG = MainActivity.class.getSimpleName();

    private List<Data> combinedList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        loadJSON();
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                RecyclerView.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON() {

        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface.class);


        Observable<List<Employee>> listObservable = requestInterface.getEmployeeList();

        Observable<List<Student>> listObservable2 = requestInterface.getStudentList();


        disposable = Observable.zip(listObservable, listObservable2,

                new BiFunction<List<Employee>, List<Student>, EmployeeAndStudent>() {
                    @Override
                    public EmployeeAndStudent apply(List<Employee> employees, List<Student> students) throws Exception {
                        return new EmployeeAndStudent(employees, students);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<EmployeeAndStudent>() {

                    @Override
                    public void onNext(EmployeeAndStudent employeeAndStudent) {

                        if (employeeAndStudent.getEmployeeList().size() > employeeAndStudent.getStudentList().size()) {

                            for (int i = 0; i < employeeAndStudent.getEmployeeList().size(); i++) {

                                Data data = new Data(1, employeeAndStudent.getEmployeeList().get(i), null);
                                combinedList.add(data);

                                if (i < employeeAndStudent.getStudentList().size()) {
                                    Data data2 = new Data(2, null, employeeAndStudent.getStudentList().get(i));
                                    combinedList.add(data2);
                                }


                            }

                        }


                        Log.e(TAG, String.valueOf(combinedList.size()));


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        EmployeeStudentAdapter adapter = new EmployeeStudentAdapter(MainActivity.this, combinedList);
                        mRecyclerView.setAdapter(adapter);

                    }
                });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}