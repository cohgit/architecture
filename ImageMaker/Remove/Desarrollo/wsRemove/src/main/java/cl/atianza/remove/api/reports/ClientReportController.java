package cl.atianza.remove.api.reports;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.enums.EReportTypes;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.ReportParams;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import spark.Request;
import spark.Response;

/**
 * Plan manager REST service.
 * @author pilin
 *
 */
public class ClientReportController extends RestController {
	public ClientReportController() {
		super(ERestPath.CLIENT_REPORTS, LogManager.getLogger(ClientReportController.class));
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			ReportParams params = (ReportParams) RemoveRequestUtil.getBodyObject(request, ReportParams.class);
			
			params.addParameter("KEY_PARAM_FILTER_USER_UUID", token.getUuidAccount());
			params.addParameter("KEY_PARAM_FILTER_USER_PROFILE", token.getProfile());
			
			EReportTypes reportType = EReportTypes.find(params.getCode());
			
			if (reportType == null || !reportType.isForClient()) {
				return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
			}
			
			return reportType.getBuildFunction().apply(params);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
}
