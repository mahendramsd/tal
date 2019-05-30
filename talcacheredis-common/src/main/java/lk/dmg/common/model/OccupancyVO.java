package lk.dmg.common.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("OccupancyVO")
public class OccupancyVO implements Serializable {
    private String occupancyCode;
    private List<String> roomCodeList;

    public String getOccupancyCode() {
        return occupancyCode;
    }

    public void setOccupancyCode(String occupancyCode) {
        this.occupancyCode = occupancyCode;
    }

    public List<String> getRoomCodeList() {
        return roomCodeList;
    }

    public void setRoomCodeList(List<String> roomCodeList) {
        this.roomCodeList = roomCodeList;
    }
}
