package com.bteam.blocal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.utility.EditTextButton;
import com.bteam.blocal.utility.NavigationResult;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class CreateStoreFragment extends Fragment {

    private ImageButton itemImageBtn;

    private TextInputLayout nameTxtInp, descrTxtInp, priceTxtInp, codeTxtInp;

    private CheckBox checkBox;
    private String imageUrl;

    public CreateStoreFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //vm = new ViewModelProvider(this).get(EditItemViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_store, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemImageBtn = view.findViewById(R.id.btn_item_picture);
        nameTxtInp = view.findViewById(R.id.txt_inp_name);
        codeTxtInp = view.findViewById(R.id.txt_inp_code);
        checkBox = view.findViewById(R.id.checkbox_in_stock);
        priceTxtInp = view.findViewById(R.id.txt_inp_price);
        descrTxtInp = view.findViewById(R.id.txt_inp_description);


        EditTextButton.setOnRightDrawableClicked(codeTxtInp.getEditText(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigatePlacePicker();
            }
        });

        itemImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  selectImage();
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

        Objects.requireNonNull(NavigationResult.<LatLng>getNavigationResult(this, null)).observe(getViewLifecycleOwner(), o -> {
            if (o != null) {
                codeTxtInp.getEditText().setText(o.toString());
            }
        });
    }

    private void navigatePlacePicker() {
        NavHostFragment.findNavController(this).navigate(CreateStoreFragmentDirections.actionCreateStoreFragmentToPlacePickerFragment());
    }

    private void updateUi(ItemModel data) {
        nameTxtInp.getEditText().setText(data.getName());
        priceTxtInp.getEditText().setText(Float.toString(data.getPrice()));
        descrTxtInp.getEditText().setText(data.getDescription());
        checkBox.setChecked(data.getStock() > 0);
        if (data.getImageUrl() != null && !data.getImageUrl().isEmpty()) {
            Glide.with(getContext()).load(data.getImageUrl()).centerCrop()
                    .into(itemImageBtn);
        }

    }

}