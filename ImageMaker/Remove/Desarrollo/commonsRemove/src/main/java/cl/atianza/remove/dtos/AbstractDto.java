package cl.atianza.remove.dtos;

/**
 * Abstract class for DTOs use.
 * 
 * @author Jose Gutierrez
 */
public abstract class AbstractDto {
	private Long id;

	public AbstractDto() {
		super();
	}

	public AbstractDto(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean esNuevo() {
		return this.getId() == null || this.getId().longValue() == 0;
	}
	
	public static boolean checkDto(AbstractDto dto) {
		return dto != null && dto.getId() != null && dto.getId() != 0;
	}

	@Override
	public String toString() {
		return "id=" + id + ", ";
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    AbstractDto that = (AbstractDto) o;
	    return id != null && that.getId() != null && id.longValue() == that.getId().longValue();
	}
}
