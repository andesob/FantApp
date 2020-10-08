package no.ntnu.FantApp2;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ChatService chatService;
    LinearLayout linearLayout;
    ArrayList<Item> itemList = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>();
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        linearLayout = findViewById(R.id.linearLayout);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 10);


        chatService = ChatService.getInstance();
        if (chatService != null) {
            chatService.findAllUsers();
            chatService.findAllItems();
        }
    }
/*
    public void showAllUsers(View view) {
        if (linearLayout.getChildCount() != 0) {
            linearLayout.removeAllViews();
        }

        userList = chatService.showAllUsers();

        setupDefaultPage(true);

        for (User u : userList) {
            setupUserPage(u);
        }
    }

    public void showAllItems(View view) {
        if (linearLayout.getChildCount() != 0) {
            linearLayout.removeAllViews();
        }

        itemList = chatService.showAllItems();

        setupDefaultPage(false);

        for (Item i : itemList) {
            setupItemPage(i);
        }
    }

    private void setupDefaultPage(boolean userPage) {
        LinearLayout.LayoutParams userparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View headerView = getLayoutInflater().inflate(R.layout.header_layout, linearLayout, false);
        headerView.setLayoutParams(userparams);

        TextView leftHeader = headerView.findViewById(R.id.leftHeader);
        TextView midHeader = headerView.findViewById(R.id.midHeader);
        TextView rightHeader = headerView.findViewById(R.id.rightHeader);

        if (userPage) {
            leftHeader.setText("Name");
            midHeader.setText("User ID");
            rightHeader.setText("Email");
        } else {
            leftHeader.setText("Title");
            midHeader.setText("Item ID");
            rightHeader.setText("Price");
        }

        linearLayout.addView(headerView);
    }

    private void setupItemPage(Item item) {
        LinearLayout.LayoutParams userparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View userView = getLayoutInflater().inflate(R.layout.user_list_layout, linearLayout, false);

        TextView itemId = userView.findViewById(R.id.ID);
        TextView title = userView.findViewById(R.id.NAME);
        TextView price = userView.findViewById(R.id.EMAIL);
        itemId.setText(Long.toString(item.getId()));
        title.setText(item.getTitle());
        price.setText(Integer.toString(item.getPrice()));

        linearLayout.addView(userView);
        userView.setLayoutParams(userparams);
    }

    private void setupUserPage(User user) {
        LinearLayout.LayoutParams userparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View userView = getLayoutInflater().inflate(R.layout.user_list_layout, linearLayout, false);

        TextView userId = userView.findViewById(R.id.ID);
        TextView username = userView.findViewById(R.id.NAME);
        TextView userEmail = userView.findViewById(R.id.EMAIL);
        userId.setText(user.getUserid());
        username.setText(user.getFirstName());
        userEmail.setText(user.getEmail());
        userEmail.setTextSize(10);

        linearLayout.addView(userView);
        userView.setLayoutParams(userparams);
    }*/
}