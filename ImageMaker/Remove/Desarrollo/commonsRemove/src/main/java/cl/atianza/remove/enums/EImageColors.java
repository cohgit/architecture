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
 * Enum with colors for image scanner parameter.
 * 
 * @author Jose Gutierrez
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = ImageColorSerializer.class)
public enum EImageColors {
	RED("red", "red"),
	ORANGE("orange", "orange"),
	YELLOW("yellow", "yellow"),
	GREEN("green", "green"),
	TEAL("teal", "teal"),
	BLUE("blue", "blue"),
	PURPLE("purple", "purple"),
	PINK("pink", "pink"),
	WHITE("white", "white"),
	GRAY("gray", "gray"),
	BLACK("black", "black"),
	BROWN("brown", "brown")
	;
	
	private String code;
	private String tag;

	private EImageColors(String code, String tag) {
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
    static EImageColors findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EImageColors obtain(String code) {
		for(EImageColors e : EImageColors.values()) {
			if (e.getCode().equalsIgnoreCase(code)) return e;
		}
		
		return null;
	}
}

class ImageColorSerializer extends StdSerializer<EImageColors> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ImageColorSerializer() {
		super(EImageColors.class);
	}
	
	protected ImageColorSerializer(Class<EImageColors> t) {
		super(t);
	}

	@Override
	public void serialize(EImageColors value, JsonGenerator generator, SerializerProvider provider) throws IOException {
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