package com.example.designernote;
public class Person {
    public int personImage;
    public String customerName;
    public String debtPrice;
    public String totalPrice;
    public String workQty;

    public Person(String customerName, int personImage, String debtPrice, String totalPrice, String workQty) {
        this.personImage = personImage;
        this.customerName = customerName;
        this.debtPrice = debtPrice;
        this.totalPrice = totalPrice;
        this.workQty = workQty;
    }

    public String getWorkQty() {
        return workQty;
    }

    public void setWorkQty(String workQty) {
        this.workQty = workQty;
    }

    public String getDebtPrice() {
        return debtPrice;
    }

    public void setDebtPrice(String debtPrice) {
        this.debtPrice = debtPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPersonImage() {
        return personImage;
    }

    public void setPersonImage(int personImage) {
        this.personImage = personImage;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}