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
 * Enum with possible status of impulses.
 * @author pilin
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = EDynamicFormTypesSerializer.class)
public enum EDynamicFormTypes {
	DEINDEXATION("deindexation");
	
	private String tag;
	private EDynamicFormTypes(String tag) {
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@JsonCreator
    static EDynamicFormTypes findValue(@JsonProperty("tag") String tag) {
        return obtain(tag);
    }
	
	public static EDynamicFormTypes obtain(String tag) {
		for(EDynamicFormTypes e : EDynamicFormTypes.values()) {
			if (e.getTag().equalsIgnoreCase(tag)) return e;
		}
		
		return null;
	}
}

class EDynamicFormTypesSerializer extends StdSerializer<EDynamicFormTypes> {
	private static final long serialVersionUID = -8761216512740416212L;

	public EDynamicFormTypesSerializer() {
		super(EDynamicFormTypes.class);
	}
	
	protected EDynamicFormTypesSerializer(Class<EDynamicFormTypes> t) {
		super(t);
	}
	
	@JsonCreator
    static EDynamicFormTypes findValue(@JsonProperty("tag") String tag) {
        return obtain(tag);
    }
	
	public static EDynamicFormTypes obtain(String tag) {
		for(EDynamicFormTypes p : EDynamicFormTypes.values()) {
			if (p.getTag().equalsIgnoreCase(tag)) return p;
		}
		
		return null;
	}

	@Override
	public void serialize(EDynamicFormTypes value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("tag");
        generator.writeString(value.getTag());
        generator.writeEndObject();
	}
}