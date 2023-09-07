package cl.atianza.remove.enums;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = DevicesSerializer.class)
public enum EObjectPageOfAlert {
	MONITOR("monitor", "monitor", "Monitor"),
	TARGET("target", "target", "Target")
	;
	
	private String code;
	private String tag;
	private String description;
	
	private EObjectPageOfAlert(String code, String tag, String description) {
		this.code = code;
		this.tag = tag;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	
}

class ObjectPageOfAlertSerializer extends StdSerializer<EObjectPageOfAlert> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ObjectPageOfAlertSerializer() {
		super(EObjectPageOfAlert.class);
	}
	
	protected ObjectPageOfAlertSerializer(Class<EObjectPageOfAlert> t) {
		super(t);
	}

	@Override
	public void serialize(EObjectPageOfAlert value, JsonGenerator generator, SerializerProvider provider) throws IOException {
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
