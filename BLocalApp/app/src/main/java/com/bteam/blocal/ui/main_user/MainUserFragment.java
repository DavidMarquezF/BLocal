package com.bteam.blocal.ui.main_user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bteam.blocal.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;

public class MainUserFragment extends Fragment {
    private static final String TAG = "MainUserFragment";
    private NavController navController;

    private MainUserViewModel mainUserViewModel;
    private TextView txtCurrentUser, txtEmail;
    private ImageView userProfileImage;

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


        NavigationView userNavView = view.findViewById(R.id.user_nav_view);
        View headerView = userNavView.getHeaderView(0);

        txtCurrentUser = headerView.findViewById(R.id.user_nav_header_username);
        txtEmail = headerView.findViewById(R.id.user_nav_header_email);
        userProfileImage = headerView.findViewById(R.id.user_nav_header_icon);

        mainUserViewModel.getCurrentUser().observe(getViewLifecycleOwner(),
                user -> {
                    txtEmail.setText(user.getEmail());
                    txtCurrentUser.setText(user.getDisplayName());
                    Glide.with(this).load(user.getPhotoUrl()).apply(new RequestOptions()
                            .placeholder(R.drawable.ic_outline_account_circle_24)
                            .error(R.drawable.ic_outline_account_circle_24)).into(userProfileImage);
                });


        navController = ((NavHostFragment) getChildFragmentManager()
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