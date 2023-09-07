package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;

import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class DeindexationFileEvidenceDto extends AbstractDto {
	private Long id_deindexation;
	private Long id_uploader;
	private String uploader_profile;
	
	private String file_address;
	private String file_description;
	private String file_type;
	private LocalDateTime creation_date;
	
	public DeindexationFileEvidenceDto() {
		super();
	}

	public Long getId_deindexation() {
		return id_deindexation;
	}

	public void setId_deindexation(Long id_deindexation) {
		this.id_deindexation = id_deindexation;
	}

	public Long getId_uploader() {
		return id_uploader;
	}

	public void setId_uploader(Long id_uploader) {
		this.id_uploader = id_uploader;
	}

	public String getUploader_profile() {
		return uploader_profile;
	}

	public void setUploader_profile(String uploader_profile) {
		this.uploader_profile = uploader_profile;
	}

	public String getFile_address() {
		return file_address;
	}

	public void setFile_address(String file_address) {
		this.file_address = file_address;
	}

	public String getFile_description() {
		return file_description;
	}

	public void setFile_description(String file_description) {
		this.file_description = file_description;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	@Override
	public String toString() {
		return "DeindexationFileEvidenceDto [id_deindexation=" + id_deindexation + ", file_address=" + file_address
				+ ", file_description=" + file_description + ", file_type="
				+ file_type + ", creation_date=" + creation_date + "]";
	}
}
