package com.bteam.blocal.ui.shared.create_store;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bteam.blocal.R;
import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.data.model.StoreModel;
import com.bteam.blocal.data.repository.StoreRepository;
import com.bteam.blocal.ui.user.main_user.MainUserFragmentDirections;
import com.bteam.blocal.utility.EditTextButton;
import com.bteam.blocal.utility.ImageSelector;
import com.bteam.blocal.utility.NavigationResult;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class CreateStoreFragment extends Fragment {

    private ImageButton itemImageBtn;

    private TextInputLayout nameTxtInp, descrTxtInp, locTxtInp;

    private CreateStoreViewModel vm;

    public CreateStoreFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        vm = new ViewModelProvider(this).get(CreateStoreViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_store, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_create_store, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_save) {
            saveItem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveItem() {
        String name = nameTxtInp.getEditText().getText().toString();
        String description = descrTxtInp.getEditText().getText().toString();
        boolean valid = true;
        if (name.isEmpty()) {
            nameTxtInp.setError(getText(R.string.err_required));
            valid = false;
        }
        if (description.isEmpty()) {
            descrTxtInp.setError(getText(R.string.err_required));
            valid = false;
        }
        if (vm.getImageUrl() == null || vm.getImageUrl().isEmpty()) {
            Snackbar.make(getView(), R.string.err_image_required, Snackbar.LENGTH_LONG).show();
            valid = false;
        }
        if (vm.getLocation() == null) {
            Snackbar.make(getView(), R.string.err_loc_required, Snackbar.LENGTH_LONG).show();
            valid = false;
        }
        if (valid) {
            vm.createStore(name, description, new StoreRepository.IOnCompleteCallback<StoreModel>() {
                @Override
                public void onError(Throwable err) {

                }

                @Override
                public void onSuccess(StoreModel data) {
                    navigateToMyStore();
                }
            });
        }
    }

    private void navigateToMyStore() {
        NavController navHostController = Navigation.findNavController(getActivity(), R.id.main_nav_host_fragment);
        navHostController.navigate(MainUserFragmentDirections.actionMainUserFragmentToMainStoreFragment());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemImageBtn = view.findViewById(R.id.btn_item_picture);
        nameTxtInp = view.findViewById(R.id.txt_inp_name);
        locTxtInp = view.findViewById(R.id.txt_inp_code);
        descrTxtInp = view.findViewById(R.id.txt_inp_description);


        EditTextButton.setOnRightDrawableClicked(locTxtInp.getEditText(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigatePlacePicker();
            }
        });

        itemImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

       /* vm.getItemDetail().observe(getViewLifecycleOwner(), new Observer<Resource<ItemModel>>() {
            @Override
            public void onChanged(Resource<ItemModel> itemModelResource) {
                switch (itemModelResource.status) {
                    case SUCCESS:
                        updateUi(itemModelResource.data);
                        break;
                }
            }
        });*/

        // Set in config changes
        if (vm.getLocation() != null) {
            setLocText(vm.getLocation());
        }
        if (vm.getImageUrl() != null && !vm.getImageUrl().isEmpty()) {
            Glide.with(getContext()).load(vm.getImageUrl()).centerCrop()
                    .into(itemImageBtn);
        }


        Objects.requireNonNull(NavigationResult.<LatLng>getNavigationResult(this, null)).observe(getViewLifecycleOwner(), o -> {
            if (o != null) {
                setLocText(o);
                vm.setLocation(o);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ImageSelector.handleRequestPermission(this, requestCode, permissions, grantResults);
    }

    private void selectImage() {
        ImageSelector.requestImage(this);
    }

    private void setLocText(LatLng loc) {
        locTxtInp.getEditText().setText(loc.toString());
    }

    private void navigatePlacePicker() {
        NavHostFragment.findNavController(this).navigate(CreateStoreFragmentDirections.actionCreateStoreFragmentToPlacePickerFragment());
    }

    private void updateUi(ItemModel data) {
        nameTxtInp.getEditText().setText(data.getName());
        descrTxtInp.getEditText().setText(data.getDescription());
        if (data.getImageUrl() != null && !data.getImageUrl().isEmpty()) {
            Glide.with(getContext()).load(data.getImageUrl()).centerCrop()
                    .into(itemImageBtn);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap image = ImageSelector.onActitityResultImageHandler(getActivity().getContentResolver(), requestCode, resultCode, data);
        if (image != null) {
            itemImageBtn.setImageBitmap(image);
            itemImageBtn.setScaleType(ImageView.ScaleType.CENTER_CROP);

            vm.uploadImage(image, new StoreRepository.IOnCompleteCallback<String>() {
                @Override
                public void onError(Throwable err) {
                    itemImageBtn.setImageResource(R.drawable.ic_outline_camera_alt_24);
                    itemImageBtn.setScaleType(ImageView.ScaleType.CENTER);
                    vm.setImageUrl(null);
                }

                @Override
                public void onSuccess(String data) {
                    vm.setImageUrl(data);
                }
            });
        }
    }
}