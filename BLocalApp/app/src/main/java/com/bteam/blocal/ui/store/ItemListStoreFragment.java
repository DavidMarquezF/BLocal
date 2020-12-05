package com.bteam.blocal.ui.store;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.bteam.blocal.R;
import com.bteam.blocal.ui.shared.item_list.ItemListFragment;

/**
 * We need to create this implementation so that the NavigationComponent can use it
 * Since we reuse this fragment for the User and the Store, when Navigation Component generates
 * the NavDirections it doesn't know how to generate the actions and arguments (it does it in order
 * so user NavDirection might be generated or store NavDirection might be). This is because
 * NavComonent generates directions based on the Fragment name
 * -- ItemListFragment -> ItemListDirections.
 *
 * This is a workaround until NavComponent introduces a way to separate reused fragments
 */
public class ItemListStoreFragment extends ItemListFragment
        implements ItemDetailStoreDualPaneFragment.DualPaneCallback {
    private boolean isDualPane;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Check whether the device is tablet or phone
        View tabletDetail = view.findViewById(R.id.list_tablet_detail);
        isDualPane = (null != tabletDetail && tabletDetail.getVisibility() == View.VISIBLE);
    }

    @Override
    protected void navigateToDetail(String uid) {
        if (isDualPane) {
            Bundle bundle = new Bundle();
            bundle.putString("itemUid", uid);

            ItemDetailStoreDualPaneFragment fragment = new ItemDetailStoreDualPaneFragment();
            fragment.setDpCallback(this);
            fragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.list_tablet_detail, fragment)
                .commit();
        } else {
            ItemListStoreFragmentDirections.ShowItemDetail dir = ItemListStoreFragmentDirections
                    .showItemDetail(uid);
            NavHostFragment.findNavController(this).navigate(dir);
        }
    }

    // Method to be called from ItemDetailStoreDualPaneFragment
    public void navigateToEdit(String uid) {
        ItemListStoreFragmentDirections.CreateNewItem dir =  ItemListStoreFragmentDirections
                .createNewItem(uid);
        NavHostFragment.findNavController(this).navigate(dir);
    }
}
