package seanpetersgichia.covid19_inforapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import seanpetersgichia.covid19_inforapp.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView totalConfirmed,totalRecovered,totalDeath,lastUpdated;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        totalConfirmed = root.findViewById(R.id.globalTotalConfirmed);
        totalDeath = root.findViewById(R.id.globalTotalDeaths);
        totalRecovered = root.findViewById(R.id.globalTotalRecoveries);
        lastUpdated = root.findViewById(R.id.LastUdated);
        progressBar = root.findViewById(R.id.progress_circular_home);

        getData();
        return root;
    }

    private String convertDateFormat(long milliSecond){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MM yyyy hhh:mmm:sss aaa");

        java.util.Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return simpleDateFormat.format(calendar.getTime());
    }

    public void getData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://corona.lmao.ninja/v2/all";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    totalConfirmed.setText(jsonObject.getString("cases"));
                    totalDeath.setText(jsonObject.getString("deaths"));
                    totalRecovered.setText(jsonObject.getString("recovered"));
                    lastUpdated.setText("Last Updated :\n" + convertDateFormat(jsonObject.getLong("updated")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error Response",error.toString());
            }

    });
        queue.add(stringRequest);

    }
}
