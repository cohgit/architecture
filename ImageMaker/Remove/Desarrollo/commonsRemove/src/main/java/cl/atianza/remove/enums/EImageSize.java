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
 * Enum with sizes for image scanner parameter.
 * 
 * @author Jose Gutierrez
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = ImageSizeSerializer.class)
public enum EImageSize {
	LARGE("large", "large"),
	MEDIUM("medium", "medium"),
	ICON("icon", "icon")
	;
	
	private String code;
	private String tag;

	private EImageSize(String code, String tag) {
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
    static EImageSize findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EImageSize obtain(String code) {
		for(EImageSize e : EImageSize.values()) {
			if (e.getCode().equalsIgnoreCase(code)) return e;
		}
		
		return null;
	}
}

class ImageSizeSerializer extends StdSerializer<EImageSize> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ImageSizeSerializer() {
		super(EImageSize.class);
	}
	
	protected ImageSizeSerializer(Class<EImageSize> t) {
		super(t);
	}

	@Override
	public void serialize(EImageSize value, JsonGenerator generator, SerializerProvider provider) throws IOException {
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