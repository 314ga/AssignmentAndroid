package com.example.designernote.storageDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("project_id")},
        foreignKeys = {
        @ForeignKey(entity = Customers.class,
        parentColumns = "customer_id",
        childColumns = "customer_id",
        onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Price.class,
        parentColumns = "price_id",
        childColumns = "price_id",
        onDelete = ForeignKey.CASCADE)})
public class Projects
{
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "project_id")
    private int project_id;
    private int customer_id, price_id;
    private String p_name, text_diff_task;
    private boolean logo, poster, webpage, photoedit, menu_design, diffeerent_task, bussinness_card, stored_online,
    paid;
    private double spent_hours;

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getPrice_id() {
        return price_id;
    }

    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getText_diff_task() {
        return text_diff_task;
    }

    public void setText_diff_task(String text_diff_task) {
        this.text_diff_task = text_diff_task;
    }

    public boolean isLogo() {
        return logo;
    }

    public void setLogo(boolean logo) {
        this.logo = logo;
    }

    public boolean isPoster() {
        return poster;
    }

    public void setPoster(boolean poster) {
        this.poster = poster;
    }

    public boolean isWebpage() {
        return webpage;
    }

    public void setWebpage(boolean webpage) {
        this.webpage = webpage;
    }

    public boolean isPhotoedit() {
        return photoedit;
    }

    public void setPhotoedit(boolean photoedit) {
        this.photoedit = photoedit;
    }

    public boolean isMenu_design() {
        return menu_design;
    }

    public void setMenu_design(boolean menu_design) {
        this.menu_design = menu_design;
    }

    public boolean isDiffeerent_task() {
        return diffeerent_task;
    }

    public void setDiffeerent_task(boolean diffeerent_task) {
        this.diffeerent_task = diffeerent_task;
    }

    public boolean isBussinness_card() {
        return bussinness_card;
    }

    public void setBussinness_card(boolean bussinness_card) {
        this.bussinness_card = bussinness_card;
    }

    public boolean isStored_online() {
        return stored_online;
    }

    public void setStored_online(boolean stored_online) {
        this.stored_online = stored_online;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getSpent_hours() {
        return spent_hours;
    }

    public void setSpent_hours(double spent_hours) {
        this.spent_hours = spent_hours;
    }
}
