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

    public TasCard(String title, String deadline, String start, String daysLeft, String difficulty, String pages, String subTask, String min, String hrs, String days) {
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

    public String getDeadline() {
        return deadline;
    }

    public String getStart() {
        return start;
    }

    /*public String getDaysLeft() {
        return daysLeft;
    }*/

    public String getDifficulty() {
        return difficulty;
    }

    public String getPages() {
        return pages;
    }

    public String getSubTask() {
        return subTask;
    }

    public String getMin() {
        return min;
    }

    public String getHrs() {
        return hrs;
    }

    public String getDays() {
        return days;
    }
}
