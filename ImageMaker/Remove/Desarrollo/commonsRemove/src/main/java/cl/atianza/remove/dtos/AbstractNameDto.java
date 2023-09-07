package cl.atianza.remove.dtos;

/**
 * 
 * @author pilin
 *
 */
public abstract class AbstractNameDto extends AbstractDto {
	private String name;

	public AbstractNameDto() {
		super();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return super.toString() + " name=" + name + ", ";
	}
}
