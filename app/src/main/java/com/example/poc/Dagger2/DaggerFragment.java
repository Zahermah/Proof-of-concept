package com.example.poc.Dagger2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.poc.R;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DaggerFragment extends Fragment {

    @Inject
    DaggerNetworkApi daggerNetworkApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dagger_network_layout, container, false);

        boolean isInjected = daggerNetworkApi == null ? false : true;

        TextView daggerTextView = rootView.findViewById(R.id.showTextInjected);
        daggerTextView.setText("Injection complete" + String.valueOf(isInjected));

        return rootView;
    }
}
