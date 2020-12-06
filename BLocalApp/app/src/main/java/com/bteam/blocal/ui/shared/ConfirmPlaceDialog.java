package com.bteam.blocal.ui.shared;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bteam.blocal.R;
import com.bteam.blocal.utility.NavigationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

//Based on tutorial https://www.youtube.com/watch?v=Z5hONYWa0b4
public class ConfirmPlaceDialog extends DialogFragment implements OnMapReadyCallback {
    public static final String DIALOG_LOC_RESULT_KEY = "dialog_loc";
    private GoogleMap mMap;
    private LatLng pos;
    private String address;
    private TextView adressTxt;
    private View v;
    private SupportMapFragment mapFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfirmPlaceDialogArgs args = ConfirmPlaceDialogArgs.fromBundle(getArguments());
        pos = args.getPos();
        address = args.getAddress();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        v = LayoutInflater.from(getContext()).inflate(R.layout.partial_confirm_location, null,
                false);
        adressTxt = v.findViewById(R.id.myAddress);

        mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.mapp);
        mapFragment.getMapAsync(this);

        return new MaterialAlertDialogBuilder(getContext())
                .setView(v)
                .setPositiveButton(R.string.lbl_select, (dialogInterface, i) -> {
                    NavigationResult.setNavigationResult(this, DIALOG_LOC_RESULT_KEY, true);
                    dialogInterface.dismiss();
                })
                .setNegativeButton(R.string.lbl_change_loc, (dialogInterface, i) -> dialogInterface
                        .dismiss())
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        adressTxt.setText(address);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(pos);

        markerOptions.title(address);
        mMap.clear();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(pos, 16f);
        mMap.animateCamera(location);
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mapFragment)
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(mapFragment)
                    .commit();
    }
}
