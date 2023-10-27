package com.example.noteapp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NoteAppApi {

    private static final String BASE_URL = "http://192.168.0.172:5000";

    private final OkHttpClient client = new OkHttpClient();
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Define JSON media type for request and response
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    // Callback interface to handle API responses
    public interface ApiCallback {
        void onResponse(String response);
        void onFailure(Exception e);
    }

    //GET
    public void getNotes(final ApiCallback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/notes")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();
                    handler.post(() -> callback.onResponse(responseBody));
                } else {
                    handler.post(() -> callback.onFailure(new IOException("Request failed")));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("NoteAppApi", "API request failed: " + e.getMessage());
                handler.post(() -> callback.onFailure(e));
            }
        });
        Log.d("NoteAppApi", "After making the API request");
    }

    //POST
    public void createNote(String title, String content, String status, final ApiCallback callback) {
        String json = "{\"note_title\":\"" + title + "\",\"note_content\":\"" + content + "\",\"note_status\":\"" + status + "\"}";

        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/notes")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();
                    handler.post(() -> callback.onResponse(responseBody));
                } else {
                    handler.post(() -> callback.onFailure(new IOException("Request failed")));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(() -> callback.onFailure(e));
            }
        });
    }

    //PUT
    public void updateNote(int noteId, String title, String content, String status, final ApiCallback callback) {
        String json = "{\"note_title\":\"" + title + "\",\"note_content\":\"" + content + "\",\"note_status\":\"" + status + "\"}";

        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/notes/" + noteId)
                .put(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();
                    handler.post(() -> callback.onResponse(responseBody));
                } else {
                    handler.post(() -> callback.onFailure(new IOException("Request failed")));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(() -> callback.onFailure(e));
            }
        });
    }

    //DELETE
    public void deleteNote(int noteId, final ApiCallback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/notes/" + noteId)
                .delete()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();
                    handler.post(() -> callback.onResponse(responseBody));
                } else {
                    handler.post(() -> callback.onFailure(new IOException("Request failed")));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(() -> callback.onFailure(e));
            }
        });
    }

}
