package com.example.recycleview.model;

public class Room {
    private String maPhong; // Mã phòng [cite: 13]
    private String tenPhong; // Tên phòng [cite: 14]
    private double giaThue; // Giá thuê [cite: 15]
    private boolean tinhTrang; // Tình trạng (true = Còn trống, false = Đã thuê) [cite: 16]
    private String nguoiThue; // Tên người thuê [cite: 17]
    private String soDienThoai; // Số điện thoại [cite: 18]

    public Room(String maPhong, String tenPhong, double giaThue, boolean tinhTrang, String nguoiThue, String soDienThoai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.giaThue = giaThue;
        this.tinhTrang = tinhTrang;
        this.nguoiThue = nguoiThue;
        this.soDienThoai = soDienThoai;
    }

    // Bạn hãy Generate các hàm Getters và Setters cho tất cả các thuộc tính trên.
    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }
    public String getTenPhong() { return tenPhong; }
    public void setTenPhong(String tenPhong) { this.tenPhong = tenPhong; }
    public double getGiaThue() { return giaThue; }
    public void setGiaThue(double giaThue) { this.giaThue = giaThue; }
    public boolean isTinhTrang() { return tinhTrang; }
    public void setTinhTrang(boolean tinhTrang) { this.tinhTrang = tinhTrang; }
    public String getNguoiThue() { return nguoiThue; }
    public void setNguoiThue(String nguoiThue) { this.nguoiThue = nguoiThue; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
}