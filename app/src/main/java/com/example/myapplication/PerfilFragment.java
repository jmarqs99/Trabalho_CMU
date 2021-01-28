package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class PerfilFragment extends Fragment {

    LogoutSelected lg;
    private TextView emailUser, passUser;
    private Button loggout, editarPass;

    public PerfilFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.lg = (LogoutSelected)  context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.perfil_fragment, container, false);

        emailUser = v.findViewById(R.id.textViewEmailPerfil);
        passUser = v.findViewById(R.id.textViewPasswordPerfil);
        loggout = v.findViewById(R.id.buttonLogout);
        editarPass = v.findViewById(R.id.buttonEditarPassword);

        loggout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lg.OnLogoutSelect();

                /**
                LoginFragment loginFragment = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction tr = fm.beginTransaction();

                tr.replace(R.id.fragment2,loginFragment, "voltar");
                tr.addToBackStack("voltar");
                tr.commit();
                 */
            }
        });

        return v;
    }
}
