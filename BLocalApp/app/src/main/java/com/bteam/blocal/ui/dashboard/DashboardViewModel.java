package com.bteam.blocal.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bteam.blocal.R;
import com.bteam.blocal.model.DashboardButtonModel;

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
            new DashboardButtonModel(R.string.dashboard_map, R.mipmap.ic_map, R.id.action_user_nav_dashboard_to_user_nav_maps),
            new DashboardButtonModel(R.string.dashboard_stores, R.mipmap.ic_stores, R.id.action_user_nav_dashboard_to_user_nav_store_list),
            new DashboardButtonModel(R.string.dashboard_search, R.mipmap.ic_search, R.id.action_user_nav_dashboard_to_user_nav_item_list),
            new DashboardButtonModel(R.string.dashboard_shopping_lists, R.mipmap.ic_shopping_lists, R.id.action_user_nav_dashboard_to_user_nav_shopping_lists)
    };

}