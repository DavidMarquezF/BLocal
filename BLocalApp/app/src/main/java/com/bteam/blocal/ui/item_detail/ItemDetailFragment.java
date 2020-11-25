package com.bteam.blocal.ui.item_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.bteam.blocal.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ItemDetailFragment extends Fragment {

    private TextView nameTxt, priceTxt, inStockTxt, descriptionTxt;
private Toolbar toolbar;
    public ItemDetailFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppBarLayout appbar =  view.findViewById(R.id.app_bar);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        nameTxt = view.findViewById(R.id.txt_item_name);
        priceTxt = view.findViewById(R.id.txt_item_price);
        inStockTxt = view.findViewById(R.id.txt_item_stock);
        descriptionTxt = view.findViewById(R.id.txt_item_description);

        FloatingActionButton fab  = view.findViewById(R.id.fab_edit);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateEdit();
            }
        });

        // Inspired from https://www.journaldev.com/13927/android-collapsingtoolbarlayout-example
        appbar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            boolean isShow= false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0){
                    isShow = true;
                    showEditOption(true);
                }
                else if(isShow){
                    isShow = false;
                    showEditOption(false);
                }
            }
        });

        toolbar.inflateMenu(R.menu.menu_item_detail);
        showEditOption(false);

        nameTxt.setText("Coffee");
        priceTxt.setText("100kr");
        inStockTxt.setText("In stock");
        descriptionTxt.setText("This is a stub description asdakjdakjdba");
    }

    private void showEditOption(boolean show) {
        toolbar.getMenu().findItem(R.id.btn_edit).setVisible(show);
    }


    private void navigateEdit() {
        NavHostFragment.findNavController(this).navigate(R.id.action_itemDetailFragment_to_editItemFragment);
    }
}