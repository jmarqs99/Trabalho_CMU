package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.API.RetrofitClient;
import com.example.myapplication.API.SportsDataAPI;
import com.example.myapplication.RecyclerView.EquipaAdapter;
import com.example.myapplication.RecyclerView.JogoAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JogosAoVivoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JogosAoVivoFragment extends Fragment {

    RecyclerView mRecyclerView;
    JogoAdapter mAdapter;
    final SportsDataAPI service = RetrofitClient.getApi();

    public JogosAoVivoFragment() {
        // Required empty public constructor
    }


    public static JogosAoVivoFragment newInstance(String param1, String param2) {
        JogosAoVivoFragment fragment = new JogosAoVivoFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_jogos_ao_vivo, container, false);

        mRecyclerView = v.findViewById(R.id.mRecyclerViewJogos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }
}