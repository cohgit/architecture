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
 * Enum with snippet and tracking word possible feelings.
 * @author pilin
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = FeelingsSerializer.class)
public enum EFeelings {
	GOOD("good", 2),
	BAD("bad", -4),
	NEUTRAL("neutral", 1),
	NOT_APPLIED("not_applied", 0);
	
	private String tag;
	private Integer score;
	
	private EFeelings(String tag, Integer score) {
		this.tag = tag;
		this.score = score;
	}

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	@JsonCreator
    static EFeelings findValue(@JsonProperty("tag") String tag) {
        return find(tag);
    }
	
	public static EFeelings find(String feeling) {
		for (EFeelings feel: EFeelings.values()) {
			if (feel.getTag().equalsIgnoreCase(feeling)) {
				return feel;
			}
		}
		
		return EFeelings.NOT_APPLIED;
	}
}

class FeelingsSerializer extends StdSerializer<EFeelings> {
	private static final long serialVersionUID = -8761216512740416212L;

	public FeelingsSerializer() {
		super(EFeelings.class);
	}
	
	protected FeelingsSerializer(Class<EFeelings> t) {
		super(t);
	}

	@Override
	public void serialize(EFeelings value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("tag");
        generator.writeString(value.getTag());
        generator.writeEndObject();
	}
}