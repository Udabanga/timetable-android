//package com.example.timetableio.activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.util.Pair;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.timetableio.R;
//import com.google.android.material.datepicker.MaterialDatePicker;
//import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
//
//import java.util.Calendar;
//import java.util.TimeZone;
//
//public class BatchScheduleActivity extends AppCompatActivity {
//    private String module[], lecturer[];
//    private RecyclerView recyclerViewStudentBatch;
//    private Button datePickerButton;
//    private TextView selectDate;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_batch_schedule_student);
//
////        datePickerButton = findViewById(R.id.datePickerButton);
////        selectDate = findViewById(R.id.selectedDateText);
////
////        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("IST"));
////        calendar.clear();
////
////        long today = MaterialDatePicker.todayInUtcMilliseconds();
////
////
////
////        //Builder
////        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
////        builder.setTheme(R.style.Theme_MaterialComponents);
////        builder.setTitleText("Select Date Range");
////        final MaterialDatePicker materialDatePicker = builder.build();
////
////
////        datePickerButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                materialDatePicker.show(getSupportFragmentManager(), "DATE_RANGE_PICKER");
////            }
////        });
////
////        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
////            @Override
////            public void onPositiveButtonClick(Object selection) {
////                selectDate.setText("Selected Date: "+ materialDatePicker.getHeaderText());
////            }
////        });
//
////        recyclerViewStudentBatch = findViewById(R.id.studentBatchRecyclerView);
//
////        module=getResources().getStringArray(R.array.student_schedule_module);
////        lecturer=getResources().getStringArray(R.array.student_schedule_lecturer);
//
////        BatchScheduleAdapter batchScheduleAdapter =  new BatchScheduleAdapter(this, module, lecturer);
////
////        recyclerViewStudentBatch.setAdapter(batchScheduleAdapter);
////        recyclerViewStudentBatch.setLayoutManager(new LinearLayoutManager(this));
//    }
//}
