package lk.dmg.common.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RedisHash("AgentFreeNightByDayVO")
public class AgentFreeNightByDayVO implements Serializable {
    @Id
    private String agentFreeNightByDayKey;//productCode+AgentCode+day
    private String productCode;
    private String freeNightDate;
    private String freeNightAgent;
    private String freeNightFromDate;//optional
    private String freeNightToDate;//optional
    private List<String> freeNightEnableHotels = new ArrayList<>();

    public String getAgentFreeNightByDayKey() {
        return agentFreeNightByDayKey;
    }

    public void setAgentFreeNightByDayKey(String agentFreeNightByDayKey) {
        this.agentFreeNightByDayKey = agentFreeNightByDayKey;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getFreeNightDate() {
        return freeNightDate;
    }

    public void setFreeNightDate(String freeNightDate) {
        this.freeNightDate = freeNightDate;
    }

    public String getFreeNightAgent() {
        return freeNightAgent;
    }

    public void setFreeNightAgent(String freeNightAgent) {
        this.freeNightAgent = freeNightAgent;
    }

    public String getFreeNightFromDate() {
        return freeNightFromDate;
    }

    public void setFreeNightFromDate(String freeNightFromDate) {
        this.freeNightFromDate = freeNightFromDate;
    }

    public String getFreeNightToDate() {
        return freeNightToDate;
    }

    public void setFreeNightToDate(String freeNightToDate) {
        this.freeNightToDate = freeNightToDate;
    }

    public List<String> getFreeNightEnableHotels() {
        return freeNightEnableHotels;
    }

    public void setFreeNightEnableHotels(List<String> freeNightEnableHotels) {
        this.freeNightEnableHotels = freeNightEnableHotels;
    }

    @Override
    public String toString() {
        return "AgentFreeNightByDayVO{" +
                "agentFreeNightByDayKey='" + agentFreeNightByDayKey + '\'' +
                ", productCode='" + productCode + '\'' +
                ", freeNightDate='" + freeNightDate + '\'' +
                ", freeNightAgent='" + freeNightAgent + '\'' +
                ", freeNightFromDate='" + freeNightFromDate + '\'' +
                ", freeNightToDate='" + freeNightToDate + '\'' +
                ", freeNightEnableHotels=" + freeNightEnableHotels +
                '}';
    }
}
