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
import java.text.DecimalFormat;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private List<Room> roomList;
    private OnItemClickListener listener;
    private DecimalFormat formatter = new DecimalFormat("###,###,###");

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
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
        holder.tvTenPhong.setText(room.getTenPhong() + " (" + room.getMaPhong() + ")");
        holder.tvGiaThue.setText("Giá: " + formatter.format(room.getGiaThue()) + " VNĐ");

        if (room.isTinhTrang()) {
            holder.tvTinhTrang.setText("Còn trống");
            holder.tvTinhTrang.setTextColor(Color.parseColor("#4CAF50")); // Green
        } else {
            holder.tvTinhTrang.setText("Đã thuê");
            holder.tvTinhTrang.setTextColor(Color.parseColor("#F44336")); // Red
        }

        if (room.getNguoiThue() != null && !room.getNguoiThue().isEmpty()) {
            holder.tvNguoiThue.setVisibility(View.VISIBLE);
            holder.tvNguoiThue.setText("Người thuê: " + room.getNguoiThue());
        } else {
            holder.tvNguoiThue.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(position);
            return true;
        });
    }

    @Override
    public int getItemCount() { return roomList.size(); }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenPhong, tvGiaThue, tvTinhTrang, tvNguoiThue;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            tvTinhTrang = itemView.findViewById(R.id.tvTinhTrang);
            tvNguoiThue = itemView.findViewById(R.id.tvNguoiThue);
        }
    }
}