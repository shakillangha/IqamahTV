package org.iqamah.iqamahtv;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.android.volley.RequestQueue;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getPrayerTimesButton = findViewById(R.id.get_prayer_times_button);
        getPrayerTimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText masjidIdEditText = findViewById(R.id.masjid_id_edit_text);
                String masjidId = masjidIdEditText.getText().toString();

                if (masjidId.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a masjid ID", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "https://iqamah.org/getMasjid/" + masjidId + ".json";
                    JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    displayPrayerTimes(masjidId, response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error retrieving prayer times", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsonObjectRequest);
                }
            }
        });
    }

    private void displayPrayerTimes(String masjidId, JSONArray response) {
        try {
            Gson gson = new Gson();
            PrayerTimes[] prayerTimesArray = gson.fromJson(response.toString(), PrayerTimes[].class);

            // Get the current date in the "M/d/yyyy" format
            DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
            Date currentDate = new Date();
            String currentDateString = dateFormat.format(currentDate);

            // Find the prayer times for today's date
            PrayerTimes prayerTimesToday = null;
            for (PrayerTimes prayerTimes : prayerTimesArray) {
                if (prayerTimes.getDate().equals(currentDateString)) {
                    prayerTimesToday = prayerTimes;
                    break;
                }
            }

            // Get the prayer times for today's date
            PrayerTime fajrTime = prayerTimesToday.getFajr();
            PrayerTime zuhrTime = prayerTimesToday.getZuhr();
            PrayerTime asrTime = prayerTimesToday.getAsr();
            PrayerTime maghribTime = prayerTimesToday.getMaghrib();
            PrayerTime ishaTime = prayerTimesToday.getIsha();

            // Create the table rows and text views for the prayer times
            TableLayout tableLayout = findViewById(R.id.prayer_times_table);
            TableRow fajrRow = new TableRow(this);
            TableRow zuhrRow = new TableRow(this);
            TableRow asrRow = new TableRow(this);
            TableRow maghribRow = new TableRow(this);
            TableRow ishaRow = new TableRow(this);

            TextView fajrTitle = new TextView(this);
            TextView fajrTimeText = new TextView(this);
            fajrTitle.setText("Fajr");
            fajrTimeText.setText(fajrTime.getAzaan() + " / " + fajrTime.getIqamah());
            fajrRow.addView(fajrTitle);
            fajrRow.addView(fajrTimeText);

            TextView zuhrTitle = new TextView(this);
            TextView zuhrTimeText = new TextView(this);
            zuhrTitle.setText("Zuhr");
            zuhrTimeText.setText(zuhrTime.getAzaan() + " / " + zuhrTime.getIqamah());
            zuhrRow.addView(zuhrTitle);
            zuhrRow.addView(zuhrTimeText);

            TextView asrTitle = new TextView(this);
            TextView asrTimeText = new TextView(this);
            asrTitle.setText("Asr");
            asrTimeText.setText(asrTime.getAzaan() + " / " + asrTime.getIqamah());
            asrRow.addView(asrTitle);
            asrRow.addView(asrTimeText);

            TextView maghribTitle = new TextView(this);
            TextView maghribTimeText = new TextView(this);
            maghribTitle.setText("Maghrib");
            maghribTimeText.setText(maghribTime.getAzaan() + " / " + maghribTime.getIqamah());
            maghribRow.addView(maghribTitle);
            maghribRow.addView(maghribTimeText);

            TextView ishaTitle = new TextView(this);
            TextView ishaTimeText = new TextView(this);
            ishaTitle.setText("Isha");
            ishaTimeText.setText(ishaTime.getAzaan() + " / " + ishaTime.getIqamah());
            ishaRow.addView(ishaTitle);
            ishaRow.addView(ishaTimeText);

            // Add the rows to the table layout
            if (currentDateString.equals(prayerTimesToday.getDate())) {
                tableLayout.addView(fajrRow);
                tableLayout.addView(zuhrRow);
                tableLayout.addView(asrRow);
                tableLayout.addView(maghribRow);
                tableLayout.addView(ishaRow);
            } else {
                Toast.makeText(getApplicationContext(), "No prayer times available for today", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error getting prayer times data", Toast.LENGTH_SHORT).show();
        }

    }
}

