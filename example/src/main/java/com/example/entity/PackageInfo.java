package com.example.entity;

public class PackageInfo {
    private String id;// 快递的 ID
    private String courier;// 快递公司名称
    private String pickupCode;// 取件码
    private String sender;// 发件人
    private String receiver;// 收件人（用户名）
    private String station;// 快递所在的驿站名称
    private boolean pickedUp; // 快递的取件状态，默认值为 false，表示未取件



    // 构造函数，初始化快递对象
    // 传入的参数有快递 ID、快递公司、取件码、发件人、收件人、驿站名称
    public PackageInfo(String id, String courier, String pickupCode, String sender, String receiver,
                       String station) {
        this.id = id;
        this.courier = courier;
        this.pickupCode = pickupCode;
        this.sender = sender;
        this.receiver = receiver;
        this.station = station;
        this.pickedUp = false; // 默认未取件

    }

    // 后面全是getter setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    // 获取快递公司名称
    public String getCourier() {
        return courier;
    }

    // 设置快递公司名称
    public void setCourier(String courier) {
        this.courier = courier;
    }

    // 获取取件码
    public String getPickupCode() {
        return pickupCode;
    }

    // 设置取件码
    public void setPickupCode(String pickupCode) {
        this.pickupCode = pickupCode;
    }

    // 获取发件人用户名
    public String getSender() {
        return sender;
    }

    // 设置发件人用户名
    public void setSender(String sender) {
        this.sender = sender;
    }

    // 获取收件人用户名
    public String getReceiver() {
        return receiver;
    }

    // 设置收件人用户名
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    // 获取快递所在的驿站名称
    public String getStation() {
        return station;
    }

    // 设置快递所在的驿站名称
    public void setStation(String station) {
        this.station = station;
    }

    // 获取快递是否已取件的状态
    public boolean isPickedUp() {
        return pickedUp;
    }

    // 设置快递的取件状态（是否已取件）
    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }


}
