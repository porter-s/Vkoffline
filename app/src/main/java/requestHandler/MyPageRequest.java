package requestHandler;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ru.example.sapp.vkoffline.R;

/**
 * Created by Юра on 24.10.2016.
 */

public class MyPageRequest {

    String LOG_TAG = "MyPageRequest";
    VKRequest request = null;
    VKResponse _response = null;

    VKResponse getData(final String _page){

        switch (_page){
            case "myPage":
                request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_id, verified, sex, " +
                        "bdate, city, country, home_town, has_photo, photo_50, photo_100, photo_200_orig, " +
                        "photo_200, photo_400_orig, photo_max, photo_max_orig, online, lists, domain, " +
                        "has_mobile, contacts, site, education, universities, schools, status, last_seen, " +
                        "followers_count, common_count, occupation, nickname, relatives, relation, personal, " +
                        "connections, exports, wall_comments, activities, interests, music, movies, tv, books, " +
                        "games, about, quotes, can_post, can_see_all_posts, can_see_audio, can_write_private_message, " +
                        "can_send_friend_request, is_favorite, is_hidden_from_feed, timezone, screen_name, maiden_name, " +
                        "crop_photo, is_friend, friend_status, career, military, blacklisted, blacklisted_by_me"));
                request.start();

                request.setRequestListener(new VKRequest.VKRequestListener() {

                    @Override
                    public void onComplete(VKResponse response) {
                        Log.e(LOG_TAG,"getData onComplete");
                        super.onComplete(response);
                    }

                    @Override
                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                        Log.e(LOG_TAG,"getData attemptFailed");
                        super.attemptFailed(request, attemptNumber, totalAttempts);
                    }

                    @Override
                    public void onError(VKError error) {
                        Log.e(LOG_TAG,"getData onError");
                        super.onError(error);
                    }

                    @Override
                    public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                        super.onProgress(progressType, bytesLoaded, bytesTotal);
                    }
                });
            break;

            case "frends":
                request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "nickname, domain, " ));//+
                        //"sex, bdate, city, country, timezone, photo_50, photo_100, photo_200_orig, " +
                        //"has_mobile, contacts, education, online, relation, last_seen, status, " +
                        //"can_write_private_message, can_see_all_posts, can_post, universities"));
                request.start();

                request.setRequestListener(new VKRequest.VKRequestListener() {

                    @Override
                    public void onComplete(VKResponse response) {

                        super.onComplete(response);
                        _response = response;
                        saveData(_page,response);
                        Log.e(LOG_TAG,"Save File Start");
                    }

                    @Override
                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                        super.attemptFailed(request, attemptNumber, totalAttempts);
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                    }

                    @Override
                    public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                        super.onProgress(progressType, bytesLoaded, bytesTotal);
                    }
                });

                break;

            case "group":

                break;

            case "news":

                break;

            case "message":

                break;
        }

        return _response;
    }

    boolean reloadData(String _page, String _data){

        return false;
    }

    public String loadData(String _page){
        if (readData(_page)==null){
            getData(_page);
        }
        return null;
    }

    JSONObject readData(String _page){

        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return null;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + R.string.DIR_SD);
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, _page);
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.e(LOG_TAG,"str=" + str);
            }
            Log.e(LOG_TAG,"Readn File conwert JSON");
            //JSONObject jsonData = str.toString();

            JSONObject jsonData = new JSONObject(str);
            Log.e(LOG_TAG,"File yes Found");
            return jsonData;
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG,"Error File Not Found");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.e(LOG_TAG,"Error IOException");
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Log.e(LOG_TAG,"Error JSON load");
            e.printStackTrace();
            return null;
        }
    }

    boolean saveData(String _page,VKResponse _data){

        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return false;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + R.string.DIR_SD);
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, _page);
            // открываем поток для записи
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(sdFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // пишем данные
        try {
            Log.e(LOG_TAG,"JSON="+String.valueOf(_data.json));
            bw.write(String.valueOf(_data.json));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // закрываем поток
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
            return true;
    }

    public boolean deleteData(String _page){

        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return false;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + R.string.DIR_SD);
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, _page);

        Log.e(LOG_TAG,"Удаление файла");
        sdFile.delete();
        Log.e(LOG_TAG,"Файл удален");
        return false;
    }

    /*VKRequest.VKRequestListener mRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            Log.e(LOG_TAG,"Load complite");
        }

        @Override
        public void onError(VKError error) {

        }

        @Override
        public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {

        }
    };*/
}
/*
    void writeFile() {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)));
            // пишем данные
            bw.write("Содержимое файла");
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readFile() {
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeFileSD() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME_SD);
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            bw.write("Содержимое файла на SD");
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readFileSD() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME_SD);
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
