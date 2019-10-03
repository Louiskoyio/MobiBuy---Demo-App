package com.project.admin.mobibuy;

public class MobiConstants {
    // web service url constants
    public class ServiceType {
        public static final String BASE_URL = "http://192.168.0.15/mobibuy/php/";
        public static final String LOGIN = BASE_URL + "login.php";
        public static final String REGISTER =  BASE_URL + "register.php";

    }
    // webservice key constants
    public class Params {

        public static final String FIRSTNAME = "firstname";
        public static final String LASTNAME = "lastname";
        public static final String BALANCE = "balance";
        public static final String PHONENUMBER = "phonenumber";
        public static final String PASSWORD = "password";
    }
}