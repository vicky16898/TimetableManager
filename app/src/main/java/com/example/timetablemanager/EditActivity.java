package com.example.timetablemanager;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Objects;

import adapter.myDbAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity {


    private int position;
    private Subject subject;
    @BindView(R.id.edit_action_bar)
    Toolbar toolbar;
    @BindView(R.id.editInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.editUpdateText)
    TextInputEditText editText;
    @BindView(R.id.editTimeSelect)
    TextInputEditText textInputEditText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        subject = (Subject) getIntent().getSerializableExtra("MyObject");
        position = getIntent().getExtras().getInt("Position");
        editText.setText(subject.getSubjectName());
        textInputEditText.setText(subject.getTime());

        textInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.update) {

            for (int i = 0; i < HomeActivity.fragmentListeners.size(); i++) {
                if ((HomeActivity.fragmentListeners.get(i).getFragmentName()).equals(subject.getDayOfWeek())) {
                    HomeActivity.fragmentListeners.get(i).editListItem(position, editText.getText().toString(), textInputEditText.getText().toString());
                }
            }

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showTimeDialog() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textInputEditText.setText(getTime(hourOfDay, minute));

            }
        }, 8, 30, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private String getTime(int hr, int min) {
        Time tme = new Time(hr, min, 0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }

}
