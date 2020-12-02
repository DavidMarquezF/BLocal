package com.bteam.blocal.ui.store;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import com.bteam.blocal.R;
import com.bteam.blocal.ui.shared.item_detail.ItemDetailFragment;
import com.google.android.material.appbar.AppBarLayout;

/**
 * We need to create this implementation so that the NavigationComponent can use it
 * Since we reuse this fragment for the User and the Store, when Navigation Component generates
 * the NavDirections it doesn't know how to generate the actions and arguments (it does it in order
 * so user NavDirection might be generated or store NavDirection might be). This is because NavComonent
 * generates directions based on the Fragment name -- ItemListFragment -> ItemListDirections.
 * <p>
 * This is a workaround until NavComponent introduces a way to separate reused fragments
 */
public class ItemDetailStoreFragment extends ItemDetailFragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.inflateMenu(R.menu.menu_item_detail);
        showEditOption(false);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.btn_edit) {
                    navigateEdit();
                }
                return false;
            }
        });
        AppBarLayout appbar = view.findViewById(R.id.app_bar);
        // Inspired from https://www.journaldev.com/13927/android-collapsingtoolbarlayout-example
        appbar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showEditOption(true);
                } else if (isShow) {
                    isShow = false;
                    showEditOption(false);
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateEdit();
            }
        });

    }

    private void navigateEdit() {
        ItemDetailStoreFragmentDirections.EditItem directions = ItemDetailStoreFragmentDirections.editItem(vm.itemDetail.getValue().data.getUid());
        NavHostFragment.findNavController(this).navigate(directions);
    }

    private void showEditOption(boolean show) {
        toolbar.getMenu().findItem(R.id.btn_edit).setVisible(show);
    }
}
