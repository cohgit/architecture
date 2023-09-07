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
@JsonSerialize(using = UserAlertMessageTypeSerializer.class)
public enum EUserAlertMessageTypes {
	VERIFY_MAIL_SOLITUDE("user_verify_mail_solitude","user.verify.mail.solitude","user_verify_mail_solitude.html"),
	VERIFY_MAIL_SUCCESSFUL("user_verify_mail_successful","user.verify.mail.successful", "user_verify_mail_successful.html"),
	RECOVERY_PASSWORD_SOLITUDE("user_recovery_password_notification","user.recovery.password.solitude","user_recovery_password_solitude.html"),
	RECOVERY_PASSWORD_SUCCESSFULL("user_recovery_password_notification","user.recovery.password.successful","user_recovery_password_successful.html"),
	
	NEW_CLIENTS_ASSIGNED("new_clients_assigned","new.clients.assigned", "user_new_clients_assigned.html"),
	
	TRANSFORM_IMPULSE_AWAITING_WORDING("transform_impulse_awaiting_wording", "transform.impulse.awaiting.wording"),
	TRANSFORM_IMPULSE_WAITING_APPROVE("transform_impulse_waiting_approve", "transform.impulse.waiting.approve", "user_transform_impulse_waiting_approve.html"),
	TRANSFORM_IMPULSE_APPROVED("transform_impulse_approved", "transform.impulse.approved"),
	TRANSFORM_IMPULSE_REJECTED("transform_impulse_rejected", "transform.impulse.rejected"),
	
	USER_EXPIRES_PLAN_CLIENT("user_expires_plan_client", "user.expires.plan.client", "user_expires_plan_client.html"),
	
	ALERT_REPORT("alert_report", "alert.report", "alert_report.html"),
	
	NEW_CLIENT_SUSCRIPTION("user_new_client_suscription", "user.new.client.suscription", "user_new_client_suscription.html"),
	
	ALERT_COST_SERP_API("alert_cost_serp_api", "alert.cost.serp.api", "user_alert_cost_serp_api.html"),
	
	ADMIN_REPORT_MONTHLY("admin_report_monthly", "admin.report.monthly", "user_admin_report_monthly.html"),
	MANAGER_REPORT_MONTHLY("manager_report_monthly", "manager.report.monthly", "user_manager_report_monthly.html"),
	EDITOR_REPORT_MONTHLY("editor_report_monthly", "editor.report.monthly", "user_editor_report_monthly.html"),
	FORMULE_REPORT_MONTHLY("formule_report_monthly", "formula.report.monthly", "user_formula_report_monthly.html")
	;
	
	private String code;
	private String tag;
	private boolean mustSendEmail;
	private String emailTemplate;

	private EUserAlertMessageTypes(String code, String tag) {
		this.code = code;
		this.tag = tag;
		this.mustSendEmail = false;
	}
	private EUserAlertMessageTypes(String code, String tag, String emailTemplate) {
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
    static EUserAlertMessageTypes findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static EUserAlertMessageTypes obtain(String code) {
		for(EUserAlertMessageTypes d : EUserAlertMessageTypes.values()) {
			if (d.getCode().equalsIgnoreCase(code)) return d;
		}
		
		return null;
	}
	public static boolean valid(String scannerType) {
		return EUserAlertMessageTypes.findValue(scannerType) != null;
	}
}

class UserAlertMessageTypeSerializer extends StdSerializer<EUserAlertMessageTypes> {
	private static final long serialVersionUID = -8761216512740416212L;

	public UserAlertMessageTypeSerializer() {
		super(EUserAlertMessageTypes.class);
	}
	
	protected UserAlertMessageTypeSerializer(Class<EUserAlertMessageTypes> t) {
		super(t);
	}

	@Override
	public void serialize(EUserAlertMessageTypes value, JsonGenerator generator, SerializerProvider provider) throws IOException {
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