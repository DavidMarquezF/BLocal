package com.bteam.blocal.ui.main_user;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bteam.blocal.R;
import com.bteam.blocal.ui.dashboard.DashboardViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

public class MainUserFragment extends Fragment {
    private static final String TAG = "MainUserFragment";
    private NavController navController;

    private MainUserViewModel mainUserViewModel;
    private TextView txtCurrentUser, txtEmail;

    public MainUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainUserViewModel =
                new ViewModelProvider(this).get(MainUserViewModel.class);

        // Fill the card header information
        // TODO: use real user from the database
        mainUserViewModel.setCurrentUser("user");
        mainUserViewModel.setEmail("user@example.com");

        NavigationView userNavView = view.findViewById(R.id.user_nav_view);
        View headerView = userNavView.getHeaderView(0);

        txtCurrentUser = headerView.findViewById(R.id.user_nav_header_username);
        txtEmail = headerView.findViewById(R.id.user_nav_header_email);

        mainUserViewModel.getCurrentUser().observe(getViewLifecycleOwner(),
                username -> txtCurrentUser.setText(username));
        mainUserViewModel.getEmail().observe(getViewLifecycleOwner(),
                email -> txtEmail.setText(email));

        MaterialCardView headerUserInfoCard = headerView.findViewById(R.id.user_nav_header_card);

        // TODO: maybe implement logout/profile dialog when the card is clicked
        headerUserInfoCard.setOnClickListener(v -> Log.d(TAG, "onViewCreated: card clicked"));
        navController = ((NavHostFragment)getChildFragmentManager()
                .findFragmentById(R.id.user_nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(userNavView, navController);

        listenToBackStack();
    }

    @Override
    public void onPause() {
        super.onPause();
        backNavCallback.setEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        backNavCallback.setEnabled(true);
    }

    void listenToBackStack() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                backNavCallback);

    }

    private final OnBackPressedCallback backNavCallback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            if (navController.getCurrentDestination().getId() == navController.getGraph()
                    .getStartDestination()) {
                /*
                    Disable this callback because calls OnBackPressedDispatcher
                     gets invoked  calls this callback  gets stuck in a loop
                 */
                setEnabled(false);
                requireActivity().onBackPressed();
                setEnabled(true);

            } else if (isVisible()) {
                navController.navigateUp();
            }
        }
    };
}