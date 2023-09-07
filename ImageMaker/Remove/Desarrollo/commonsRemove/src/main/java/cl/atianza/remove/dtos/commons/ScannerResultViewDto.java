package cl.atianza.remove.dtos.commons;

import cl.atianza.remove.dtos.AbstractDto;

public class ScannerResultViewDto extends AbstractDto {
	private String uuid;
	private String content;
	
	public ScannerResultViewDto() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ScannerResultViewDto [uuid=" + uuid + ", content=" + content + "]";
	}
}
