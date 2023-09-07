package cl.atianza.remove.dtos;

/**
 * 
 * @author Jose Gutierrez
 */
public abstract class AbstractPersonDto extends AbstractDto {
	private String first_name;
	private String middle_name;
	private String first_lastname;
	private String second_lastname;
	
	public AbstractPersonDto() {
		super();
	}

	public AbstractPersonDto(Long id) {
		super(id);
	}

	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String second_name) {
		this.middle_name = second_name;
	}

	public String getFirst_lastname() {
		return first_lastname;
	}
	public void setFirst_lastname(String first_lastname) {
		this.first_lastname = first_lastname;
	}

	public String getSecond_lastname() {
		return second_lastname;
	}
	public void setSecond_lastname(String second_lastname) {
		this.second_lastname = second_lastname;
	}

	public String getFullName() {
		String name = "";
		
		if (this.getFirst_name() != null && !this.getFirst_name().trim().isEmpty()) {
			name += this.getFirst_name() + " ";
		}
		if (this.getMiddle_name() != null && !this.getMiddle_name().trim().isEmpty()) {
			name += this.getMiddle_name() + " ";
		}
		if (this.getFirst_lastname() != null && !this.getFirst_lastname().trim().isEmpty()) {
			name += this.getFirst_lastname() + " ";
		}
		if (this.getSecond_lastname() != null && !this.getSecond_lastname().trim().isEmpty()) {
			name += this.getSecond_lastname() + " ";
		}
		
		return name;
	}
	public void setFullName(String fullName) { }
	
	public String getShortName() {
		String name = "";
		
		if (this.getFirst_name() != null && !this.getFirst_name().trim().isEmpty()) {
			name += this.getFirst_name() + " ";
		}
		if (this.getFirst_lastname() != null && !this.getFirst_lastname().trim().isEmpty()) {
			name += this.getFirst_lastname() + " ";
		}
		
		return name;
	}
	public void setShortName(String fullName) { }

	@Override
	public String toString() {
		return "PersonDto [" + super.toString() + ", first_name=" + first_name + ", middle_name=" + middle_name + ", first_lastname="
				+ first_lastname + ", second_lastname=" + second_lastname + "]";
	}
}
