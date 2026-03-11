package com.example.recycleview.controller;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recycleview.R;
import com.example.recycleview.model.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Room> roomList;
    private RoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roomList = new ArrayList<>();
        // Thêm dữ liệu mẫu
        roomList.add(new Room("P101", "Phòng 101", 2500000, true, "", ""));
        roomList.add(new Room("P102", "Phòng 102", 3000000, false, "Nguyễn Văn A", "0987654321"));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new RoomAdapter(roomList, new RoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showAddEditDialog(position);
            }

            @Override
            public void onItemLongClick(int position) {
                showDeleteDialog(position);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> showAddEditDialog(-1));
    }

    private void showAddEditDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_room, null);
        builder.setView(view);

        EditText etMa = view.findViewById(R.id.edtMaPhong);
        EditText etTen = view.findViewById(R.id.edtTenPhong);
        EditText etGia = view.findViewById(R.id.edtGiaThue);
        RadioButton rbConTrong = view.findViewById(R.id.rbConTrong);
        RadioButton rbDaThue = view.findViewById(R.id.rbDaThue);
        EditText etNguoi = view.findViewById(R.id.edtNguoiThue);
        EditText etSdt = view.findViewById(R.id.edtSoDienThoai);

        if (position != -1) { // Chế độ sửa
            Room r = roomList.get(position);
            etMa.setText(r.getMaPhong());
            etTen.setText(r.getTenPhong());
            etGia.setText(String.valueOf(r.getGiaThue()));
            if (r.isTinhTrang()) {
                rbConTrong.setChecked(true);
            } else {
                rbDaThue.setChecked(true);
            }
            etNguoi.setText(r.getNguoiThue());
            etSdt.setText(r.getSoDienThoai());
            builder.setTitle("Sửa thông tin phòng");
        } else {
            builder.setTitle("Thêm phòng mới");
        }

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String ma = etMa.getText().toString().trim();
            String ten = etTen.getText().toString().trim();
            String giaStr = etGia.getText().toString().trim();
            
            if (ma.isEmpty() || ten.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin cơ bản!", Toast.LENGTH_SHORT).show();
                return;
            }

            double gia = Double.parseDouble(giaStr);
            boolean tinhTrang = rbConTrong.isChecked();
            String nguoi = etNguoi.getText().toString().trim();
            String sdt = etSdt.getText().toString().trim();

            if (position == -1) {
                roomList.add(new Room(ma, ten, gia, tinhTrang, nguoi, sdt));
                adapter.notifyItemInserted(roomList.size() - 1);
            } else {
                Room r = roomList.get(position);
                r.setMaPhong(ma);
                r.setTenPhong(ten);
                r.setGiaThue(gia);
                r.setTinhTrang(tinhTrang);
                r.setNguoiThue(nguoi);
                r.setSoDienThoai(sdt);
                adapter.notifyItemChanged(position);
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa phòng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    roomList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Đã xóa phòng", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
