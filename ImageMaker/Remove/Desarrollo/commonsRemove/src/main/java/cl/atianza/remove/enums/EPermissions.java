package cl.atianza.remove.enums;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * 
 * @author pilin
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = PermissionSerializer.class)
public enum EPermissions {
	SCANNER_ONE_SHOT("SCANNER_ONE_SHOT"),
	SCANNER_MONITOR("SCANNER_MONITOR"),
	SCANNER_TRANSFORM("SCANNER_TRANSFORM"),
	SCANNER_IMPULSE("IMPULSE");
	
	private String tag;
	private EPermissions(String tag) {
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}

class PermissionSerializer extends StdSerializer<EPermissions> {
	private static final long serialVersionUID = -8761216512740416212L;

	public PermissionSerializer() {
		super(EPermissions.class);
	}
	
	protected PermissionSerializer(Class<EPermissions> t) {
		super(t);
	}
	
	@JsonCreator
    static EPermissions findValue(@JsonProperty("tag") String tag) {
        return obtain(tag);
    }
	
	public static EPermissions obtain(String tag) {
		for(EPermissions p : EPermissions.values()) {
			if (p.getTag().equalsIgnoreCase(tag)) return p;
		}
		
		return null;
	}

	@Override
	public void serialize(EPermissions value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("tag");
        generator.writeString(value.getTag());
        generator.writeEndObject();
	}
}