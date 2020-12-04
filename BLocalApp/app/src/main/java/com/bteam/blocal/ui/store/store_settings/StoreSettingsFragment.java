package com.bteam.blocal.ui.store.store_settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.bteam.blocal.R;
import com.google.firebase.auth.FirebaseAuth;

public class StoreSettingsFragment extends PreferenceFragmentCompat {

    private StoreSettingsViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(StoreSettingsViewModel.class);

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.store_settings, rootKey);
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