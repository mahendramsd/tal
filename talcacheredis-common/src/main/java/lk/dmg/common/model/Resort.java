package lk.dmg.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Resort")
public class Resort implements Serializable {
	@Id
	private String resortCode;
	private List<String> subHotels=new ArrayList<>();


	public String getResortCode() {
		return resortCode;
	}
	public void setResortCode(String resortCode) {
		this.resortCode = resortCode;
	}
	public List<String> getSubHotels() {
		return subHotels;
	}
	public void setSubHotels(List<String> subHotels) {
		this.subHotels = subHotels;
	}
	

}
