package org.iqamah.iqamahtv;
import java.util.Date;
public class PrayerTimeItem {
    private String prayerName;
    private Date startTime;
    private Date endTime;

    public PrayerTimeItem(String prayerName, Date startTime, Date endTime) {
        this.prayerName = prayerName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getPrayerName() {
        return prayerName;
    }

    public void setPrayerName(String prayerName) {
        this.prayerName = prayerName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
