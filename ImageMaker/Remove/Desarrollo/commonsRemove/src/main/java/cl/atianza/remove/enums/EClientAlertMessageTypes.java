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
@JsonSerialize(using = ClientAlertMessageTypeSerializer.class)
public enum EClientAlertMessageTypes {
	VERIFY_MAIL_SOLITUDE("verify_mail_solitude","verify.mail.solitude","client_verify_mail_solitude.html"),
	VERIFIED_MAIL_SUCCESFULL("verified_mail_notification","verified.mail.notification","client_verify_mail_successful.html"),
	RECOVERY_PASSWORD_SOLITUDE("recovery_password_solitude","recovery.password.solitude","client_recovery_password_solitude.html"),
	RECOVERIED_PASSWORD_SUCCESSFULL("recovery_password_notification","recovery.password.notification","client_recovery_password_successful.html"),
	
	ONE_SHOT_STARTING_SCANNER("one_shot_starting_scanner", "one.shot.starting.scanner"),
	ONE_SHOT_FINISHED_SCANNER("one_shot_finished_scanner", "one.shot.finished.scanner"),
	ONE_SHOT_LIMIT_CREDITS_RUNNING_OUT("one_shot_limit_credits_running_out", "one.shot.limit.credits.running.out", "client_one_shot_limit_credits_running_out.html"),
	ONE_SHOT_REPORT_MONTHLY("one_shot_report_monthly", "one.shot.report.monthly", "client_report_monthly_one_shot.html"),
	
	RECURRENT_STARTING_SCANNER("recurrent_starting_scanner", "recurrent.starting.scanner"),
	RECURRENT_NEW_AND_LEAVING_CONTENT("recurrent_new_and_leaving_content", "recurrent.new.and.leaving.content", "client_recurrent_new_and_leaving_content.html"),
	RECURRENT_CONFIGURATION_UPDATED("recurrent_configuration_updated", "recurrent.configuration.updated"),
	RECURRENT_REPORT_MONTHLY("recurrent_report_monthly", "recurrent.report.monthly", "client_report_monthly_recurrent.html"),
	
	TRANSFORM_STARTING_SCANNER("transform_starting_scanner", "transform.starting.scanner"),
	TRANSFORM_NEW_AND_LEAVING_CONTENT("transform_new_and_leaving_content", "transform.new.and.leaving.content", "client_transform_new_and_leaving_content.html"),
	TRANSFORM_CONFIGURATION_UPDATED("transform_configuration_updated", "transform.configuration.updated"),
	TRANSFORM_REPORT_MONTHLY("transform_report_monthly", "transform.report.monthly", "client_report_monthly_transform.html"),
	
	TRANSFORM_IMPULSE_WAITING_APPROVE("transform_impulse_waiting_approve", "transform.impulse.waiting.approve", "client_transform_impulse_waiting_approve.html"),
	TRANSFORM_IMPULSE_APPROVED("transform_impulse_approved", "transform.impulse.approved"),
	TRANSFORM_IMPULSE_REJECTED("transform_impulse_rejected", "transform.impulse.rejected"),
	TRANSFORM_IMPULSE_PUBLISHED("transform_impulse_published", "transform.impulse.published", "client_transform_impulse_published.html"),
	
	PAYMENT_DUE_NOTIFICATION_LVL_1("payment_due_level_1", "payment.due.level.1", "client_payment_due_level_1.html"),
	PAYMENT_DUE_NOTIFICATION_LVL_2("payment_due_level_2", "payment.due.level.2", "client_payment_due_level_2.html"),
	PAYMENT_DUE_NOTIFICATION_LVL_3("payment_due_level_3", "payment.due.level.3", "client_payment_due_level_3.html"),
	
	RENEWAL_PLAN("renewal_plan", "renewal.plan", "client_renewal_plan.html"),
	EXPIRES_PLAN("expires_plan", "expires.plan", "client_expires_plan.html"),
	ALERT_REPORT("alert_report", "alert.report", "alert_report.html")
	;
	
	private String code;
	private String tag;
	private boolean mustSendEmail;
	private String emailTemplate;

	private EClientAlertMessageTypes(String code, String tag) {
		this.code = code;
		this.tag = tag;
		this.mustSendEmail = false;
	}
	private EClientAlertMessageTypes(String code, String tag, String emailTemplate) {
		this.code = code;
		this.tag = tag;
		this.mustSendEmail = true;
		this.emailTemplate = emailTemplate;
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
	
	public boolean isMustSendEmail() {
		return mustSendEmail;
	}
	public void setMustSendEmail(boolean mustSendEmail) {
		this.mustSendEmail = mustSendEmail;
	}
	
	public String getEmailTemplate() {
		return emailTemplate;
	}
	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}
	@JsonCreator
    static EClientAlertMessageTypes findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EClientAlertMessageTypes obtain(String code) {
		for(EClientAlertMessageTypes d : EClientAlertMessageTypes.values()) {
			if (d.getCode().equalsIgnoreCase(code)) return d;
		}
		
		return null;
	}
	public static boolean valid(String scannerType) {
		return EClientAlertMessageTypes.findValue(scannerType) != null;
	}
}

class ClientAlertMessageTypeSerializer extends StdSerializer<EClientAlertMessageTypes> {
	private static final long serialVersionUID = -8761216512740416212L;

	public ClientAlertMessageTypeSerializer() {
		super(EClientAlertMessageTypes.class);
	}
	
	protected ClientAlertMessageTypeSerializer(Class<EClientAlertMessageTypes> t) {
		super(t);
	}

	@Override
	public void serialize(EClientAlertMessageTypes value, JsonGenerator generator, SerializerProvider provider) throws IOException {
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