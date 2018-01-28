package com.example.mojtaba.doodzoda.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.adapter.CommentsAdapter;
import com.example.mojtaba.doodzoda.model.Comment;
import com.example.mojtaba.doodzoda.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ServerExchange {
    private Context mContext;
    private List<Comment> mCommentList = new ArrayList<>();
    private User mUser;

    private Uri fileUri;

    private Runnable mCommentsUpdateRunnable;
    private Handler handler = new Handler();
    private CommentsAdapter mCommentsAdapter;

    private final String GET_COMMENT_URL = "http://46.4.136.80/stop-smoking/get_comments.php";
    private final String GET_USER_URL = "http://46.4.136.80/stop-smoking/get_user.php?user_id=%s";

    public ServerExchange(Context context) {
        this.mContext = context;
    }

    public ServerExchange(Context mContext, Uri fileUri) {
        this.mContext = mContext;
        this.fileUri = fileUri;
    }

    public void requestAllComments() {
        GetCommentsTask getCommentsTask = new GetCommentsTask();
        getCommentsTask.execute(GET_COMMENT_URL);

    }

    public User requestUser(int userId) {
        GetUserTask getUserTask = new GetUserTask();
        getUserTask.execute(GET_USER_URL, String.valueOf(userId));

        try {
            return getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void uploadImage() {
        PutImageTask putImageTask = new PutImageTask();
        File file = new File(getRealPathFromURI(mContext, fileUri));
        putImageTask.execute(file);
    }

    private class GetCommentsTask extends AsyncTask<String, String, List<Comment>> {
        ProgressBar progressBar = ((Activity) mContext).findViewById(R.id.main_comments_loader);

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Comment> doInBackground(String... strings) {
            RequestParser requestParser = new RequestParser(strings[0]);
            String comments = requestParser.getUrlContents();

            String commentData = "";
            if (comments != null && !comments.equals("")) {
                commentData = new Gson().fromJson(comments, JsonObject.class).get("comments").toString();
            }

            Type listType = new TypeToken<List<Comment>>() {

            }.getType();

            if (!commentData.equals("")) {
                mCommentList = new Gson().fromJson(commentData, listType);
            } else {
                mCommentList = new ArrayList<>();
            }

            return mCommentList;
        }

        @Override
        protected void onPostExecute(List<Comment> commentList) {
            progressBar.setVisibility(View.INVISIBLE);
            updateDisplay();
        }
    }

    private class GetUserTask extends AsyncTask<String, Integer, User> {

        @Override
        protected User doInBackground(String... strings) {
            RequestParser requestParser = new RequestParser(String.format(strings[0], strings[1]));
            String user = requestParser.getUrlContents();

            String userData = "";
            if (user != null && !user.equals("")) {
                userData = new Gson().fromJson(user, JsonObject.class).get("user").toString();
            }

            Type dataType = new TypeToken<User>() {

            }.getType();

            if (!userData.equals("")) {
                mUser = new Gson().fromJson(userData, dataType);
            } else {
                mUser = null;
            }

            return mUser;
        }
    }

    private void updateDisplay() {
        LinearLayoutManager linearVerticalLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        final ImageButton getCommentsButton = ((Activity) mContext).findViewById(R.id.get_comments_button);

        RecyclerView mainCommentsRecyclerView = ((Activity) mContext).findViewById(R.id.comments_recycler_view);
        mainCommentsRecyclerView.setLayoutManager(linearVerticalLayoutManager);

        if (mCommentList.size() != 0) {
            mCommentsAdapter = new CommentsAdapter(mContext, mCommentList);
            mainCommentsRecyclerView.setAdapter(mCommentsAdapter);
        } else {
            getCommentsButton.setVisibility(View.VISIBLE);

            getCommentsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getCommentsButton.setVisibility(View.INVISIBLE);
                    requestAllComments();
                }
            });
        }

        mCommentsUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                mCommentsAdapter.notifyDataSetChanged();
            }
        };

        handler.postDelayed(mCommentsUpdateRunnable, 1000);
    }

    public class PutImageTask extends AsyncTask<File, Integer, Void> {

        @Override
        protected Void doInBackground(File... files) {

            try {
                String boundary = "*********";

                URL url = new URL("http://46.4.136.80/file-server/upload.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                String contentDescription = "Content-Disposition: form-data; name=\"userFile\"; filename=\"" + files[0].getName() + "\"";
                String contentType = "\nContent-Type: image/jpeg";
                String contentLength = "\nContent-Length: " + files[0].length();

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);
                urlConnection.setRequestMethod("POST");
                urlConnection.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                urlConnection.setRequestProperty("Connection", "Keep-Alive");

                String tail = "\n--" + boundary + "--\n";
                String data = "\n\n--" + boundary + "\n" +
                        contentDescription + contentType + contentLength +
                        "\n\n";

                urlConnection.setRequestProperty("Content-Length", "" + files[0].length() + tail.length());
                urlConnection.connect();

                DataOutputStream outputStreamRequestBody = new DataOutputStream(urlConnection.getOutputStream());

                outputStreamRequestBody.writeBytes(data);
                outputStreamRequestBody.flush();

                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(files[0]));

                int bytesRead;
                byte[] dataBuffer = new byte[1024];
                while ((bytesRead = bufferedInputStream.read(dataBuffer)) != -1) {
                    outputStreamRequestBody.write(dataBuffer, 0, bytesRead);
                    outputStreamRequestBody.flush();
                    publishProgress(bytesRead);
                }

                outputStreamRequestBody.writeBytes(tail);
                outputStreamRequestBody.close();

                BufferedReader httpResponseReader =
                        new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String lineRead;
                while ((lineRead = httpResponseReader.readLine()) != null) {
                    System.out.println(lineRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            TextView textView = ((Activity) mContext).findViewById(R.id.image_upload_text);
            textView.setText(String.valueOf(values[0]));
        }
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
