package layout;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;

import requestHandler.MyPageRequest;
import ru.example.sapp.vkoffline.R;
public class ScreenOne extends Fragment {

    public ScreenOne() {
    }

    TextView textView;
    TextView last_name;
    TextView first_name;
    ProgressBar progressBar;
    ImageView imageView;
    Context context;
    String LOG_TAG = "ScreenOne";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_screen_one, container,
                false);
        context = rootView.getContext();
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarFSO);
        //progressBar.setVisibility(View.INVISIBLE);
        //textView = (TextView) rootView.findViewById(R.id.textViewScrOne);
        last_name = (TextView) rootView.findViewById(R.id.last_name_tvFSO);
        first_name = (TextView) rootView.findViewById(R.id.first_name_tvFSO);
        imageView = (ImageView) rootView.findViewById(R.id.imageViewFSO);

        MyPageRequest myPageRequest = new MyPageRequest();
        myPageRequest.deleteData("frends");
        myPageRequest.loadData("frends");
        Log.e(LOG_TAG,"Start Load");
       // VKRequest request=VKApi.groups().get(VKParameters.from("count",2,"extended",1,"fields"));
        /*VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "first_name,last_name,photo_50,photo_200"));
        request.start();
        //request.unregisterObject();
        request.setRequestListener(mRequestListener);*/

        //request.cancel();
        return rootView;
    }

    protected void setResponseText(String text) {
           // textView.setText(text);
    }

    VKRequest.VKRequestListener mRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            setResponseText(response.json.toString());

            String string = new String();
            try {
                last_name.setText(response.json.getJSONArray("response").getJSONObject(0).getString("first_name").toString() +
                        " " +response.json.getJSONArray("response").getJSONObject(0).getString("last_name").toString());
                //last_name.setText(response.json.getJSONArray("response").getJSONObject(0).getString("last_name").toString());
                Picasso.with(context).load(response.json.getJSONArray("response").getJSONObject(0).getString("photo_200").toString()).into(imageView);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*Picasso.with(context) //передаем контекст приложения
                    .load( "http://i.imgur.com/DvpvklR.png") //адрес изображения
                    .into(imageView); //ссылка на ImageView*/
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onError(VKError error) {
            setResponseText(error.toString());
        }

        @Override
        public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded,
                               long bytesTotal) {
            // you can show progress of the request if you want

        }

        @Override
        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            textView.append(String.format("Attempt %d/%d failed\n", attemptNumber, totalAttempts));
        }
    };
}