package com.example.libmana.ui.doanhThu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libmana.DAO.ThongKeDAO;
import com.example.libmana.DAO.ThuThuDAO;
import com.example.libmana.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class doanhThuFragment extends Fragment {
    TextView tungay, denngay, thu;
    Button btnShow;

    DatePickerDialog datePickerDialog;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMouth, mDay;

    ThongKeDAO dao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.doanh_thu_fragment, container, false);

        tungay = v.findViewById(R.id.tuNgay);
        denngay = v.findViewById(R.id.denNgay);
        thu = v.findViewById(R.id.tienThu);

        btnShow = v.findViewById(R.id.btnKetQua);
        dao = new ThongKeDAO(getActivity());

        DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMouth = month;
                mDay = dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear, mMouth, mDay);
                tungay.setText(sdf.format(c.getTime()));
            }
        };

        DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMouth = month;
                mDay = dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear, mMouth, mDay);
                denngay.setText(sdf.format(c.getTime()));
            }
        };

        tungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMouth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mYear, mMouth, mDay);
                d.show();
            }
        });


        denngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMouth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDenNgay, mYear, mMouth, mDay);
                d.show();

            }
        });


        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tuNgay = tungay.getText().toString();
                String denNgay = denngay.getText().toString();
                ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
                thu.setText(thongKeDAO.getDoanhThu(tuNgay, denNgay)+ " VND.");
            }
        });
        return v;
    }
}
