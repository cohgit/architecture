package cl.atianza.remove.models;

import java.time.LocalDateTime;

public class CacheDB {
	private LocalDateTime created;
	private String data;
	
	public CacheDB() {
		super();
	}
	public CacheDB(LocalDateTime created, String data) {
		super();
		this.created = created;
		this.data = data;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "CacheDB [created=" + created + ", data=" + data + "]";
	}
}
