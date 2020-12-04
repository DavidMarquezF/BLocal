package com.bteam.blocal.ui.user.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bteam.blocal.R;
import com.bteam.blocal.data.model.DashboardButtonModel;

import java.util.Arrays;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    // TODO: Use to get the user's role and create Dashboard buttons accordingly
    private MutableLiveData<String> currentUser;

    private MutableLiveData<List<DashboardButtonModel>> buttons;

    public DashboardViewModel() {
        buttons = new MutableLiveData<>();
        init();
    }

    public LiveData<List<DashboardButtonModel>> getButtons() {
        return buttons;
    }

    private void init() {
        // TODO: If user is store owner -> generate different set of buttons
        // Right now we only have set of buttons for the user with 'Customer' role
        buttons.setValue(Arrays.asList(items));
    }

    // TODO: Move to the utility class or something like that
    private DashboardButtonModel[] items = {
            new DashboardButtonModel(R.string.title_maps, R.drawable.ic_baseline_map_24, R.id.openMaps),
            new DashboardButtonModel(R.string.title_stores, R.drawable.ic_baseline_store_24, R.id.openStoreList),
            new DashboardButtonModel(R.string.title_search, R.drawable.ic_baseline_search_24, R.id.openItemList),
            new DashboardButtonModel(R.string.title_shopping_lists, R.drawable.ic_baseline_shopping_basket_24, R.id.openShoppingLists)
    };

}