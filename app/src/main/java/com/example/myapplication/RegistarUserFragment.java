package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistarUserFragment extends Fragment implements View.OnClickListener {

    RegisterSelected mContext;
    private EditText email, password, confirmarPassword;
    private Button registarUserButton, voltarButton;
    private Context context;
    private TextView errorMessage;

    public RegistarUserFragment() {
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

        View v = inflater.inflate(R.layout.registo_fragment, container, false);
        email = v.findViewById(R.id.editTextTextRegisterEmailAddress);
        password = v.findViewById(R.id.editTextTextRegisterPassword);
        confirmarPassword = v.findViewById(R.id.editTextTextRegisterPasswordConfirmar);

        errorMessage = v.findViewById(R.id.textViewErrorRegister);
        registarUserButton = v.findViewById(R.id.registarUserButton);

        registarUserButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        String userMail = email.getText().toString();
        String userPass = password.getText().toString();
        String confirmarPass = confirmarPassword.getText().toString();

        if (userMail.isEmpty()) {
            email.setError("Insira um email");
            email.requestFocus();
        } else if (userPass.isEmpty()) {
            password.setError("Insira uma password");
            password.requestFocus();
        } else if (password.length() < 6) {
            password.setError("A password precisa de ter pelo menos 6 caracteres");
        } else {
            if (confirmarPass.equals(userPass)) {
                mContext.OnRegisterSelected(userMail, userPass, errorMessage);
            } else {

                password.setError("Palavras passe incorretas!");
                Log.d("UserPass", userPass);
                Log.d("UserPassConfirmar", confirmarPass);
            }
        }
    }
}