package com.bteam.blocal.ui.store;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Fragment supporting Dual Pane layout for tablets adding more functionality to the
 * ItemDetailFragment
 */
public class ItemDetailStoreDualPaneFragment extends ItemDetailStoreFragment {
    DualPaneCallback dpCallback;

    public interface DualPaneCallback {
        void navigateToEdit(String uid);
    }

    public void setDpCallback(DualPaneCallback dpCallback) {
        this.dpCallback = dpCallback;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void navigateEdit() {
        String uid = getArguments().getString("itemUid");
        dpCallback.navigateToEdit(uid);
    }
}
