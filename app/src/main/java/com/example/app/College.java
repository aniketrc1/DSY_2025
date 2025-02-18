package com.example.app;

public class College {
    private String college_name; // Match key in Firebase
    private String dte_code;     // Match key in Firebase

    public College() {
        // Default constructor required for Firebase
    }

    public College(String college_name, String dte_code) {
        this.college_name = college_name;
        this.dte_code = dte_code;
    }

    public String getCollege_name() {
        return college_name;
    }

    public String getDte_code() {
        return dte_code;
    }
}
