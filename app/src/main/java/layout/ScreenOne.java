package layout;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import ru.example.sapp.vkoffline.R;
public class ScreenOne extends Fragment {

    public ScreenOne() {
    }

    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_screen_one, container,
                false);
        textView = (TextView) rootView.findViewById(R.id.textViewScrOne);
        VKRequest request=VKApi.messages().get();
        request.start();
        //request.unregisterObject();
        request.setRequestListener(mRequestListener);

        //request.cancel();
        return rootView;
    }

    protected void setResponseText(String text) {
            textView.setText(text);
    }

    VKRequest.VKRequestListener mRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            setResponseText(response.json.toString());
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