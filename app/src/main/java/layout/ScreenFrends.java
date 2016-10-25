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
public class ScreenFrends extends Fragment {

    public ScreenFrends() {
    }

    Context context;
    String LOG_TAG = "ScreenFrends";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_screen_frends, container,
                false);
        context = rootView.getContext();

        MyPageRequest myPageRequest = new MyPageRequest();
        myPageRequest.loadData("frends");
        Log.e(LOG_TAG,"Start Load Frends");

        return rootView;
    }

}