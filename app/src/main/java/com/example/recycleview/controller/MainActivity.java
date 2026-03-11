package com.example.recycleview.controller;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recycleview.R;
import com.example.recycleview.model.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Room> roomList;
    private RoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        roomList = new ArrayList<>();
        // Dữ liệu mẫu ban đầu (có thể bỏ qua)
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

        TextInputEditText edtMa = view.findViewById(R.id.edtMaPhong);
        TextInputEditText edtTen = view.findViewById(R.id.edtTenPhong);
        TextInputEditText edtGia = view.findViewById(R.id.edtGiaThue);
        RadioGroup rgTinhTrang = view.findViewById(R.id.rgTinhTrang);
        RadioButton rbConTrong = view.findViewById(R.id.rbConTrong);
        RadioButton rbDaThue = view.findViewById(R.id.rbDaThue);
        TextInputEditText edtNguoi = view.findViewById(R.id.edtNguoiThue);
        TextInputEditText edtSdt = view.findViewById(R.id.edtSoDienThoai);

        if (position != -1) {
            Room room = roomList.get(position);
            edtMa.setText(room.getMaPhong());
            edtTen.setText(room.getTenPhong());
            edtGia.setText(String.valueOf(room.getGiaThue()));
            if (room.isTinhTrang()) rbConTrong.setChecked(true);
            else rbDaThue.setChecked(true);
            edtNguoi.setText(room.getNguoiThue());
            edtSdt.setText(room.getSoDienThoai());
        }

        builder.setPositiveButton("Lưu", null);
        builder.setNegativeButton("Hủy", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String ma = edtMa.getText().toString().trim();
            String ten = edtTen.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();
            boolean tinhTrang = rbConTrong.isChecked();
            String nguoi = edtNguoi.getText().toString().trim();
            String sdt = edtSdt.getText().toString().trim();

            if (ma.isEmpty() || ten.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show();
                return;
            }

            double gia = Double.parseDouble(giaStr);
            Room room = new Room(ma, ten, gia, tinhTrang, nguoi, sdt);

            if (position == -1) {
                roomList.add(room);
                adapter.notifyItemInserted(roomList.size() - 1);
                Toast.makeText(this, "Đã thêm phòng mới", Toast.LENGTH_SHORT).show();
            } else {
                roomList.set(position, room);
                adapter.notifyItemChanged(position);
                Toast.makeText(this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });
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