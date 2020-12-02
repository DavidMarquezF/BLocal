package com.bteam.blocal.ui.user;

import androidx.navigation.fragment.NavHostFragment;

import com.bteam.blocal.ui.shared.item_list.ItemListFragment;

/**
 * We need to create this implementation so that the NavigationComponent can use it
 * Since we reuse this fragment for the User and the Store, when Navigation Component generates
 * the NavDirections it doesn't know how to generate the actions and arguments (it does it in order
 * so user NavDirection might be generated or store NavDirection might be). This is because NavComonent
 * generates directions based on the Fragment name -- ItemListFragment -> ItemListDirections.
 * <p>
 * This is a workaround until NavComponent introduces a way to separate reused fragments
 */
public class ItemListUserFragment extends ItemListFragment {
    @Override
    protected void navigateToDetail(String uid) {
        ItemListUserFragmentDirections.ShowItemDetail dir = ItemListUserFragmentDirections.showItemDetail(uid);
        NavHostFragment.findNavController(this).navigate(dir);
    }
}
