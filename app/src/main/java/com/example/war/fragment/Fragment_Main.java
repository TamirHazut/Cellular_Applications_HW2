package com.example.war.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.war.R;
import com.example.war.logic.Constants;
import com.example.war.logic.TopTenTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Fragment_Main extends Fragment_Base {
    private Button main_BTN_start;
    private Button main_BTN_top_ten;
    private ExecutorService pool;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pool = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.pool.shutdown();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.pool.execute(new TopTenTask(getPrefs()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            playSound(R.raw.background_music);
        }
        findViews(view);
        initViews();
    }


    private void findViews(View view) {
        this.main_BTN_start = view.findViewById(R.id.main_BTN_start);
        this.main_BTN_top_ten = view.findViewById(R.id.main_BTN_top_ten);
    }

    private void initViews() {
        this.main_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Game fragment_game = new Fragment_Game();
                switchFragments(fragment_game);
            }
        });
        this.main_BTN_top_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_TopTen fragment_topTen = new Fragment_TopTen();
                switchFragments(fragment_topTen);
            }
        });
    }
    private void switchFragments(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FGMT_container, fragment, fragment.getClass().getSimpleName()).addToBackStack(getClass().getSimpleName()).commit();
    }

}
