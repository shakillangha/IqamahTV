package org.iqamah.iqamahtv;

public class PrayerTimes {
    private String d_date;
    private String fajr_begins;
    private String fajr_jamah;
    private String sunrise;
    private String zuhr_begins;
    private String zuhr_jamah;
    private String asr_mithl_1;
    private String asr_mithl_2;
    private String asr_jamah;
    private String maghrib_begins;
    private String maghrib_jamah;
    private String isha_begins;
    private String isha_jamah;
    private Integer is_ramadan;
    private Integer hijri_date;
    private String jumuah_jamah;

    public PrayerTime getFajr() {
        PrayerTime pt = new PrayerTime();
        pt.setAzaan(fajr_begins);
        pt.setIqamah(fajr_jamah);
        return pt;
    }

    public void setFajr(PrayerTime fajr) {
        this.fajr_begins = fajr.getAzaan();
        this.fajr_jamah = fajr.getIqamah();
    }
    public PrayerTime getZuhr() {
        PrayerTime pt = new PrayerTime();
        pt.setAzaan(zuhr_begins);
        pt.setIqamah(zuhr_jamah);
        return pt;
    }

    public PrayerTime getAsr() {
        PrayerTime pt = new PrayerTime();
        pt.setAzaan(asr_mithl_1);
        pt.setIqamah(asr_jamah);
        return pt;
    }

    public PrayerTime getMaghrib() {
        PrayerTime pt = new PrayerTime();
        pt.setAzaan(maghrib_begins);
        pt.setIqamah(maghrib_jamah);
        return pt;
    }

    public PrayerTime getIsha() {
        PrayerTime pt = new PrayerTime();
        pt.setAzaan(isha_begins);
        pt.setIqamah(isha_jamah);
        return pt;
    }

    public String getDate() {
        return d_date;
    }

}

