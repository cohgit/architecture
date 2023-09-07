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
 * Enum with scanner types.
 * 
 * @author Jose Gutierrez
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = ScannerTypeSerializer.class)
public enum EScannerTypes {
	ONE_SHOT("one_shot", "scanner.one.shot", "Scanner One Shot"),
	TRANSFORM("transform", "scanner.transform", "Transform"),
	MONITOR("monitor", "scanner.monitor", "Monitor")
	;
	
	private String code;
	private String tag;
	private String description;

	private EScannerTypes(String code, String tag, String description) {
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
    static EScannerTypes findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EScannerTypes obtain(String code) {
		for(EScannerTypes d : EScannerTypes.values()) {
			if (d.getCode().equalsIgnoreCase(code)) return d;
		}
		
		return null;
	}
	public static boolean valid(String scannerType) {
		return EScannerTypes.findValue(scannerType) != null;
	}
}

class ScannerTypeSerializer extends StdSerializer<EScannerTypes> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ScannerTypeSerializer() {
		super(EScannerTypes.class);
	}
	
	protected ScannerTypeSerializer(Class<EScannerTypes> t) {
		super(t);
	}

	@Override
	public void serialize(EScannerTypes value, JsonGenerator generator, SerializerProvider provider) throws IOException {
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