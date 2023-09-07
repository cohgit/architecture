package cl.atianza.remove.dtos.commons;

import java.time.LocalDateTime;

public class SchemaExecutedDto {
	private Long id;
	private String name;
	private String schema_executed;
	private boolean success;
	private LocalDateTime date;
	private String message;
	
	public SchemaExecutedDto() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSchema_executed() {
		return schema_executed;
	}
	public void setSchema_executed(String schema_executed) {
		this.schema_executed = schema_executed;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SchemaExecutedDto [id=" + id + ", name=" + name + ", success=" + success + ", date=" + date
				+ ", message=" + message + "]";
	}
}
