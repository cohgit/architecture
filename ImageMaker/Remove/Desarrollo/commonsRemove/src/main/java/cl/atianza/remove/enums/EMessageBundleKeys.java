package cl.atianza.remove.enums;

/**
 * Enum with message codes to front.
 * 
 * @author Jose Gutierrez
 *
 */
public enum EMessageBundleKeys {
	OK(200, "OK"),
	
	MESSAGE_AUDIT_SCANNER_ONE_SHOT_GENERATED(301, "message.scanner.one.shot.generated"),
	MESSAGE_AUDIT_SCANNER_RECURRENT_GENERATED(302, "message.scanner.recurrent.generated"),
	MESSAGE_AUDIT_SCANNER_TRANSFORM_GENERATED(303, "message.scanner.transform.generated"),
	
	MESSAGE_AUDIT_SCANNER_RECURRENT_CONFIG_UPDATED(304, "message.scanner.recurrent.configuration.updated"),
	MESSAGE_AUDIT_SCANNER_TRANSFORM_CONFIG_UPDATED(305, "message.scanner.transform.configuration.updated"),
	
	MESSAGE_AUDIT_SCANNER_ONE_SHOT_RESETED(306, "message.scanner.one.shot.reseted"),
	MESSAGE_AUDIT_SCANNER_RECURRENT_RESETED(307, "message.scanner.recurrent.reseted"),
	MESSAGE_AUDIT_SCANNER_TRANSFORM_RESETED(308, "message.scanner.transform.reseted"),
	
	MESSAGE_AUDIT_SCANNER_WEB_SNIPPET_CHANGE_FEELING(309, "message.scanner.web.snippet.change.feeling"),
	MESSAGE_AUDIT_SCANNER_NEWS_SNIPPET_CHANGE_FEELING(310, "message.scanner.news.snippet.change.feeling"),
	MESSAGE_AUDIT_SCANNER_IMAGE_SNIPPET_CHANGE_FEELING(311, "message.scanner.image.snippet.change.feeling"),
	
	MESSAGE_AUDIT_CLIENT_CONFIGURATION_UPDATED(312, "message.client.configuration.updated"),
	MESSAGE_AUDIT_CLIENT_PASSWORD_UPDATED(313, "message.client.password.updated"),
	MESSAGE_AUDIT_CLIENT_LOGIN(314, "message.client.login"),
	MESSAGE_AUDIT_CLIENT_LOGOUT(315, "message.client.logout"),
	MESSAGE_AUDIT_CLIENT_PASSWORD_CHANGE_REQUESTED(316, "message.client.password.change.requested"),
	
	MESSAGE_AUDIT_IMPULSE_VARIABLE_SAVED(317, "message.audit.impulse.variable.saved"),
	MESSAGE_AUDIT_IMPULSE_VARIABLE_UPDATED(318, "message.audit.impulse.variable.updated"),
	
	MESSAGE_AUDIT_CLIENT_SAVED(320, "message.audit.client.saved"),
	MESSAGE_AUDIT_CLIENT_UPDATED(321, "message.audit.client.updated"),
	MESSAGE_AUDIT_CLIENT_SWITCH_ACTIVE(321, "message.audit.client.active"),
	MESSAGE_AUDIT_CLIENT_SWITCH_DEACTIVE(321, "message.audit.client.deactive"),
	MESSAGE_AUDIT_CLIENT_PAYMENT_APPROVED(321, "message.audit.client.payment_approved"),
	
	MESSAGE_AUDIT_USER_SAVED(322, "message.audit.user.saved"),
	MESSAGE_AUDIT_USER_UPDATED(323, "message.audit.user.updated"),
	
	MESSAGE_AUDIT_PLAN_SAVED(324, "message.audit.plan.saved"),
	MESSAGE_AUDIT_PLAN_UPDATED(325, "message.audit.plan.updated"),
	
	MESSAGE_AUDIT_TRACKING_WORD_SAVED(326, "message.audit.tracking.word.saved"),
	MESSAGE_AUDIT_TRACKING_WORD_FILE_SAVED(326, "message.audit.tracking.word.file.saved"),
	MESSAGE_AUDIT_TRACKING_WORD_UPDATED(327, "message.audit.tracking.word.updated"),
	
	MESSAGE_AUDIT_FORBIDDEN_WORD_SAVED(328, "message.audit.forbidden.word.saved"),
	MESSAGE_AUDIT_FORBIDDEN_WORD_UPDATED(329, "message.audit.forbidden.word.updated"),
	
	MESSAGE_AUDIT_SCANNER_STOPPED(306, "message.audit.scanner.stopped"),
	MESSAGE_AUDIT_SCANNER_DELETED(307, "message.audit.scanner.deleted"),
	MESSAGE_AUDIT_SCANNER_RESUMED(308, "message.audit.scanner.resumed"),
	
	MESSAGE_AUDIT_IMPULSE_CREATED(308, "message.audit.impulse.created"),
	MESSAGE_AUDIT_IMPULSE_TO_BE_APPROVED(308, "message.audit.impulse.to.be.approved"),
	MESSAGE_AUDIT_IMPULSE_APPROVED(308, "message.audit.impulse.approved"),
	MESSAGE_AUDIT_IMPULSE_REJECTED(308, "message.audit.impulse.rejected"),
	MESSAGE_AUDIT_IMPULSE_PUBLISHED(308, "message.audit.impulse.published"),
	
	ERROR_INVALID_PARAMS(400, "error.invalid.params"), // PAR\u00C1METROS INV\u00C1LIDOS
	ERROR_USER_NOT_ALLOWED(403, "error.user.not.allowed"), // USUARIO NO PERMITIDO
	ERROR_NULL_VALUE(404, "error.null.value"), // SE INTENT\u00D3 RETORNAR UN VALOR NULO
	ERROR_INVALID_PASSWORD(405, "error.invalid.credentials"), // CREDENCIALES INV\u00C1LIDAS
	ERROR_STORING_FILE(406, "error.file.stored"),
	ERROR_FILE_NOT_FOUND(407, "error.file.not.found"),
	
	ERROR_REGISTER_DUPLICATED(411, "error.already.exists"), // EL USUARIO YA SE ENCUENTRA REGISTRADO EN EL SISTEMA
	ERROR_USER_DUPLICATED(412, "error.user.already.exists"),
	ERROR_CLIENT_DUPLICATED(413, "error.client.already.exists"),
	ERROR_MAIL_ALREADY_REGISTERED(414, "error.mail.already.registered"),
	ERROR_PHONE_ALREADY_REGISTERED(415, "error.phone.already.registered"),
	ERROR_SENDING_EMAIL(424, "error.sending.email"), // NO SE PUDO ENVIAR EL CORREO
	
	ERROR_PAYMENT_CONFIRMATION(425, "error.stripe.payment.confirmation"), // NO SE CONFIRMAR EL PAGO POR STRIPE
	
	ERROR_INTERNAL_SERVER(500, "error.internal.server"), // ERROR INTERNO DEL SERVIDOR
	ERROR_SERVICE_NOT_IMPLEMENTED(501, "error.service.not.implemented"), // SERVICIO NO IMPLEMENTADO
	ERROR_DATABASE(502, "error.database"), // ERROR EN TRANSACCI\u00D3N DB
	ERROR_UPDATING_DATABASE(503, "error.updating.database"), // NO SE ACTUALIZ\u00D3 EL REGISTRO EN BASE DE DATOS
	ERROR_GATEWAY(504, "error.gateway"), // ERROR DE SERVICIO EXTERNO
	ERROR_AUTHENTICATION_REQUIRED(511, "error.authentication"), // AUTENTICACI\u00D3N REQUERIDA
	ERROR_INITIALIZING_SESSION(512, "error.initializing.session"), // ERROR CREANDO LA SESI\u00D3N
	ERROR_PARSING_JSON(513, "error.parsing.object"), // ERROR PARSING OBJECT: 
	ERROR_PARSING_FILE(514, "error.parsing.file"), // ERROR PARSING FILE: 
	ERROR_EXTERNAL_SERVICE(515, "error.external.service"), // ERROR CONECTANDO CON SERVICIO EXTERNO: 
	ERROR_CONNECTION_DATABASE(515, "error.database.connection"), // ERROR EN CONEXI\u00D3N DB
	ERROR_DELETING_REGISTER(516, "error.removing.record"), // NO SE PUDO ELIMINAR EL REGISTRO
	ERROR_DECRIPT_TOKEN(518, "error.decript.token"), // ERROR DESENCRIPTANDO TOKEN
	ERROR_VALIDATING_ACCESS(519, "error.validating.access"), // ERROR VALIDANDO ACCESO
	ERROR_RESET_PASSWORD_INVALID(520, "error.reset.password.invalid"),
	ERROR_EMAIL_VERIFICATION_INVALID(521, "error.email.verification.invalid"),
	ERROR_EMAIL_VERIFICATION_ALREADY(522, "error.email.verification.already"),
	ERROR_EMAIL_VERIFICATION_EXPIRED(523, "error.email.verification.expired"),
	
	ERROR_UNSUPPORTED_PAYMENT_TYPE(550, "error.unsuppoted.payment.type"),
	
	ERROR_JOB_PROGRAMMING(524, "error.starting.job"), // ERROR INICIANDO JOB
	
	ERROR_USER_BLOCKED(603, "error.user.blocked"), // USUARIO BLOQUEADO EN EL SISTEMA
	
	ERROR_USER_NOT_ALLOWED_THIS_CLIENT(610, "error.user.not.allowed.this.client"),
	ERROR_CLIENT_NOT_FOUND(611, "error.client.not.found"),
	ERROR_CLIENT_NOT_ACTIVE(612, "error.client.not.active"),
	ERROR_USER_NOT_FOUND(613, "error.user.not.found"),
	ERROR_USER_NOT_ACTIVE(614, "error.user.not.active"),
	ERROR_USER_NOT_MATCH_PROFILE(615, "error.user.not.match.profile"),
	ERROR_USER_PROFILE_NOT_EXISTS(616, "error.user.profile.not.exists"),
	ERROR_CLIENT_AND_SCANNER_DONT_MATCH(617, "error.client.scanner.dont.match"),
	ERROR_CLIENT_SCANNER_DONT_MATCH_PLAN(618, "error.client.scanner.dont.match.plan"),
	ERROR_CLIENT_SCANNER_NOT_ACTIVE(619, "error.client.scanner.not.active"),
	ERROR_CLIENT_SCANNER_DONT_MATCH(620, "error.client.scanner.dont.match"),
	ERROR_CLIENT_READ_ONLY(621, "error.client.read.only"),
	ERROR_CLIENT_MAX_SCANNERS_ALLOWED_EXCEDED(622, "error.client.max.scanners.allowed.exceded"),
	ERROR_SUSCRIPTION_NOT_FOUND(623, "error.suscription.not.found"),
	ERROR_INVALID_SUSCRIPTION_EMAIL(624, "error.invalid.suscription.email"),
	ERROR_INVALID_SUSCRIPTION_PLAN(625, "error.invalid.suscription.plan"),
	ERROR_INVALID_SUSCRIPTION_ATTEMPTED(626, "error.invalid.suscription.attempted"),
	ERROR_INVALID_SUSCRIPTION_REGISTRED(627, "error.invalid.suscription.registred"),
	
	ERROR_SCANNER_EXCEEDS_MAX_KEYWORDS(620, "error.scanner.exceeds.max.keywords"),
	ERROR_SCANNER_EXCEEDS_MAX_COUNTRIES(621, "error.scanner.exceeds.max.countries"),
	ERROR_SCANNER_EXCEEDS_MAX_PAGES(622, "error.scanner.exceeds.max.pages"),
	ERROR_SCANNER_INVALID_SEARCH_TYPES(623, "error.scanner.invalid.search.types"),
	ERROR_SCANNER_EXCEEDS_CREDITS(624, "error.scanner.exceeds.credits"),
	ERROR_SCANNER_EXCEEDS_MAX_URL_TO_REMOVE(625, "error.scanner.exceeds.max.url.to.remove"),
	ERROR_CLIENT_NOTIFICATION_NOT_MATCH(626, "error.client.alert.not.match"),
	ERROR_USER_NOTIFICATION_NOT_MATCH(627, "error.user.alert.not.match"),
	ERROR_CLIENT_PAYMENT_KEY_NOT_FOUND(628, "error.client.payment.key.not.found"),
	ERROR_CLIENT_DEINDEXATION_DONT_MATCH(629, "error.client.deindexation.dont.match"),
	ERROR_DEINDEXATION_EXCEEDS_CREDITS(630, "error.deindexation.exceeds.credits"),
	ERROR_SCANNER_FORBIDDEN_WORDS_FOUND(620, "error.scanner.forbidden.words.found"),
	
	WARNING_DEFAULT(704, "warning.default"),
	WARNING_PAYMENT_PAST_DUE(705, "warning.payment.past.due"),
	WARNING_CLIENT_MUST_VERIFY_EMAIL(706, "warning.client.must.verify.email"),
	WARNING_WAITING_FOR_TRANSFER(707, "warning.waiting.for.transfer"),
	WARNING_WAITING_FOR_TRANSFER_VERIFICATION(708, "warning.waiting.for.transfer.verification"),
	
	ERROR_CLIENT_NOT_EMAIL(709, "error.client.not.email"),
	
	USER_ALERT_CREATE(710, "user.alert.create"),
	USER_ALERT_UPDATE(711, "user.alert.update"),
	
	CLIENT_ALERT_CREATE(712, "user.alert.create"),
	CLIENT_ALERT_UPDATE(713, "user.alert.update")
	;
	
	private String tag;
	private int code;

	private EMessageBundleKeys(int code, String tag) {
		this.tag = tag;
		this.code = code;
	}

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
