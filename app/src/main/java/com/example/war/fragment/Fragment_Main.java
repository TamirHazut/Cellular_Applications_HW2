package com.example.war.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.war.R;
import com.example.war.logic.data.DataPassString;
import com.example.war.logic.data.PlayerRepositoryImplementation;
import com.example.war.logic.data.repo.PlayerRepository;


public class Fragment_Main extends Fragment {
    private PlayerRepository playersRepository;
    private Button main_BTN_start;
    private Button main_BTN_top_ten;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.playersRepository = new PlayerRepositoryImplementation();
        this.playersRepository.updateTopPlayers();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
                switchFragments(fragment_game, fragment_game.getArguments());
            }
        });
        this.main_BTN_top_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_TopTen fragment_topTen = new Fragment_TopTen();
                Bundle args = new Bundle();
                args.putSerializable(DataPassString.LIST.toString(), playersRepository.findTopPlayers());
                fragment_topTen.setArguments(args);
                switchFragments(fragment_topTen, args);
            }
        });
    }
    private void switchFragments(Fragment fragment, Bundle bundle) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FGMT_container, fragment, fragment.getClass().getSimpleName()).addToBackStack(getClass().getSimpleName()).commit();
    }

}
