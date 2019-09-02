package com.example.poc.Dagger2;

import javax.inject.Inject;

public class DaggerNetworkApi {

    @Inject
    public DaggerNetworkApi() {
    }


    public boolean validateUser(String userName, String passsword) {
        if (userName == null || userName.length() == 0) {
            return false;
        } else {
            return true;
        }

    }
}
