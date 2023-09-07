package cl.atianza.remove.api.client.deindexation;

import org.apache.logging.log4j.LogManager;

import cl.atianza.remove.daos.admin.PlanDao;
import cl.atianza.remove.daos.commons.DeindexationDao;
import cl.atianza.remove.daos.commons.DeindexationHistoricDao;
import cl.atianza.remove.daos.commons.DeindexationUrlDao;
import cl.atianza.remove.enums.EProfiles;
import cl.atianza.remove.enums.ERestPath;
import cl.atianza.remove.enums.EWebParam;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.admin.Plan;
import cl.atianza.remove.models.client.Client;
import cl.atianza.remove.models.commons.Deindexation;
import cl.atianza.remove.models.views.ListDeindaxations;
import cl.atianza.remove.utils.RemoveRequestUtil;
import cl.atianza.remove.utils.RemoveResponseUtil;
import cl.atianza.remove.utils.RemoveTokenAccess;
import cl.atianza.remove.utils.RestController;
import cl.atianza.remove.validators.ClientAccessValidator;
import cl.atianza.remove.validators.DeindexationValidator;
import spark.Request;
import spark.Response;

/**
 * REST deindexation service for deindexation operations.
 * @author Jose Gutierrez
 *
 */
@Deprecated
public class ClientDeindexationController extends RestController {
	public ClientDeindexationController() {
		super(ERestPath.CLIENT_DEINDEXATION, LogManager.getLogger(ClientDeindexationController.class));
	}

	@Override
	public Object list(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LIST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			
			ListDeindaxations table = new ListDeindaxations();
			table.setList(DeindexationDao.init().listByOwner(client.getId()));
			
			Integer urlOcupieds = DeindexationUrlDao.init().countByPlan(client.getPlanActives().get(0).getId());
			Plan plan = PlanDao.init().get(client.getPlanActives().get(0).getId_plan());
			table.setUrlsAvailables(plan.getMax_url_to_deindexate().intValue() - urlOcupieds.intValue());
			
			return RemoveResponseUtil.buildOk(table);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	
	@Override
	public Object get(Request request, Response response) throws RemoveApplicationException {
		getLog().info("GET: " + this.getClass());
		RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
		Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
		
		String uuidDeindexation = RemoveRequestUtil.getOptionalParamString(request, EWebParam.UUID);
		getLog().info("Loading deindexation: " + uuidDeindexation);
		
		Deindexation deindexation = uuidDeindexation != null && !uuidDeindexation.isEmpty() ? 
				DeindexationDao.init().getByUuid(uuidDeindexation, client.getLanguage()) : DeindexationDao.init().getTemplate(client.getLanguage());

		if (!deindexation.esNuevo()) {
			DeindexationValidator.init(client).validateClientDeindexation(deindexation);	
		}
		
		Integer urlOcupieds = DeindexationUrlDao.init().countByPlan(client.getPlanActives().get(0).getId());
		Plan plan = PlanDao.init().get(client.getPlanActives().get(0).getId_plan());
		deindexation.setMaxUrlsAvailables(plan.getMax_url_to_deindexate().intValue() - urlOcupieds.intValue());
		
		return RemoveResponseUtil.buildOk(deindexation);
	}

	@Override
	public Object post(Request request, Response response) throws RemoveApplicationException {
		getLog().info("POST: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			
			Deindexation deindexation = (Deindexation) RemoveRequestUtil.getBodyObject(request, Deindexation.class);
			DeindexationValidator deindexValidator = DeindexationValidator.init(client);
//			deindexValidator.validateClientDeindexation(deindexation);
			deindexValidator.checkDeindexationPlanLimitations(deindexation);
			
			deindexation = DeindexationDao.init().save(deindexation, client.getId(), EProfiles.CLIENT.getCode());
			
			DeindexationHistoricDao.init().save(deindexation, client.getId(), EProfiles.CLIENT.getCode(), "message.create.deindexation");
			return RemoveResponseUtil.buildOk(deindexation);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object put(Request request, Response response) throws RemoveApplicationException {
		getLog().info("PUT: " + this.getClass());

		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			
			Deindexation deindexation = (Deindexation) RemoveRequestUtil.getBodyObject(request, Deindexation.class);
			DeindexationValidator deindexValidator = DeindexationValidator.init(client);
			deindexValidator.validateClientDeindexation(deindexation);
			deindexValidator.checkDeindexationPlanLimitations(deindexation);
			
			deindexation = DeindexationDao.init().update(deindexation, client.getId(), EProfiles.CLIENT.getCode());
			
			DeindexationHistoricDao.init().save(deindexation, client.getId(), EProfiles.CLIENT.getCode(), "message.update.deindexation");
			return RemoveResponseUtil.buildOk(deindexation);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	/*
	@Override
	public Object loadFile(Request request, Response response) throws RemoveApplicationException {
		getLog().info("LOAD FILE: " + this.getClass());
		
		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			
			UploaderFile infoFile = (UploaderFile) RemoveRequestUtil.extractDataMultipart(request, UploaderFile.class);
			getLog().info("infoFile: " + infoFile);
			
			DeindexationValidator.init(client).validateClientDeindexation(infoFile.getIdDeindexation());
			
			if (infoFile.isMarkForDelete()) { // Service recycled for delete file...
				DeindexationFileEvidenceDao.init().delete(infoFile.getId());
				return RemoveResponseUtil.buildDefaultOk();
			} else {
				DeindexationFileEvidence fileEvidence = null;
				
				switch(infoFile.getType()) {
					case "FILE":
						Part file = RemoveRequestUtil.extractFileMultipart(request);
						infoFile = FileManager.init().uploadFile(infoFile, file);
					case "URL":
						fileEvidence = new DeindexationFileEvidence();
						fileEvidence.setCreation_date(RemoveDateUtils.nowLocalDateTime());
						fileEvidence.setFile_address(infoFile.getLink());
						fileEvidence.setFile_description(infoFile.getDescription());
						fileEvidence.setFile_name_file(infoFile.getName());
						fileEvidence.setFile_type(infoFile.getType());
						fileEvidence.setId_deindexation(infoFile.getIdDeindexation());
						fileEvidence.setId_uploader(client.getId());
						fileEvidence.setUploader_profile(EProfiles.CLIENT.getCode());
						fileEvidence.setMarkForDelete(infoFile.isMarkForDelete());
						fileEvidence.setExtension(infoFile.getExtension());
						
						fileEvidence = DeindexationFileEvidenceDao.init().save(fileEvidence);
					
						break;
					default:
						getLog().error("File type not supported: " + infoFile);
						return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_INVALID_PARAMS);
				}
				
				return RemoveResponseUtil.buildOk(fileEvidence);
			}
		} catch (Throwable t) {
			return catchException(t);
		} 
	}

	@Override
	public Object downloadFile(Request request, Response response) throws RemoveApplicationException {
		getLog().info("DOWNLOAD FILE: " + this.getClass());
		
		try {
			RemoveTokenAccess token = RemoveRequestUtil.extractToken(request);
			Client client = ClientAccessValidator.init(token.getUuidAccount()).validateClientAccess();
			
			DeindexationFileEvidence file = DeindexationFileEvidenceDao.init().getById(RemoveRequestUtil.getParamLong(request, EWebParam.ID.getTag()));
			DeindexationValidator.init(client).validateClientDeindexation(file.getId_deindexation());
			
			if (file != null && file.getFile_address() != null) {
				return buildFileRawResponse(response, file.getExtension(), file.getFile_address());
			}
			
			return RemoveResponseUtil.buildError(EMessageBundleKeys.ERROR_FILE_NOT_FOUND);
		} catch (Throwable t) {
			return catchException(t);
		} 
	}
	*/
}
