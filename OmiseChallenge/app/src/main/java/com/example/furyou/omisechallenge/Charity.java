package com.example.furyou.omisechallenge;

/**
 * Class containing all the information regarding a Charity (name & url of its logo
 *
 **/

public class Charity {
    private String name;
    private String logo_url;

    public Charity(String name, String logo_url) {
        this.name = name;
        this.logo_url = logo_url;
    }


    public String get_name() {
        return this.name;
    }

    public String get_logo_url() {
        return this.logo_url;
    }
}
