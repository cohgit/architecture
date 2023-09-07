package cl.atianza.remove.api.xternal.commons;

import static spark.Spark.get;
import static spark.Spark.post;

import static spark.Spark.path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.helpers.EmailHelper;
import cl.atianza.remove.models.views.CheckoutPlansView;
import cl.atianza.remove.models.views.ContactMailView;
import cl.atianza.remove.utils.AbstractRestController;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import spark.Request;
import spark.Response;

/**
 * Common services used by the system. Generally lists required by forms in the frontend.
 * @author Jose Gutierrez
 *
 */
public class XternalCommonController {
	private static Logger log = LogManager.getLogger(XternalCommonController.class);
	
	public static void buildPaths() {
		path(ERestPath.XTERNAL.getPath(), () -> {
			get(ERestPath.XTERNAL_COMMON_PLAN.getNonBasePath(), (Request request, Response response) -> {
				try {
					Long idPlan = RemoveRequestUtil.getParamLong(request, EWebParam.ID);
					CheckoutPlansView checkout = new CheckoutPlansView(PlanDao.init().getForCheckout(idPlan));
					
					checkout.getParams().put("TRANSFER_MIN_VALUE", ConfigurationDao.init().getTransferMinValue());
					
					return RemoveResponseUtil.buildOk(checkout);
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });		
			get(ERestPath.XTERNAL_COMMON_PLANS.getNonBasePath(), (Request request, Response response) -> {
				try {
					CheckoutPlansView checkout = new CheckoutPlansView(PlanDao.init().listActiveNotCostumized());
					
					checkout.getParams().put("TRANSFER_MIN_VALUE", ConfigurationDao.init().getTransferMinValue());
					
					return RemoveResponseUtil.buildOk(checkout);
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });		
			post(ERestPath.XTERNAL_COMMON_CONTACTS.getNonBasePath(), (Request request, Response response) -> {
				try {
					EmailHelper.init().sendEmailToContact((ContactMailView) RemoveRequestUtil.getBodyObject(request, ContactMailView.class));
					
					return RemoveResponseUtil.buildDefaultOk();
	        	} catch (Throwable t) {
	    			return AbstractRestController.catchException(log, t);
	    		}
	        });	
			get(ERestPath.XTERNAL_COMMON_PING.getNonBasePath(), (Request request, Response response) -> {
				return RemoveResponseUtil.buildDefaultOk();
	        });
			get(ERestPath.XTERNAL_COMMON_CERTIFICATE.getNonBasePath(), (Request request, Response response) -> {
				response.type("text/html");

				String path = RemoveRequestUtil.getOptionalParamString(request, EWebParam.PATH);
				return "<!DOCTYPE html><html><script >window.location.replace('"+path+"')</script></html>";
	        });
	    });
	}
}
