package com.bteam.blocal.ui.store.edit_item;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bteam.blocal.R;
import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.data.model.Resource;
import com.bteam.blocal.data.repository.StoreRepository;
import com.bteam.blocal.utility.EditTextButton;
import com.bteam.blocal.utility.ImageSelector;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

public class EditItemFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private EditItemViewModel vm;


    private ImageButton itemImageBtn;
    private Toolbar toolbar;

    private TextInputLayout nameTxtInp, descrTxtInp, priceTxtInp, codeTxtInp;

    private CheckBox checkBox;
    private String imageUrl;

    public EditItemFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(EditItemViewModel.class);
        vm.setArguments(getArguments());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemImageBtn = view.findViewById(R.id.btn_item_picture);
        nameTxtInp = view.findViewById(R.id.txt_inp_name);
        codeTxtInp = view.findViewById(R.id.txt_inp_code);
        toolbar = view.findViewById(R.id.tlb_edit_item);
        checkBox = view.findViewById(R.id.checkbox_in_stock);
        priceTxtInp = view.findViewById(R.id.txt_inp_price);
        descrTxtInp = view.findViewById(R.id.txt_inp_description);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateBack();
            }
        });

        toolbar.setTitle("Edit item");

        EditTextButton.setOnRightDrawableClicked(codeTxtInp.getEditText(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Scan
            }
        });

        itemImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        vm.getItemDetail().observe(getViewLifecycleOwner(), new Observer<Resource<ItemModel>>() {
            @Override
            public void onChanged(Resource<ItemModel> itemModelResource) {
                switch (itemModelResource.status) {
                    case SUCCESS:
                        updateUi(itemModelResource.data);
                        break;
                }
            }
        });

        toolbar.setOnMenuItemClickListener(this);

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

    private void navigateBack() {
        getActivity().onBackPressed();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_save:
                String name = nameTxtInp.getEditText().getText().toString();

                float price;
                try {
                    price = Float.parseFloat(priceTxtInp.getEditText().getText().toString());
                } catch (Exception err) {
                    price = 0;
                }

                int stock = checkBox.isChecked() ? 1 : 0;
                String description = descrTxtInp.getEditText().getText().toString();
                ItemModel itemModel = new ItemModel(name, imageUrl, price, stock, description);

                if (vm.getIsModeEdit()) {
                    vm.updateItem(itemModel, new StoreRepository.IOnCompleteCallback<Void>() {
                        @Override
                        public void onError(Throwable err) {

                        }

                        @Override
                        public void onSuccess(Void data) {
                            navigateBack();
                        }
                    });
                } else {
                    vm.createItem(itemModel, new StoreRepository.IOnCompleteCallback<ItemModel>() {
                        @Override
                        public void onError(Throwable err) {

                        }

                        @Override
                        public void onSuccess(ItemModel data) {
                            navigateBack();
                        }
                    });
                }
                break;
        }
        return false;
    }

    private void selectImage() {
        ImageSelector.requestImage(this);
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
                    imageUrl = null;
                }

                @Override
                public void onSuccess(String data) {
                    imageUrl = data;
                }
            });
        }
    }

}