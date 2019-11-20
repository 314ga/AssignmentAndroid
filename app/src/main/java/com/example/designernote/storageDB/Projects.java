package com.example.designernote.storageDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

@Entity(indices = {@Index("project_id")},
        foreignKeys = {
        @ForeignKey(entity = Customers.class,
        parentColumns = "customer_id",
        childColumns = "customer_id",
        onDelete = ForeignKey.CASCADE)})
public class Projects implements Serializable
{
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "project_id")
    private int project_id;
    private int customer_id;
    private String p_name, text_diff_task;
    private ArrayList<String> image_path;
    private boolean logo, poster, webpage, photoedit, menu_design, diffeerent_task, bussinness_card, stored_online,
    paid, done;
    private double spent_hours, price, amountPerHour;

    public Projects(int customer_id, double price, String p_name, String text_diff_task, ArrayList<String> image_path, boolean logo,
                    boolean poster, boolean webpage, boolean photoedit, boolean menu_design, boolean diffeerent_task,
                    boolean bussinness_card, boolean stored_online, boolean paid, boolean done, double spent_hours, double amountPerHour)
    {
        this.customer_id = customer_id;
        this.price = price;
        this.image_path  = image_path;
        this.p_name = p_name;
        this.text_diff_task = text_diff_task;
        this.logo = logo;
        this.poster = poster;
        this.webpage = webpage;
        this.photoedit = photoedit;
        this.menu_design = menu_design;
        this.diffeerent_task = diffeerent_task;
        this.bussinness_card = bussinness_card;
        this.stored_online = stored_online;
        this.paid = paid;
        this.done = done;
        this.spent_hours = spent_hours;
        this.amountPerHour = amountPerHour;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public ArrayList<String> getImage_path() {
        return image_path;
    }

    public void setImage_path(ArrayList<String> image_path) {
        this.image_path = image_path;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public double getAmountPerHour() {
        return amountPerHour;
    }

    public void setAmountPerHour(double amountPerHour) {
        this.amountPerHour = amountPerHour;
    }
}
