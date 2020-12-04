package com.bteam.blocal.ui.store.store_settings;

import androidx.lifecycle.ViewModel;

import com.bteam.blocal.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

public class StoreSettingsViewModel extends ViewModel {

    public void logout() {
        UserRepository.getInstance().logout();
    }
}