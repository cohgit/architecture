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
 * Enum with devices types for scanner.
 * 
 * @author Jose Gutierrez
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = ImpulseInteractionCategorySerializer.class)
public enum EImpulseInteractionCategory {
	SOCIAL("social", "social"),
	GOOGLE("google", "google"),
	DIRECT("direct", "direct"),
	MEDIA("media", "media"),
	OTHER("other", "other"),
	BLOG("blog", "blog"),
	LINK("link", "link")
	;
	
	private String code;
	private String tag;

	private EImpulseInteractionCategory(String code, String tag) {
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
    static EImpulseInteractionCategory findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EImpulseInteractionCategory obtain(String code) {
		for(EImpulseInteractionCategory d : EImpulseInteractionCategory.values()) {
			if (d.getCode().equalsIgnoreCase(code)) return d;
		}
		
		return null;
	}
}

class ImpulseInteractionCategorySerializer extends StdSerializer<EImpulseInteractionCategory> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ImpulseInteractionCategorySerializer() {
		super(EImpulseInteractionCategory.class);
	}
	
	protected ImpulseInteractionCategorySerializer(Class<EImpulseInteractionCategory> t) {
		super(t);
	}

	@Override
	public void serialize(EImpulseInteractionCategory value, JsonGenerator generator, SerializerProvider provider) throws IOException {
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