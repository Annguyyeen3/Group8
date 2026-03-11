package com.example.recycleview.controller;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
    private List<Room> roomList; // Dữ liệu lưu tạm thời bằng List
    private RoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roomList = new ArrayList<>();
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

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            // Validate dữ liệu (kiểm tra rỗng)
            // Tạo đối tượng Room mới
            if (position == -1) {
                // roomList.add(newRoom); -> Thêm vào List [cite: 24]
                adapter.notifyItemInserted(roomList.size() - 1);
            } else {
                // roomList.set(position, updatedRoom); -> Cập nhật lại List [cite: 37]
                adapter.notifyItemChanged(position);
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa") // Hiển thị AlertDialog xác nhận [cite: 40]
                .setMessage("Bạn có chắc muốn xóa phòng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    roomList.remove(position); // Xóa khỏi List [cite: 41]
                    adapter.notifyItemRemoved(position); // Cập nhật lại RecyclerView [cite: 42]
                    Toast.makeText(this, "Đã xóa phòng", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}