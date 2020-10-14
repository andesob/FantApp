package no.ntnu.FantApp2.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import no.ntnu.FantApp2.ChatService;
import no.ntnu.FantApp2.Item;
import no.ntnu.FantApp2.R;
import no.ntnu.FantApp2.RetrofitClientInstance;
import no.ntnu.FantApp2.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    View root;
    ArrayList<Item> itemList = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>();
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendListUserRequest();

        root = inflater.inflate(R.layout.fragment_home, container, false);

        // set up the RecyclerView
        recyclerView = root.findViewById(R.id.rvItems);

        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());

        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new RecyclerViewAdapter(root.getContext(), itemList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (adapter.getItem(position).isBought()) {
            builder.setCancelable(true);
            builder.setTitle("Already bought");
            builder.setMessage("This item has already been bought");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        } else {
            builder.setCancelable(true);
            builder.setTitle("Confirmation");
            builder.setMessage("Do you want to buy "
                    + adapter.getItem(position).getTitle()
                    + " for "
                    + adapter.getItem(position).getPrice()
                    + "?");
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().purchase(String.valueOf(adapter.getItem(position).getId()));
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "You bought " + adapter.getItem(position).getTitle() + "!", Toast.LENGTH_LONG).show();
                                        adapter.getItem(position).setBought();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void sendListUserRequest() {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().listUsers();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        TextView tv = getView().findViewById(R.id.tempTextview);
                        tv.setVisibility(View.INVISIBLE);
                        parseUsers(response.body().string());
                        adapter.notifyDataSetChanged();
                        sendListItemsRequest();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(response.code() == 401){
                    TextView tv = getView().findViewById(R.id.tempTextview);
                    tv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void sendListItemsRequest() {
        Call<ResponseBody> itemCall = RetrofitClientInstance.getSINGLETON().getAPI().listItems();
        itemCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> itemCall, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        parseItems(response.body().string());
                        adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void parseUsers(String jsonLine) {
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();

            String firstName = jsonObject.get("firstName").getAsString();
            String lastName = jsonObject.get("lastName").getAsString();
            String email = jsonObject.get("email").getAsString();
            String id = jsonObject.get("userid").getAsString();

            boolean exists = false;
            for (User u : userList) {
                if (u.getUserid().equals(id)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                User user = new User(id, firstName, lastName, email);
                userList.add(user);
            }
        }
    }


    private void parseItems(String jsonLine) {
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            JsonObject object = element.getAsJsonObject();

            boolean bought = object.get("bought").getAsBoolean();
            long id = object.get("id").getAsLong();
            int price = object.get("price").getAsInt();
            String title = object.get("title").getAsString();
            String description = object.get("description").getAsString();
            String userId = object.get("user").getAsJsonObject().get("userid").getAsString();

            User user = null;
            for (User u : userList) {
                if (userId.equals(u.getUserid())) {
                    user = u;
                }
            }

            boolean exists = false;
            for (Item item : itemList) {
                if (item.getId() == id) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                Item item = new Item(id, price, title, description, user, bought);
                itemList.add(item);
            }
        }
    }
}