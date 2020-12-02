package com.bteam.blocal.ui.store.notifications;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.bteam.blocal.R;
import com.google.firebase.auth.FirebaseAuth;

public class NotificationsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.store_settings, rootKey);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        switch (key){
            case "btn_logout":
                FirebaseAuth.getInstance().signOut();
                return true;
        }
        return super.onPreferenceTreeClick(preference);
    }
}