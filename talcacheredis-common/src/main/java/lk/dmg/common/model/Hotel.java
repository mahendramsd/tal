package lk.dmg.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RedisHash("Hotel")
public class Hotel implements Serializable {
    @Id
    private String subHotelCode;
    private String mainHotelCode;
    private String date;
    private String ratePlanCode;
    private String ratePlanDesc;
    private String resortCode;
    private String countryCode;
    private String priorityIndicator;
    private List<Room> rooms = new ArrayList<>();

    public String getSubHotelCode() {
        return subHotelCode;
    }

    public void setSubHotelCode(String subHotelCode) {
        this.subHotelCode = subHotelCode;
    }

    public String getMainHotelCode() {
        return mainHotelCode;
    }

    public void setMainHotelCode(String mainHotelCode) {
        this.mainHotelCode = mainHotelCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    public String getRatePlanDesc() {
        return ratePlanDesc;
    }

    public void setRatePlanDesc(String ratePlanDesc) {
        this.ratePlanDesc = ratePlanDesc;
    }

    public String getResortCode() {
        return resortCode;
    }

    public void setResortCode(String resortCode) {
        this.resortCode = resortCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPriorityIndicator() {
        return priorityIndicator;
    }

    public void setPriorityIndicator(String priorityIndicator) {
        this.priorityIndicator = priorityIndicator;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "subHotelCode='" + subHotelCode + '\'' +
                ", mainHotelCode='" + mainHotelCode + '\'' +
                ", date=" + date +
                ", ratePlanCode='" + ratePlanCode + '\'' +
                ", ratePlanDesc='" + ratePlanDesc + '\'' +
                ", resortCode='" + resortCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", priorityIndicator='" + priorityIndicator + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
