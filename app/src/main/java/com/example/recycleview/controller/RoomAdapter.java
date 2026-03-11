package com.example.recycleview.controller;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recycleview.R;
import com.example.recycleview.model.Room;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private List<Room> roomList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position); // Click để mở màn hình sửa [cite: 36]
        void onItemLongClick(int position); // Nhấn giữ để xóa [cite: 39]
    }

    public RoomAdapter(List<Room> roomList, OnItemClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvTenPhong.setText(room.getTenPhong()); // Hiển thị Tên phòng [cite: 29]
        holder.tvGiaThue.setText("Giá: " + room.getGiaThue()); // Hiển thị Giá thuê [cite: 30]

        // Hiển thị và tô màu tình trạng [cite: 31, 32]
        if (room.isTinhTrang()) {
            holder.tvTinhTrang.setText("Còn trống");
            holder.tvTinhTrang.setTextColor(Color.GREEN); // Xanh -> Còn trống [cite: 33]
        } else {
            holder.tvTinhTrang.setText("Đã thuê");
            holder.tvTinhTrang.setTextColor(Color.RED); // Đỏ -> Đã thuê [cite: 34]
        }

        // Bắt sự kiện
        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(position);
            return true;
        });
    }

    @Override
    public int getItemCount() { return roomList.size(); }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenPhong, tvGiaThue, tvTinhTrang;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            tvTinhTrang = itemView.findViewById(R.id.tvTinhTrang);
        }
    }
}