package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegistarUserFragment extends Fragment {

    RegisterSelected mContext;
    private EditText email, password;
    private Button registarUserButton, voltarButton;
    private Context context;

    public RegistarUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = (RegisterSelected) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_registar_user_fragment, container, false);
        email = v.findViewById(R.id.editTextTextRegisterEmailAddress);
        password = v.findViewById(R.id.editTextTextRegisterPassword);
        voltarButton = v.findViewById(R.id.voltarLoginPage);

        registarUserButton = v.findViewById(R.id.registarUserButton);

        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.OnBackToLoginPageSelected();
            }
        });

        registarUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMail = email.getText().toString();
                String userPass = password.getText().toString();

                if(userMail.isEmpty()) {
                    email.setError("Insira um email");
                    email.requestFocus();
                }
                else if(userPass.isEmpty()) {
                    password.setError("Insira uma password");
                    password.requestFocus();
                }
                else if(userMail.isEmpty() || userPass.isEmpty()) {

                }
                else {
                    mContext.OnRegisterSelected(userMail, userPass);
                }
            }
        });


        return v;
    }
}