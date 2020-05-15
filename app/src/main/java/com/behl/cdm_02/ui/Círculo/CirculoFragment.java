package com.behl.cdm_02.ui.Círculo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.behl.cdm_02.FragmentHomeScreen_02;
import com.behl.cdm_02.FragmentHomeScreen_03;
import com.behl.cdm_02.FragmenteHomeScreen_01;
import com.behl.cdm_02.R;
import com.behl.cdm_02.SectionsPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class CirculoFragment extends Fragment {

    private CirculoViewModel homeViewModel;

    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =  ViewModelProviders.of(this).get(CirculoViewModel.class);
        View root = inflater.inflate(R.layout.circulo_fragment_screen, container, false);

        Log.i("ONCREATEVIEW_FROM_CIRCULOFRAGMENT", "IN_ONCREATEVIEW");

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSectionsPageAdapter = new SectionsPageAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) getView().findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager());
        adapter.addFragment(new FragmenteHomeScreen_01(), "Família");
        adapter.addFragment(new FragmentHomeScreen_02(), "Localização");
        adapter.addFragment(new FragmentHomeScreen_03(), "Visualização");
        viewPager.setAdapter(adapter);
    }
}