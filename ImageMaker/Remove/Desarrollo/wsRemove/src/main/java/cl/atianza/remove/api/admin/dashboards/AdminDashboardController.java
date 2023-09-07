package cl.atianza.remove.api.admin.dashboards;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.client.ClientDao;
import cl.atianza.remove.daos.client.ClientPaybillDao;
import cl.atianza.remove.daos.client.ClientPlanDao;
import cl.atianza.remove.daos.commons.ConfigurationDao;
import cl.atianza.remove.daos.commons.ScannerDao;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EScannerTypes;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.external.clients.serp.RemoveSERPEngine;
import cl.atianza.remove.external.clients.serp.RemoveSERPWebResponse;
import cl.atianza.remove.external.clients.serp.RemoveSerpAccountDataResponse;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.views.dashboards.AdminDashboardClientExpiration;
import cl.atianza.remove.models.views.dashboards.AdminDashboardGraphElement;
import cl.atianza.remove.models.views.dashboards.AdminDashboardView;
import cl.atianza.remove.utils.RemoveDateUtils;
import cl.atianza.remove.utils.RemoveFormula;
import cl.atianza.remove.utils.RemoveJsonUtil;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponse;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RemoveUtils;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.UserProfileAccessValidator;
import spark.Request;
import spark.Response;

/**
 * Dashboard service for User account
 * @author pilin
 *
 */
public class AdminDashboardController extends RestController {
	public AdminDashboardController() {
		super(ERestPath.ADMIN_DASHBOARD, LogManager.getLogger(AdminDashboardController.class));
	}

	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			UserProfileAccessValidator.init(token).validateServiceAccess(this, request.requestMethod());
			
			String filterMonthYearParam = RemoveRequestUtil.getOptionalParamString(request, EWebParam.FILTER_MONTH_YEAR);
			
			LocalDate filterMonthYear = filterMonthYearParam != null ? RemoveDateUtils.parseLocalDate(filterMonthYearParam).withDayOfMonth(1) : RemoveDateUtils.nowLocalDate().withDayOfMonth(1);
			
			return RemoveResponseUtil.buildOk(buildDashboard(filterMonthYear));
//			return RemoveResponseUtil.buildOk(buildDummyData(filterMonthYear));
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	private AdminDashboardView buildDashboard(LocalDate filterMonthYear) throws NumberFormatException, RemoveApplicationException {
		AdminDashboardView dash = new AdminDashboardView();
		dash.setFilterMonthYear(filterMonthYear);
		
		buildIncomeBars(dash);
		buildSerps(dash);
		buildPlanIndicators(dash);
		buildExpireList(dash);
		buildPiePlans(dash);
		buildEconomics(dash);
		
		return dash;
	}

	private void buildEconomics(AdminDashboardView dash) {
		getLog().info("Building Economics...");
		dash.getEconomics().setCurrency("eur");
		try {
			dash.getEconomics().setCurrentMonth(ClientPaybillDao.init().totalByMonthYear(LocalDate.now()));
			dash.getEconomics().setTotalAmount(ClientPaybillDao.init().totalByLifetime());
		} catch (Throwable e1) {
			getLog().error("Error calculating Economics Current Month and Lifetime: ", e1);
		}
		try {
			dash.calculateEconomicsCurrentYear();
		} catch (Throwable e) {
			getLog().error("Error calculating Economics Current Year", e);
		}
		try {
			dash.getEconomics().setMrr(RemoveFormula.calculateMrr());
			dash.getEconomics().setClv(RemoveFormula.calculateCLV());
		} catch (Throwable e) {
			getLog().error("Error calculating Mrr or CLV", e);
		}
	}

	private void buildSerps(AdminDashboardView dash) throws RemoveApplicationException {
		getLog().info("Building Serps...");
		RemoveResponse scaleSerpData = RemoveSERPEngine.init().accountData();
		
		if (scaleSerpData.isSuccess() && scaleSerpData.getData() != null) {
			RemoveSerpAccountDataResponse serpData = (RemoveSerpAccountDataResponse) RemoveJsonUtil.jsonToData(RemoveJsonUtil.dataToJson(scaleSerpData.getData()), RemoveSerpAccountDataResponse.class);
			
			dash.getSerps().setTotal(serpData.getAccount_info().getCredits_limit());
			dash.getSerps().setConsumed(serpData.getAccount_info().getCredits_used());		
			dash.getSerps().setRemaining(serpData.getAccount_info().getCredits_remaining());
			dash.getSerps().setRenovationDate(serpData.getAccount_info().getCredits_reset_at());
		}
	}

	private void buildPlanIndicators(AdminDashboardView dash) throws RemoveApplicationException {
		getLog().info("Building Plan Indicators...");
		ScannerDao scannerDao = ScannerDao.init();
		dash.getPlansIndicators().instanceOneShot(scannerDao.countForType(EScannerTypes.ONE_SHOT));
		dash.getPlansIndicators().instanceMonitor(scannerDao.countActivesForType(EScannerTypes.MONITOR));
		dash.getPlansIndicators().instanceTransform(scannerDao.countActivesForType(EScannerTypes.TRANSFORM), 
				0l, 0l);
		dash.getPlansIndicators().instanceDesindexation(RemoveUtils.generateRandomNumber(1, 10), RemoveUtils.generateRandomNumber(5, 20));
	}

	private void buildExpireList(AdminDashboardView dash) throws RemoveApplicationException {
		getLog().info("Building Expire Lists...");
		ClientDao clientDao = ClientDao.init();
		ClientPlanDao clientPlanDao = ClientPlanDao.init();
		
		List<Client> listNextDue = clientDao.listNextDue(14);
		
		if (listNextDue != null) {
			listNextDue.forEach(nextDue -> {
				if (nextDue.getPlanActives() != null) {
					nextDue.getPlanActives().forEach(planActive -> {
						dash.getClients().getNextDue().add(new AdminDashboardClientExpiration(
								nextDue.getName() + " " + nextDue.getLastname(), 
								planActive.getDetail().getName(), 
								planActive.getExpiration_date() != null ? planActive.getExpiration_date().toLocalDate() : null));	
					});
				}
			});
		}
		
		LocalDate startOfYear = LocalDate.now().withMonth(1);
		
		do {
			dash.getClients().getNewRegisters().add(new AdminDashboardGraphElement(
					String.valueOf(startOfYear.getMonth().getValue()), 
					clientPlanDao.countSubscriptionsAtMonth(startOfYear)));
						
			startOfYear = startOfYear.plusMonths(1);
		} while (startOfYear.getMonthValue() <= LocalDate.now().getMonthValue() && startOfYear.getYear() == LocalDate.now().getYear());
	}

	private void buildPiePlans(AdminDashboardView dash) throws RemoveApplicationException {
		getLog().info("Building Pie Plans...");
		ClientPlanDao clientPlanDao = ClientPlanDao.init();
		
		try {
			PlanDao.init().listActiveNotCostumized().forEach(plan -> {
				try {
					dash.getPiePlan().add(new AdminDashboardGraphElement(plan.getName(), clientPlanDao.countActives(plan.getId())));
				} catch (RemoveApplicationException e) {
					getLog().info("Error building pie plans:"+plan, e);
				}
			});
		} catch (RemoveApplicationException e) {}
	}

	private void buildIncomeBars(AdminDashboardView dash) {
		getLog().info("Building Incoming Bars...");
		LocalDate startOfYear = LocalDate.now().withMonth(1);
		
		do {
			try {
				dash.getIncomesBars().add(new AdminDashboardGraphElement(
						String.valueOf(startOfYear.getMonth().getValue()), 
						ClientPaybillDao.init().totalByMonthYear(startOfYear)));
			} catch (RemoveApplicationException e) {
				getLog().error("Error calculating incoming bars for: " + startOfYear, e);
			}
			startOfYear = startOfYear.plusMonths(1);
		} while (startOfYear.getMonthValue() <= LocalDate.now().getMonthValue() && startOfYear.getYear() == LocalDate.now().getYear());
	}

	private AdminDashboardView buildDummyData(LocalDate filterMonthYear) {
		AdminDashboardView dash = new AdminDashboardView();
		
		dash.setFilterMonthYear(filterMonthYear);
		buildDummyEconomics(dash);
		
		dash.getSerps().setTotal(100000l);
		dash.getSerps().setConsumed(RemoveUtils.generateRandomNumber(50000, 100000));
		
		dash.getPlansIndicators().instanceOneShot(RemoveUtils.generateRandomNumber(500, 1000));
		dash.getPlansIndicators().instanceMonitor(RemoveUtils.generateRandomNumber(150, 300));
		dash.getPlansIndicators().instanceTransform(RemoveUtils.generateRandomNumber(200, 400), 
				RemoveUtils.generateRandomNumber(200, 500), RemoveUtils.generateRandomNumber(50, 100));
		dash.getPlansIndicators().instanceDesindexation(RemoveUtils.generateRandomNumber(1, 10), RemoveUtils.generateRandomNumber(5, 20));
		
		buildDummyExpireList(dash);
		buildDummyPiePlans(dash);
		buildDummyIncomeBars(dash);
		
		return dash;
	}

	private void buildDummyEconomics(AdminDashboardView dash) {
		dash.getEconomics().setCurrency("eur");
		dash.getEconomics().setCurrentMonth(RemoveUtils.generateRandomNumber(2000, 4000));
		dash.getEconomics().setCurrentYear(RemoveUtils.generateRandomNumber(20000, 40000));
		dash.getEconomics().setTotalAmount(RemoveUtils.generateRandomNumber(100000, 200000));
		dash.getEconomics().setMrr(RemoveUtils.generateRandomNumber(800, 1200));
		dash.getEconomics().setClv(RemoveUtils.generateRandomNumber(600, 800));
	}

	private void buildDummyExpireList(AdminDashboardView dash) {
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 1", "Transforma y Descata Pro", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 2", "Escaner Recurrente Pro", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 3", "Transforma Elimina Contenido Basico", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 4", "Transforma Elimina Contenido Basico", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 5", "Escaner Recurrente Pro", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 6", "Transforma y Descata Pro", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 7", "Transforma Elimina Contenido Pro", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 8", "Transforma Elimina Contenido Basico", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 9", "Escaner Recurrente Pro", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 10", "Transforma y Descata Pro", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 11", "Transforma Elimina Contenido Basico", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 12", "Escaner Recurrente Pro", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 13", "Transforma Elimina Contenido Basico", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 14", "Transforma Elimina Contenido Pro", RemoveDateUtils.randomNextDays(20)));
		dash.getClients().getNextDue().add(new AdminDashboardClientExpiration("Cliente Dummy 15", "Transforma Elimina Contenido Pro", RemoveDateUtils.randomNextDays(20)));
		
		LocalDate startOfYear = LocalDate.now().withMonth(1);
		
		do {
			dash.getClients().getNewRegisters().add(new AdminDashboardGraphElement(String.valueOf(startOfYear.getMonth().getValue()), RemoveUtils.generateRandomNumber(500, 2000)));
			startOfYear = startOfYear.plusMonths(1);
		} while (startOfYear.getMonthValue() <= LocalDate.now().getMonthValue() && startOfYear.getYear() == LocalDate.now().getYear());
	}

	private void buildDummyPiePlans(AdminDashboardView dash) {
		try {
			PlanDao.init().listActiveNotCostumized().forEach(plan -> {
				dash.getPiePlan().add(new AdminDashboardGraphElement(plan.getName(), RemoveUtils.generateRandomNumber(10, 50)));
			});
		} catch (RemoveApplicationException e) {}
	}

	private void buildDummyIncomeBars(AdminDashboardView dash) {
		LocalDate startOfYear = LocalDate.now().withMonth(1);
		
		do {
			dash.getIncomesBars().add(new AdminDashboardGraphElement(String.valueOf(startOfYear.getMonth().getValue()), RemoveUtils.generateRandomNumber(2000, 4000)));
			startOfYear = startOfYear.plusMonths(1);
		} while (startOfYear.getMonthValue() <= LocalDate.now().getMonthValue() && startOfYear.getYear() == LocalDate.now().getYear());
	}
}
