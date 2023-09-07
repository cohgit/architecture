package cl.atianza.remove.enums;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Enum with system profiles.
 * 
 * @author Jose Gutierrez
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = ProfileSerializer.class)
public enum EProfiles {
	ADMIN("ADMIN", "profile_admin", "Administrator"),
	MANAGER("MANAGER", "profile_manager", "Manager"),
	EDITOR("EDITOR", "profile_editor", "Editor"),
	FACTURATION("FACTURATION", "profile_facturation", "Facturation"),
	FORMULE("FORMULE", "profile_formule", "Formule"),
	CLIENT("CLIENT", "profile_client", "Client")
	;
	
	private String code;
	private String tag;
	private String description;

	private EProfiles(String code, String tag, String description) {
		this.code = code;
		this.tag = tag;
		this.description = description;
	}

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@JsonCreator
    static EProfiles findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EProfiles obtain(String code) {
		for(EProfiles p : EProfiles.values()) {
			if (p.getCode().equalsIgnoreCase(code)) return p;
		}
		
		return null;
	}
	
	public static List<EProfiles> valuesWithoutClient() {
		return Arrays.asList(EProfiles.ADMIN, EProfiles.EDITOR, EProfiles.FACTURATION, EProfiles.FORMULE, EProfiles.MANAGER);
	}
}

class ProfileSerializer extends StdSerializer<EProfiles> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ProfileSerializer() {
		super(EProfiles.class);
	}
	
	protected ProfileSerializer(Class<EProfiles> t) {
		super(t);
	}

	@Override
	public void serialize(EProfiles value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("code");
        generator.writeString(value.getCode());
        generator.writeFieldName("tag");
        generator.writeString(value.getTag());
        generator.writeFieldName("description");
        generator.writeString(value.getDescription());
        generator.writeEndObject();
	}
}