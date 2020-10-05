package com.arrsdesign.oclock;

class TasCard {
    private String title;
    private String deadline;
    private String start;
    //private String daysLeft;
    private String difficulty;
    private String pages;
    private String subTask;
    private String min;
    private String hrs;
    private String days;

    public TasCard(){
        //Empty Constructor Needed
    }

    public TasCard(String title, String deadline, String start, String difficulty, String pages, String subTask, String min, String hrs, String days) {
        this.title = title;
        this.deadline = deadline;
        this.start = start;
        //this.daysLeft = daysLeft;
        this.difficulty = difficulty;
        this.pages = pages;
        this.subTask = subTask;
        this.min = min;
        this.hrs = hrs;
        this.days = days;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getSubTask() {
        return subTask;
    }

    public void setSubTask(String subTask) {
        this.subTask = subTask;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getHrs() {
        return hrs;
    }

    public void setHrs(String hrs) {
        this.hrs = hrs;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
