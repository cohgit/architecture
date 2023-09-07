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
 * Enum with usage rights for image scanner parameter.
 * 
 * @author Jose Gutierrez
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = ImageUsageRightsSerializer.class)
public enum EImageUsageRights {
	REUSE_WITH_MODIFICATION("reuse_with_modification", "reuse_with_modification"),
	REUSE("reuse", "reuse"),
	NON_COMERCIAL_REUSE_WITH_MODIFICATION("non_commercial_reuse_with_modification", "non_commercial_reuse_with_modification"),
	NON_COMERCIAL_REUSE("non_commercial_reuse", "non_commercial_reuse")
	;
	
	private String code;
	private String tag;

	private EImageUsageRights(String code, String tag) {
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
    static EImageUsageRights findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EImageUsageRights obtain(String code) {
		for(EImageUsageRights e : EImageUsageRights.values()) {
			if (e.getCode().equalsIgnoreCase(code)) return e;
		}
		
		return null;
	}
}

class ImageUsageRightsSerializer extends StdSerializer<EImageUsageRights> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ImageUsageRightsSerializer() {
		super(EImageUsageRights.class);
	}
	
	protected ImageUsageRightsSerializer(Class<EImageUsageRights> t) {
		super(t);
	}

	@Override
	public void serialize(EImageUsageRights value, JsonGenerator generator, SerializerProvider provider) throws IOException {
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