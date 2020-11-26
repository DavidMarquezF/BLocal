package com.bteam.blocal.ui.main_user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainUserViewModel extends ViewModel {
    // TODO: consider replacing it with the user model object mapped from the database
    private MutableLiveData<String> currentUser;
    private MutableLiveData<String> email;

    public MainUserViewModel() {
        this.currentUser = new MutableLiveData<>();
        this.email = new MutableLiveData<>();
    }

    public LiveData<String> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser.setValue(currentUser);
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }
}
