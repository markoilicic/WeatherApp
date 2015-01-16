package com.undabot.weatherapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.undabot.weatherapp.R;
import com.undabot.weatherapp.data.utils.SharedPrefsUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NavigationDrawerFragment extends Fragment {

    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    @InjectView(R.id.btn_drawer_edit_city_list)
    ImageButton btnEditCityList;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mIsUserLearnedDrawer;
    private boolean mIsFromSavedInstanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Read from shared preferences
        mIsUserLearnedDrawer = Boolean.valueOf(SharedPrefsUtils.getFromPreferences(KEY_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mIsFromSavedInstanceState = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    /**
     * Setup navigation drawer
     *
     * @param navDrawerId
     * @param drawerLayout
     * @param appBar
     */
    public void setUp(int navDrawerId, DrawerLayout drawerLayout, final Toolbar appBar) {
        View drawerContainer = getActivity().findViewById(navDrawerId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, appBar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //If user started for first time, set isUserLearnedDrawer = true so drawer doesn't opens in next start
                if (!mIsUserLearnedDrawer) {
                    mIsUserLearnedDrawer = true;
                    SharedPrefsUtils.saveToPreferences(KEY_USER_LEARNED_DRAWER, "true");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                //Change appBar transparency on drawer slide
                if (slideOffset < 0.4) {
                    appBar.setAlpha(1 - slideOffset);
                }
            }
        };

        //Show drawer if user never seen it
        if (!mIsUserLearnedDrawer && !mIsFromSavedInstanceState) {
            mDrawerLayout.openDrawer(drawerContainer);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //Sync the state of appBar icon
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        btnEditCityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditCityListActivity.class));
            }
        });

    }

}
