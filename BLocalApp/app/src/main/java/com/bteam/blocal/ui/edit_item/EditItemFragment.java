package com.bteam.blocal.ui.edit_item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bteam.blocal.R;
import com.bteam.blocal.utility.EditTextButton;
import com.google.android.material.textfield.TextInputLayout;

public class EditItemFragment extends Fragment {

    private EditItemViewModel mViewModel;

    private ImageButton itemImageBtn;
    private Toolbar toolbar;

    private TextInputLayout nameTxtInp, descrTxtInp, priceTxtInp, codeTxtInp;
    public EditItemFragment() {
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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        toolbar.setTitle("Edit item");

        EditTextButton.setOnRightDrawableClicked(codeTxtInp.getEditText(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //TODO: Scan
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditItemViewModel.class);
        // TODO: Use the ViewModel
    }

}