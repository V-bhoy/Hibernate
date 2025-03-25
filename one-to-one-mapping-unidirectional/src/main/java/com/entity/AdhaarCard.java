package com.entity;

public class AdhaarCard {
    private int id;
    private String adhaarNo;

    @Override
    public String toString() {
        return "AdhaarCard{" +
                "id=" + id +
                ", adhaarNo='" + adhaarNo + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdhaarNo() {
        return adhaarNo;
    }

    public void setAdhaarNo(String adhaarNo) {
        this.adhaarNo = adhaarNo;
    }
}
