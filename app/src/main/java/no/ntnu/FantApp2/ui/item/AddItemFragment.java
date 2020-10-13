package no.ntnu.FantApp2.ui.item;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import no.ntnu.FantApp2.R;
import no.ntnu.FantApp2.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_item, container, false);

        super.onCreate(savedInstanceState);

        final EditText titleEditText = root.findViewById(R.id.item_title);
        final EditText descriptionEditText = root.findViewById(R.id.item_description);
        final EditText priceEditText = root.findViewById(R.id.item_price);
        final Button addItemButton = root.findViewById(R.id.btn_addItem);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem(titleEditText.getText().toString(),
                        descriptionEditText.getText().toString(),
                        priceEditText.getText().toString());
            }
        });


        return root;
    }

    private void addNewItem(String title, String description, String price) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().addItem(title, description, price);
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