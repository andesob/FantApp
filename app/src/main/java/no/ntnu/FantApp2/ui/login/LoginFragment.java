package no.ntnu.FantApp2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import no.ntnu.FantApp2.R;
import no.ntnu.FantApp2.ui.home.HomeViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

        //loginViewModel =
        //        ViewModelProviders.of(this).get(LoginViewModel.class);

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        /*final TextView textView = root.findViewById(R.id.text_login);
        loginViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }
}