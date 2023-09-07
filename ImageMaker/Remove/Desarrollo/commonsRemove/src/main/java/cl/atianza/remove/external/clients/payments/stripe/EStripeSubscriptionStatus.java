package cl.atianza.remove.external.clients.payments.stripe;

public enum EStripeSubscriptionStatus {
	INCOMPLETE("incomplete"),
	INCOMPLETE_EXPIRED("incomplete_expired"),
	TRIALING("trialing"),
	ACTIVE("active"),
	PAST_DUE("past_due"),
	CANCELED("canceled"),
	UNPAID("unpaid");
	
	private String code;

	private EStripeSubscriptionStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public static EStripeSubscriptionStatus find(String code) {
		for (EStripeSubscriptionStatus status : EStripeSubscriptionStatus.values()) {
			if (status.getCode().equals(code)) return status;
		}
		
		return null;
	}
	
	public boolean same(EStripeSubscriptionStatus other) {
		return this.getCode().equals(other.getCode());
	}
}
