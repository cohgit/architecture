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
 * Enum with possible impulses types.
 * @author pilin
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = ImpulseTypeSerializer.class)
public enum EImpulseType {
	PUBLISHED_URL("PUBLISHED_URL"),
	OWN_WRITING("OWN_WRITING"),
	WORDING_REQUESTED("WORDING_REQUESTED");
	
	private String tag;
	
	private EImpulseType(String tag) {
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	@JsonCreator
    static EImpulseType findValue(@JsonProperty("tag") String tag) {
        return obtain(tag);
    }
	
	public static EImpulseType obtain(String tag) {
		for(EImpulseType e : EImpulseType.values()) {
			if (e.getTag().equalsIgnoreCase(tag)) return e;
		}
		
		return null;
	}
}

class ImpulseTypeSerializer extends StdSerializer<EImpulseType> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ImpulseTypeSerializer() {
		super(EImpulseType.class);
	}
	
	protected ImpulseTypeSerializer(Class<EImpulseType> t) {
		super(t);
	}
	
	@JsonCreator
    static EImpulseType findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EImpulseType obtain(String tag) {
		for(EImpulseType p : EImpulseType.values()) {
			if (p.getTag().equalsIgnoreCase(tag)) return p;
		}
		
		return null;
	}

	@Override
	public void serialize(EImpulseType value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("tag");
        generator.writeString(value.getTag());
        generator.writeEndObject();
	}
}