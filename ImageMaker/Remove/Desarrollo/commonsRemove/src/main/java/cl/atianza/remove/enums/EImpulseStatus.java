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
@JsonSerialize(using = ImpulseStatusSerializer.class)
public enum EImpulseStatus {
	DRAFT("draft"),
	AWAITING_DRAFTING("awaiting_drafting"),
	TO_BE_APPROVED("to_be_approved"),
	APPROVED("approved"),
	REJECTED("rejected"),
	PUBLISHED("published");
	
	private String tag;
	private EImpulseStatus(String tag) {
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@JsonCreator
    static EImpulseStatus findValue(@JsonProperty("tag") String tag) {
        return obtain(tag);
    }
	
	public static EImpulseStatus obtain(String tag) {
		for(EImpulseStatus e : EImpulseStatus.values()) {
			if (e.getTag().equalsIgnoreCase(tag)) return e;
		}
		
		return null;
	}
}

class ImpulseStatusSerializer extends StdSerializer<EImpulseStatus> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ImpulseStatusSerializer() {
		super(EImpulseStatus.class);
	}
	
	protected ImpulseStatusSerializer(Class<EImpulseStatus> t) {
		super(t);
	}
	
	@JsonCreator
    static EImpulseStatus findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EImpulseStatus obtain(String tag) {
		for(EImpulseStatus p : EImpulseStatus.values()) {
			if (p.getTag().equalsIgnoreCase(tag)) return p;
		}
		
		return null;
	}

	@Override
	public void serialize(EImpulseStatus value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("tag");
        generator.writeString(value.getTag());
        generator.writeEndObject();
	}
}