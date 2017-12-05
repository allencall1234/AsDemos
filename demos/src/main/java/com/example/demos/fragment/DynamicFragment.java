package com.example.demos.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demos.R;

/**
 * Created by 524202 on 2017/10/9.
 */

public class DynamicFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    public static DynamicFragment newInstance() {

        Bundle args = new Bundle();

        DynamicFragment fragment = new DynamicFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
