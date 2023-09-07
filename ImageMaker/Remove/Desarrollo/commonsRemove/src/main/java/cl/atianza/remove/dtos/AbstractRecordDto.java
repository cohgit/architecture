package cl.atianza.remove.dtos;

/**
 * 
 * @author pilin
 *
 */
public abstract class AbstractRecordDto extends AbstractDto{
	private String description;
	private boolean active;
	
	public AbstractRecordDto() {
		super();
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return super.toString() + " description=" + description + ", active=" + active + ",";
	}
}
