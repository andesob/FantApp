package no.ntnu.FantApp2.data;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import no.ntnu.FantApp2.ChatService;
import no.ntnu.FantApp2.data.model.LoggedInUser;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    LoggedInUser fakeUser;
    HttpURLConnection c;
    String username;
    String password;
    Context context;

    public Result<LoggedInUser> login(String username, String password, Context context) {
        c = null;
        this.username = username;
        this.password = password;
        this.context = context;

        try {
            LoginTask loginTask = new LoginTask();
            synchronized (loginTask) {
                loginTask.execute(username, password).notify();
            }
            boolean check = false;
            while (!check) {
                if (fakeUser != null) {
                    return new Result.Success<>(fakeUser);
                }
            }

            return new Result.Error(new IOException("Error logging in " + c.getResponseMessage()));
            // TODO: handle loggedInUser authentication

        } catch (Exception e) {
            System.err.println("Failed to call " + e);
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        } finally {
            if (c != null) c.disconnect();
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private class LoginTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                String token = getUserToken();
                ChatService.initialize(context, token);
                if (token != null) {
                    setCurrentUser();
                } else {
                    throw new IOException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void setCurrentUser() {
            ChatService chatService = ChatService.getInstance();
            while (chatService == null) {
                chatService = ChatService.getInstance();
                chatService.findAllUsers();
                chatService.findAllItems();
            }
            chatService.setLoggedInUser(fakeUser);
            while (fakeUser.getDisplayName() == null) {
            }
        }

        private String getUserToken() throws IOException {
            URL url = new URL("http://10.0.2.2:8080/Oblig1/api/auth/login?uid=" + username + "&pwd=" + password);
            c = (HttpURLConnection) url.openConnection();
            c.setUseCaches(true);
            c.setRequestMethod("GET");
            if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8));
                String token = br.readLine();
                fakeUser = new LoggedInUser(username, token);
                c.getInputStream().close(); // Why?
                //return new Result.Success<>(fakeUser);
                return token;
            } else {
                System.err.println("Failed to log in : " + c.getResponseCode());
            }
            return null;
        }
    }
}