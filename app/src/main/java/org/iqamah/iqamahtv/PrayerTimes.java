package org.iqamah.iqamahtv;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PrayerTimes {
    private Date date;
    private ArrayList<PrayerTimeItem> prayerTimes;
    private boolean isRamadan;
    private int hijriDate;

    public PrayerTimes(JSONObject json) throws JSONException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);

        try {
            this.date = dateFormat.parse(json.optString("d_date"));
        } catch (ParseException e) {
            this.date = null;
        }
        this.prayerTimes = new ArrayList<>();

        PrayerTimeItem fajr = new PrayerTimeItem(
                "Fajr",
                getTime(json.optString("fajr_begins")),
                getTime(json.optString("fajr_jamah"))
        );
        this.prayerTimes.add(fajr);

        PrayerTimeItem sunrise = new PrayerTimeItem(
                "Sunrise",
                getTime(json.optString("sunrise")),
                null
        );
        this.prayerTimes.add(sunrise);

        PrayerTimeItem zuhr = new PrayerTimeItem(
                "Zuhr",
                getTime(json.optString("zuhr_begins")),
                getTime(json.optString("zuhr_jamah"))
        );
        this.prayerTimes.add(zuhr);

        PrayerTimeItem asr = new PrayerTimeItem(
                "Asr",
                getTime(json.optString("asr_mithl_1")),
                getTime(json.optString("asr_jamah"))
        );
        this.prayerTimes.add(asr);

        PrayerTimeItem maghrib = new PrayerTimeItem(
                "Maghrib",
                getTime(json.optString("maghrib_begins")),
                getTime(json.optString("maghrib_jamah"))
        );
        this.prayerTimes.add(maghrib);

        PrayerTimeItem isha = new PrayerTimeItem(
                "Isha",
                getTime(json.optString("isha_begins")),
                getTime(json.optString("isha_jamah"))
        );
        this.prayerTimes.add(isha);

        PrayerTimeItem jumua = new PrayerTimeItem(
                "Jumua",
                getTime(json.optString("jumua_begins")),
                getTime(json.optString("jumua_jamah"))
        );
        this.prayerTimes.add(jumua);

        this.isRamadan = json.optInt("is_ramadan", 0) == 1;
        this.hijriDate = json.optInt("hijri_date", 0);
    }

    private Date getTime(String time) {
        if (time == null || time.isEmpty()) {
            return null;
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
        try {
            return timeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<PrayerTimeItem> getPrayerTimes() {
        return prayerTimes;
    }

    public boolean isRamadan() {
        return isRamadan;
    }

    public int getHijriDate() {
        return hijriDate;
    }

}

