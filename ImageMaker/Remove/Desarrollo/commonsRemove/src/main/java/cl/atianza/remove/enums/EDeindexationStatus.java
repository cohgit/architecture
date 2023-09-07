
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
@Deprecated
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = DeindexationStatusSerializer.class)
public enum EDeindexationStatus {
	CREATED("created"),						
	SENT("sent"),							
	PROCESSING("processing"),				
	APPROVED("approved"),					
	REJECTED("rejected"),					
	DELETED("deleted");						
	
	private String tag;
	private EDeindexationStatus(String tag) {
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@JsonCreator
    static EDeindexationStatus findValue(@JsonProperty("tag") String tag) {
        return obtain(tag);
    }
	
	public static EDeindexationStatus obtain(String tag) {
		for(EDeindexationStatus e : EDeindexationStatus.values()) {
			if (e.getTag().equalsIgnoreCase(tag)) return e;
		}
		
		return null;
	}
}

class DeindexationStatusSerializer extends StdSerializer<EDeindexationStatus> {
	private static final long serialVersionUID = -8761216512740416212L;

	public DeindexationStatusSerializer() {
		super(EDeindexationStatus.class);
	}
	
	protected DeindexationStatusSerializer(Class<EDeindexationStatus> t) {
		super(t);
	}
	
	@JsonCreator
    static EDeindexationStatus findValue(@JsonProperty("tag") String tag) {
        return obtain(tag);
    }
	
	public static EDeindexationStatus obtain(String tag) {
		for(EDeindexationStatus p : EDeindexationStatus.values()) {
			if (p.getTag().equalsIgnoreCase(tag)) return p;
		}
		
		return null;
	}

	@Override
	public void serialize(EDeindexationStatus value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("tag");
        generator.writeString(value.getTag());
        generator.writeEndObject();
	}
}