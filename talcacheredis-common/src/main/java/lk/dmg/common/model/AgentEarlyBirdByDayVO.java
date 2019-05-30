package lk.dmg.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RedisHash("AgentEarlyBirdByDayVO")
public class AgentEarlyBirdByDayVO implements Serializable {
    @Id
    private String agentEbdByDayKey; //productCode+AgentCode+day
    private String productCode;
    private String edbDate;
    private String ebdAgent;
    private String ebdFromDate;//optional
    private String ebdToDate;//optional
    private List<String> ebdEnableHotels = new ArrayList<>();

    public String getAgentEbdByDayKey() {
        return agentEbdByDayKey;
    }

    public void setAgentEbdByDayKey(String agentEbdByDayKey) {
        this.agentEbdByDayKey = agentEbdByDayKey;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getEdbDate() {
        return edbDate;
    }

    public void setEdbDate(String edbDate) {
        this.edbDate = edbDate;
    }

    public String getEbdAgent() {
        return ebdAgent;
    }

    public void setEbdAgent(String ebdAgent) {
        this.ebdAgent = ebdAgent;
    }

    public String getEbdFromDate() {
        return ebdFromDate;
    }

    public void setEbdFromDate(String ebdFromDate) {
        this.ebdFromDate = ebdFromDate;
    }

    public String getEbdToDate() {
        return ebdToDate;
    }

    public void setEbdToDate(String ebdToDate) {
        this.ebdToDate = ebdToDate;
    }

    public List<String> getEbdEnableHotels() {
        return ebdEnableHotels;
    }

    public void setEbdEnableHotels(List<String> ebdEnableHotels) {
        this.ebdEnableHotels = ebdEnableHotels;
    }

    @Override
    public String toString() {
        return "AgentEarlyBirdByDayVO{" +
                "productCode='" + productCode + '\'' +
                ", edbDate='" + edbDate + '\'' +
                ", ebdAgent='" + ebdAgent + '\'' +
                ", ebdFromDate='" + ebdFromDate + '\'' +
                ", ebdToDate='" + ebdToDate + '\'' +
                ", ebdEnableHotels=" + ebdEnableHotels +
                '}';
    }
}
