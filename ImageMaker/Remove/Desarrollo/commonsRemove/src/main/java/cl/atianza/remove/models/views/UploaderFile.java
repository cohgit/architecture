package cl.atianza.remove.models.views;

public class UploaderFile {
	private Long id;
	private Long idDeindexation;
	private String extension;
	private Long lastModified;
	private String lastModifiedDate;
	private String name;
	private String link;
	private String description;
	private Long size;
	private String type;
	private boolean markForDelete;
	
	public UploaderFile() {
		super();
	}
	
	public Long getIdDeindexation() {
		return idDeindexation;
	}

	public void setIdDeindexation(Long idDeindexation) {
		this.idDeindexation = idDeindexation;
	}

	public Long getLastModified() {
		return lastModified;
	}
	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "UploaderFile [id=" + id + ", idDeindexation=" + idDeindexation + ", lastModified=" + lastModified
				+ ", lastModifiedDate=" + lastModifiedDate + ", name=" + name + ", link=" + link + ", description="
				+ description + ", size=" + size + ", type=" + type + ", markForDelete=" + markForDelete + "]";
	}
}
