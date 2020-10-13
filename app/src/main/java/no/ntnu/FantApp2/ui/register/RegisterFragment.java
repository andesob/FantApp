package no.ntnu.FantApp2.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;

import no.ntnu.FantApp2.R;
import no.ntnu.FantApp2.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        super.onCreate(savedInstanceState);

        registerViewModel =
                ViewModelProviders.of(this).get(RegisterViewModel.class);

        final EditText usernameEditText = root.findViewById(R.id.et_name);
        final EditText passwordEditText = root.findViewById(R.id.et_password);
        final EditText emailEditText = root.findViewById(R.id.et_email);
        final EditText firstNameEditText = root.findViewById(R.id.et_firstName);
        final EditText lastNameEditText = root.findViewById(R.id.et_lastName);
        final Button registerButton = root.findViewById(R.id.btn_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser(emailEditText.getText().toString(),
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString());
            }
        });

        return root;
    }

    private void registerNewUser(String email, String uid, String password, String firstName, String lastName){
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().createUser(email, uid, password, firstName, lastName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}