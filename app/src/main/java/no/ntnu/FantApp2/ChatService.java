package no.ntnu.FantApp2;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import no.ntnu.FantApp2.data.model.LoggedInUser;

public class ChatService implements Response.ErrorListener {
    public static final String BASE_URL = "http://10.0.2.2:8080/Oblig1/api/";
    static ChatService SINGLETON;

    String token;
    RequestQueue requestQueue;
    JSONArray allUsersJSONArray;
    JSONArray allItemsJSONArray;
    ArrayList<User> userList;
    ArrayList<Item> itemList;

    public ChatService(Context context, String token) {
        this.token = token;
        this.requestQueue = Volley.newRequestQueue(context);
        userList = new ArrayList<>();
        itemList = new ArrayList<>();
        //findAllUsers();
    }


    public static ChatService initialize(Context context, String token) {
        SINGLETON = new ChatService(context, token);
        return SINGLETON;
    }

    public static ChatService getInstance() {
        return SINGLETON;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Error: " + error);
    }

    public User getUser() {
        String url = BASE_URL + "auth/currentuser";

        User result = new User("50", "no", "yes", "email");

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        result.setUserid(response.getString("userid"));
                        result.setFirstName(response.getString("firstName"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, this) {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Map.of("Authorization", "Bearer " + token);
            }
        };

        requestQueue.add(jor);

        return result;
    }

    public LoggedInUser setLoggedInUser(LoggedInUser user) {
        String url = BASE_URL + "auth/currentuser";

        LoggedInUser result = user;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        result.setDisplayName(response.getString("firstName"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, this) {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Map.of("Authorization", "Bearer " + token);
            }
        };

        requestQueue.add(jor);
        return result;
    }

    public void findAllUsers() {
        String url = BASE_URL + "fant/users";
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    allUsersJSONArray = response;

                }, this) {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Map.of("Authorization", "Bearer " + token);
            }
        };
        requestQueue.add(jar);

        new CountDownTimer(500, 1) {
            @Override
            public void onTick(long l) {
                if (allUsersJSONArray != null) {
                    addListedUsers();
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                System.out.println("USERS FINISHED LATE");
            }
        }.start();
    }

    public ArrayList<User> showAllUsers() {
        return userList;
    }

    public void findAllItems() {
        String url = BASE_URL + "fant/items";

        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> allItemsJSONArray = response, this) {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Map.of("Authorization", "Bearer " + token);
            }
        };


        requestQueue.add(jar);

        new CountDownTimer(500, 1) {
            @Override
            public void onTick(long l) {
                if (allItemsJSONArray != null) {
                    addListedItems();
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                System.out.println("ITEMS FINISHED LATE");
            }
        }.start();
    }

    public ArrayList<Item> showAllItems() {
        return itemList;
    }

    private void addListedItems() {
        for (int i = 0; i < allItemsJSONArray.length(); i++) {
            try {
                boolean exists = false;
                JSONObject jsonItem = allItemsJSONArray.getJSONObject(i);
                boolean bought = jsonItem.getBoolean("bought");
                long id = jsonItem.getLong("id");
                int price = jsonItem.getInt("price");
                String title = jsonItem.getString("title");
                String description = jsonItem.getString("description");
                String sellerid = jsonItem.getJSONObject("user").getString("userid");
                User seller = null;
                for (User u : userList) {
                    if (sellerid.equals(u.getUserid())) {
                        seller = u;
                    }
                }

                for (Item item : itemList) {
                    if (item.getId() == id) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    Item item = new Item(id, price, title, description, seller, bought);
                    itemList.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addListedUsers() {
        for (int i = 1; i < allUsersJSONArray.length(); i++) {
            try {
                boolean exists = false;
                JSONObject jsonUser = allUsersJSONArray.getJSONObject(i);
                String firstName = jsonUser.getString("firstName");
                String lastName = jsonUser.getString("lastName");
                String email = jsonUser.getString("email");
                String id = jsonUser.getString("userid");
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
