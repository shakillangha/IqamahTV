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

import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {

    private EditText masjidIdEditText;
    private Button getPrayerTimesButton;
    private TableLayout prayerTimesTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        masjidIdEditText = findViewById(R.id.masjidIdEditText);
        masjidIdEditText.setText("ChIJm53YZjj9zUwRzCeuSonVU1Q"); // set default value

        getPrayerTimesButton = findViewById(R.id.getPrayerTimesButton);
        prayerTimesTable = findViewById(R.id.prayerTimesTable);

        getPrayerTimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masjidId = masjidIdEditText.getText().toString();
                String url = "https://iqamah.org/getMasjid/" + masjidId + ".json";

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

                                    List<PrayerTimes> prayerTimesList = new ArrayList<>();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject prayerTimeJson = response.getJSONObject(i);
                                        PrayerTimes prayerTime = new PrayerTimes(prayerTimeJson);
                                        prayerTimesList.add(prayerTime);
                                    }
                                    PrayerTimes[] prayerTimes = prayerTimesList.toArray(new PrayerTimes[0]);

                                    // find prayer times for today's date
                                    Date currentDate = new Date();
                                    PrayerTimes prayerTimeForToday = null;
                                    for (PrayerTimes prayerTime : prayerTimes) {
                                        if (dateFormat.format(prayerTime.getDate()).equals(dateFormat.format(currentDate))) {
                                            prayerTimeForToday = prayerTime;
                                            break;
                                        }
                                    }

                                    if (prayerTimeForToday != null) {
                                        displayPrayerTimes(prayerTimeForToday);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "No prayer times available for today", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error fetching prayer times", Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(jsonArrayRequest);
            }
        });
    }


    private void displayPrayerTimes(PrayerTimes prayerTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US);

        TextView dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText(dateFormat.format(prayerTime.getDate()));

        ArrayList<PrayerTimeItem> prayerTimes = prayerTime.getPrayerTimes();
        for (PrayerTimeItem prayerTimeItem : prayerTimes) {
            String prayerName = prayerTimeItem.getPrayerName();
            Date startTime = prayerTimeItem.getStartTime();
            Date endTime = prayerTimeItem.getEndTime();

            String startTimeString = "";
            String endTimeString = "";
            if (startTime != null) {
                startTimeString = timeFormat.format(startTime);
            }

            if (endTime != null) {
                endTimeString = timeFormat.format(endTime);
            }
            switch (prayerName) {
                case "Fajr":
                    displayPrayerTimesRow("Fajr", startTimeString, endTimeString);
                    break;
                case "Sunrise":
                    displayPrayerTimesRow("Sunrise", startTimeString, endTimeString);
                    break;
                case "Zuhr":
                    displayPrayerTimesRow("Zuhr", startTimeString, endTimeString);
                    break;
                case "Asr":
                    displayPrayerTimesRow("Asr", startTimeString, endTimeString);
                    break;
                case "Maghrib":
                    displayPrayerTimesRow("Maghrib", startTimeString, endTimeString);
                    break;
                case "Isha":
                    displayPrayerTimesRow("Isha", startTimeString, endTimeString);
                    break;
                case "Jumua":
                    displayPrayerTimesRow("Jumua", startTimeString, endTimeString);
                    break;
            }
        }
    }

    private void displayPrayerTimesRow(String prayerName, String azaanTime, String iqamahTime) {
        TableRow row = new TableRow(this);

        TextView prayerNameTextView = new TextView(this);
        prayerNameTextView.setText(prayerName);
        prayerNameTextView.setPadding(16, 16, 16, 16);
        row.addView(prayerNameTextView);

        TextView azaanTextView = new TextView(this);
        azaanTextView.setText(azaanTime);
        azaanTextView.setPadding(16, 16, 16, 16);
        row.addView(azaanTextView);

        TextView iqamahTextView = new TextView(this);
        iqamahTextView.setText(iqamahTime);
        iqamahTextView.setPadding(16, 16, 16, 16);
        row.addView(iqamahTextView);

        prayerTimesTable.addView(row);
    }

}

