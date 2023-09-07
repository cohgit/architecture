package cl.atianza.remove.models.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.atianza.remove.models.admin.Plan;

@Deprecated
public class CheckoutPlansView {
	private List<Plan> plans;
	private Map<String, Object> params = new HashMap<String, Object>();
	public CheckoutPlansView() {
		super();
	}
	public CheckoutPlansView(List<Plan> plans) {
		super();
		this.plans = plans;
	}
	public CheckoutPlansView(Plan plan) {
		super();
		this.plans = new ArrayList<Plan>();
		if (plan != null) this.plans.add(plan);
	}
	
	public List<Plan> getPlans() {
		return plans;
	}
	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	@Override
	public String toString() {
		return "CheckoutPlansView [plans=" + plans + ", params=" + params + "]";
	}
}
