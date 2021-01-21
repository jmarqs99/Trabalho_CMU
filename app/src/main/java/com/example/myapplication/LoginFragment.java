package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    LoginSelected mContext;
    private Context context;
    //private FirebaseAuth mAuth;
    private EditText email, password;
    private Button loginButton;

    private Button proxAtivity;
    private TextView teste;

    public LoginFragment() {
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
        this.mContext = (LoginSelected) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_login_fragment, container, false);
        email = v.findViewById(R.id.editTextTextEmailAddress);
        password = v.findViewById(R.id.editTextTextPassword);
        loginButton = v.findViewById(R.id.button3);
        teste = v.findViewById(R.id.textViewTeste);

        proxAtivity = v.findViewById(R.id.buttonClassi);

        //mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
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
                    mContext.onSelected(userMail, userPass);
                }
            }
        });



        return v;
    }



}