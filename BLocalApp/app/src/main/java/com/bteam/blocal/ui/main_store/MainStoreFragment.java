package com.bteam.blocal.ui.main_store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bteam.blocal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

// Navigation inspired by https://github.com/SmartToolFactory/NavigationComponents-Tutorials/blob/master/Tutorial1-3Navigation-NestedNavHost/src/main/java/com/smarttoolfactory/tutorial1_3navigation_nestednavhost/navhost/HomeNavHostFragment.kt
// and by https://proandroiddev.com/handle-complex-navigation-flow-with-single-activity-and-android-jetpacks-navigation-component-6ad988602902
// https://stackoverflow.com/questions/50730494/new-navigation-component-from-arch-with-nested-navigation-graph
public class MainStoreFragment extends Fragment {

    public MainStoreFragment() {
        // Required empty public constructor
    }


    private NavController _navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView navView = view.findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_list, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        _navController = ((NavHostFragment)getChildFragmentManager().findFragmentById(R.id.nav_store_host_fragment)).getNavController();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, _navController);

        listenToBackStack();
    }

    @Override
    public void onPause() {
        super.onPause();
        _backNavCallback.setEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        _backNavCallback.setEnabled(true);
    }

    void listenToBackStack() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), _backNavCallback);

    }

    private final OnBackPressedCallback _backNavCallback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            if (_navController.getCurrentDestination().getId() == _navController.getGraph().getStartDestination()) {
                /*
                    Disable this callback because calls OnBackPressedDispatcher
                     gets invoked  calls this callback  gets stuck in a loop
                 */
                setEnabled(false);
                requireActivity().onBackPressed();
                setEnabled(true);

            } else if (isVisible()) {
                _navController.navigateUp();
            }
        }
    };
}