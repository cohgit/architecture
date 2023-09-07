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
@JsonSerialize(using = EPaymentPlatformsSerializer.class)
public enum EPaymentMethods {
	STRIPE("stripe"),
	TRANSFER("transfer");
	
	private String code;
	
	private EPaymentMethods(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@JsonCreator
    static EPaymentMethods findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EPaymentMethods obtain(String code) {
		for(EPaymentMethods p : EPaymentMethods.values()) {
			if (p.getCode().equalsIgnoreCase(code)) return p;
		}
		
		return null;
	}
}

class EPaymentPlatformsSerializer extends StdSerializer<EPaymentMethods> {
	private static final long serialVersionUID = -68634905355547356L;

	public EPaymentPlatformsSerializer() {
		super(EPaymentMethods.class);
	}
	
	protected EPaymentPlatformsSerializer(Class<EPaymentMethods> t) {
		super(t);
	}
	
	@JsonCreator
    static EFeelings findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EFeelings obtain(String tag) {
		for(EFeelings p : EFeelings.values()) {
			if (p.getTag().equalsIgnoreCase(tag)) return p;
		}
		
		return null;
	}

	@Override
	public void serialize(EPaymentMethods value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("code");
        generator.writeString(value.getCode());
        generator.writeEndObject();
	}
}