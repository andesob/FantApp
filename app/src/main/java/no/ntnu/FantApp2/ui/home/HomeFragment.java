package no.ntnu.FantApp2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import no.ntnu.FantApp2.ChatService;
import no.ntnu.FantApp2.Item;
import no.ntnu.FantApp2.R;
import no.ntnu.FantApp2.User;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    private HomeViewModel homeViewModel;
    View root;
    ArrayList<Item> itemList = null;
    ChatService chatService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatService = ChatService.getInstance();
        if (chatService != null) {
            //chatService.findAllItems();
            chatService.findAllUsers();
            System.out.println("ACTUALLY FINDING ITEMS");

        }

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");


        if (chatService != null) {

            //ArrayList<User> userList = chatService.showAllUsers();

            itemList = chatService.showAllItems();
            // set up the RecyclerView
            RecyclerView recyclerView = root.findViewById(R.id.rvItems);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            adapter = new RecyclerViewAdapter(root.getContext(), itemList);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        }

        /*final TextView textView = root.findViewById(R.id.text_home);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        System.out.println("YES");

    }
}