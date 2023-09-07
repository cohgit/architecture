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
 * Enum with types of news for scanner configuration.
 * 
 * @author Jose Gutierrez
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = NewsTypeSerializer.class)
public enum ENewsTypes {
	ALL("all", "news_all", "All News"),
	BLOGS("blogs", "blogs_only", "Blogs Only")
	;
	
	private String code;
	private String tag;
	private String description;

	private ENewsTypes(String code, String tag, String description) {
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
    static ENewsTypes findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static ENewsTypes obtain(String code) {
		for(ENewsTypes d : ENewsTypes.values()) {
			if (d.getCode().equalsIgnoreCase(code)) return d;
		}
		
		return null;
	}
}

class NewsTypeSerializer extends StdSerializer<ENewsTypes> {
	private static final long serialVersionUID = -8761216512740416212L;

	public NewsTypeSerializer() {
		super(ENewsTypes.class);
	}
	
	protected NewsTypeSerializer(Class<ENewsTypes> t) {
		super(t);
	}

	@Override
	public void serialize(ENewsTypes value, JsonGenerator generator, SerializerProvider provider) throws IOException {
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