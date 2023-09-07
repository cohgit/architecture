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
 * Enum with types for image scanner parameter.
 * 
 * @author Jose Gutierrez
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = ImageTypeSerializer.class)
public enum EImageType {
	CLIPART("clipart", "clipart"),
	LINE_DRAWING("line_drawing", "line_drawing"),
	GIF("gif", "gif")
	;
	
	private String code;
	private String tag;

	private EImageType(String code, String tag) {
		this.code = code;
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@JsonCreator
    static EImageType findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EImageType obtain(String code) {
		for(EImageType e : EImageType.values()) {
			if (e.getCode().equalsIgnoreCase(code)) return e;
		}
		
		return null;
	}
}

class ImageTypeSerializer extends StdSerializer<EImageType> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ImageTypeSerializer() {
		super(EImageType.class);
	}
	
	protected ImageTypeSerializer(Class<EImageType> t) {
		super(t);
	}

	@Override
	public void serialize(EImageType value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("code");
        generator.writeString(value.getCode());
        generator.writeFieldName("tag");
        generator.writeString(value.getTag());
        generator.writeEndObject();
	}
}