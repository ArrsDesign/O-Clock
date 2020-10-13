package com.arrsdesign.oclock;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ProgressBar;

public class TaskInput implements Parcelable {

    String titleTask;
    String startDate;
    String endDate;
    String key;
    String difficultyNumber;
    String numberPages;
    String numberSub;
    String timeInMinutes;
    String timeInHours;
    String timeInDays;
    private boolean expanded;


    public TaskInput() {
    }

    public TaskInput(String titleTask, String startDate, String endDate, String key, String difficultyNumber, String numberPages, String numberSub,
                     String timeInMinutes, String timeInHours,String timeInDays) {
        this.titleTask = titleTask;
        this.startDate = startDate;
        this.endDate = endDate;
        this.key = key;
        this.difficultyNumber = difficultyNumber;
        this.numberPages = numberPages;
        this.numberSub = numberSub;
        this.timeInMinutes = timeInMinutes;
        this.timeInHours = timeInHours;
        this.timeInDays = timeInDays;


    }

    protected TaskInput(Parcel in) {
        titleTask = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        key = in.readString();
        difficultyNumber = in.readString();
        numberPages = in.readString();
        numberSub = in.readString();
        timeInMinutes = in.readString();
        timeInHours = in.readString();
        timeInDays = in.readString();
        expanded = in.readByte() != 0;
    }

    public static final Creator<TaskInput> CREATOR = new Creator<TaskInput>() {
        @Override
        public TaskInput createFromParcel(Parcel in) {
            return new TaskInput(in);
        }

        @Override
        public TaskInput[] newArray(int size) {
            return new TaskInput[size];
        }
    };

    public String getTitleTask() {
        return titleTask;
    }

    public void setTitleTask(String titleTask) {
        this.titleTask = titleTask;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDifficultyNumber() {
        return difficultyNumber;
    }

    public void setDifficultyNumber(String difficultyNumber) {
        this.difficultyNumber = difficultyNumber;
    }

    public String getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(String numberPages) {
        this.numberPages = numberPages;
    }

    public String getNumberSub() {
        return numberSub;
    }

    public void setNumberSub(String numberSub) {
        this.numberSub = numberSub;
    }

    public String getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(String timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public String getTimeInHours() {
        return timeInHours;
    }

    public void setTimeInHours(String timeInHours) {
        this.timeInHours = timeInHours;
    }

    public String getTimeInDays() {
        return timeInDays;
    }

    public void setTimeInDays(String timeInDays) {
        this.timeInDays = timeInDays;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titleTask);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(key);
        dest.writeString(difficultyNumber);
        dest.writeString(numberPages);
        dest.writeString(numberSub);
        dest.writeString(timeInMinutes);
        dest.writeString(timeInHours);
        dest.writeString(timeInDays);
        dest.writeByte((byte) (expanded ? 1 : 0));
    }
}
