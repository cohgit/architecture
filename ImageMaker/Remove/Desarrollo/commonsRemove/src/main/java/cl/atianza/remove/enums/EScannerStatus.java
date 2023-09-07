
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
@JsonSerialize(using = ScannerStatusSerializer.class)
public enum EScannerStatus {
	CREATED("created"),						
	ACTIVE("active"),
	DEACTIVE("deactive"),
	EXECUTING("executing"),
	SUSPENDED("suspended"),
	FAILED("failed"),	
	PAUSED("paused"),					
	DELETED("deleted"),
	FIXED("fixed");						
	
	private String tag;
	private EScannerStatus(String tag) {
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@JsonCreator
    static EScannerStatus findValue(@JsonProperty("tag") String tag) {
        return obtain(tag);
    }
	
	public static EScannerStatus obtain(String tag) {
		for(EScannerStatus e : EScannerStatus.values()) {
			if (e.getTag().equalsIgnoreCase(tag)) return e;
		}
		
		return null;
	}
}

class ScannerStatusSerializer extends StdSerializer<EScannerStatus> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ScannerStatusSerializer() {
		super(EScannerStatus.class);
	}
	
	protected ScannerStatusSerializer(Class<EScannerStatus> t) {
		super(t);
	}
	
	@JsonCreator
    static EScannerStatus findValue(@JsonProperty("tag") String tag) {
        return obtain(tag);
    }
	
	public static EScannerStatus obtain(String tag) {
		for(EScannerStatus p : EScannerStatus.values()) {
			if (p.getTag().equalsIgnoreCase(tag)) return p;
		}
		
		return null;
	}

	@Override
	public void serialize(EScannerStatus value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("tag");
        generator.writeString(value.getTag());
        generator.writeEndObject();
	}
}