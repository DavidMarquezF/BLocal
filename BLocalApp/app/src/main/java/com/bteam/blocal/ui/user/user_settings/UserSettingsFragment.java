package com.bteam.blocal.ui.user.user_settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.bteam.blocal.R;
import com.bteam.blocal.ui.store.edit_item.EditItemViewModel;
import com.bteam.blocal.ui.store.store_settings.StoreSettingsViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class UserSettingsFragment extends PreferenceFragmentCompat {

    UserSettingsViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(UserSettingsViewModel.class);

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        switch (key){
            case "btn_logout":
                vm.logout();
                return true;
        }
        return super.onPreferenceTreeClick(preference);
    }
}