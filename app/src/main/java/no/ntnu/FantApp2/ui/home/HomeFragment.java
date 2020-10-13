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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import no.ntnu.FantApp2.ChatService;
import no.ntnu.FantApp2.Item;
import no.ntnu.FantApp2.R;
import no.ntnu.FantApp2.User;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    View root;
    ArrayList<Item> itemList = null;
    ChatService chatService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.out.println("STARTED NOW");
        chatService = ChatService.getInstance();
        if (chatService != null) {
            //chatService.findAllUsers();
            //chatService.findAllItems();
        }

        root = inflater.inflate(R.layout.fragment_home, container, false);
        System.out.println(chatService + " CHATSERVICE");
        if (chatService != null) {
            itemList = chatService.showAllItems();

            // set up the RecyclerView
            RecyclerView recyclerView = root.findViewById(R.id.rvItems);

            LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());

            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            adapter = new RecyclerViewAdapter(root.getContext(), itemList);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        }
        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        System.out.println("YES");
    }
}