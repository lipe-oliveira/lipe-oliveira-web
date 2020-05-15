package com.behl.cdm_02;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHomeScreen_02 extends Fragment {

    public static View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null)
                parent.removeView(mView);
        }
        try {
            mView = inflater.inflate(R.layout.circulo_fragment_02_screen, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
            Log.i("ERRORFRAGMENT", e.getLocalizedMessage());
        }
        return mView;
    }
}
