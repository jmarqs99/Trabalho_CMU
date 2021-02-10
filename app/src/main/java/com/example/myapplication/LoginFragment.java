package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment implements View.OnClickListener {

    LoginSelected mContext;
    private Context context;
    private TextInputEditText email, password;
    private Button loginButton, loginWithGoogleButton;

    private Button registarUserButton;
    private TextView errorMessage;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = (LoginSelected) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        email = v.findViewById(R.id.editTextTextEmailAddress);
        password = v.findViewById(R.id.editTextTextPassword);
        loginButton = v.findViewById(R.id.button3);
        errorMessage = v.findViewById(R.id.textViewTeste);
        loginWithGoogleButton = v.findViewById(R.id.loginGoogleButton);

        registarUserButton = v.findViewById(R.id.buttonClassi);

        loginButton.setOnClickListener(this);
        loginWithGoogleButton.setOnClickListener(this);
        registarUserButton.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //botão que irá permitir ao user fazer login na app
            case R.id.button3:
                String userMail = email.getText().toString();
                String userPass = password.getText().toString();

                if (userMail.isEmpty()) {
                    errorMessage.setText("Login falhado! Parâmetro(s) vazio(s)!");
                    errorMessage.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            errorMessage.setVisibility(View.INVISIBLE);
                        }
                    }, 2500); //desparece passados 2,5 segundos
                } else {
                    mContext.onSelected(userMail, userPass, errorMessage);
                }
                break;

            //botão que irá permitir ao user fazer login pela conta do Google
            case R.id.loginGoogleButton:
                mContext.onLoginWithGoogleSelected();
                break;

            //botão que irá registar um user na aplicação
            case R.id.buttonClassi:
                mContext.onRegisterButtonSelected();
                break;
        }
    }
}