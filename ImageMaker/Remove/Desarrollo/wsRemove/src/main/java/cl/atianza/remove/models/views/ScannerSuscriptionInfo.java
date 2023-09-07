package cl.atianza.remove.models.views;

import java.time.LocalDateTime;

public class ScannerSuscriptionInfo {
	private String type;
	private Long id;
	private String uuid;
	private String name;
	private String keywords;
	private LocalDateTime creationDate;
	private boolean checked;
	
	public ScannerSuscriptionInfo() {
		super();
	}
	public ScannerSuscriptionInfo(String type, Long id, String uuid, String name, String keywords,
			LocalDateTime creationDate, boolean checked) {
		super();
		this.type = type;
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.keywords = keywords;
		this.creationDate = creationDate;
		this.checked = checked;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	@Override
	public String toString() {
		return "ScannerSuscriptionInfo [type=" + type + ", id=" + id + ", uuid=" + uuid + ", name=" + name
				+ ", keywords=" + keywords + ", creationDate=" + creationDate + ", checked=" + checked + "]";
	}
}
