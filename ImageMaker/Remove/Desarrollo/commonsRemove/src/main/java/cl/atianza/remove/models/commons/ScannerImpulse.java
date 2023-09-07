package cl.atianza.remove.models.commons;

import cl.atianza.remove.dtos.commons.ScannerImpulseDto;
import cl.atianza.remove.enums.EImpulseStatus;
import cl.atianza.remove.enums.EImpulseType;
import cl.atianza.remove.enums.EProfiles;

public class ScannerImpulse extends ScannerImpulseDto {
	private ScannerImpulseContent content;
	private ScannerKeyword keyword;
	private EImpulseType typeObj;
	private EImpulseStatus statusObj;
	
	private String uuidClient;
	private String uuidScanner;
	private Long idClientPlan;
	private boolean editableContent;
	private boolean editableSolitude = false;
	private boolean aprovableRejectable;
	private boolean askAprovable;
	
	public ScannerImpulse() {
		super();
	}

	public ScannerImpulseContent getContent() {
		return content;
	}

	public void setContent(ScannerImpulseContent content) {
		this.content = content;
	}

	public ScannerKeyword getKeyword() {
		return keyword;
	}

	public void setKeyword(ScannerKeyword keyword) {
		this.keyword = keyword;
	}

	public EImpulseType getTypeObj() {
		return typeObj;
	}

	public void setTypeObj(EImpulseType typeObj) {
		this.typeObj = typeObj;
	}

	public EImpulseStatus getStatusObj() {
		return statusObj;
	}

	public void setStatusObj(EImpulseStatus statusObj) {
		this.statusObj = statusObj;
	}
	
	public String getUuidClient() {
		return uuidClient;
	}

	public void setUuidClient(String uuidClient) {
		this.uuidClient = uuidClient;
	}

	public Long getIdClientPlan() {
		return idClientPlan;
	}

	public void setIdClientPlan(Long idClientPlan) {
		this.idClientPlan = idClientPlan;
	}

	public boolean esWordingRequest() {
		return this.getType().equals(EImpulseType.WORDING_REQUESTED.getTag());
	}
	public boolean esPublishedUrl() {
		return this.getType().equals(EImpulseType.PUBLISHED_URL.getTag());
	}
	public boolean esOwnWriting() {
		return this.getType().equals(EImpulseType.OWN_WRITING.getTag());
	}
	
	public boolean isEditableContent() {
		return editableContent;
	}
	public void setEditableContent(boolean editableContent) {
		this.editableContent = editableContent;
	}

	public boolean isEditableSolitude() {
		return editableSolitude;
	}

	public void setEditableSolitude(boolean editableSolitude) {
		this.editableSolitude = editableSolitude;
	}

	public boolean isAprovableRejectable() {
		return aprovableRejectable;
	}
	public void setAprovableRejectable(boolean aprovableRejectable) {
		this.aprovableRejectable = aprovableRejectable;
	}

	public boolean isAskAprovable() {
		return askAprovable;
	}
	public void setAskAprovable(boolean askAprovable) {
		this.askAprovable = askAprovable;
	}

	/**
	 * Verify level access for user interactions against this impulse
	 * @param idUserAccess
	 * @param profile
	 */
	public void checkAccess(Long idUserAccess, String profile) {
		// Check impulse type
		if (this.getType().equals(EImpulseType.OWN_WRITING.getTag())) {
			this.checkAccessForOwnWritting(idUserAccess, profile);
		} else if (this.getType().equals(EImpulseType.WORDING_REQUESTED.getTag())) {
			this.checkAccessForWordingRequest(idUserAccess, profile);
		} else if (this.getType().equals(EImpulseType.PUBLISHED_URL.getTag())) {
			this.checkAccessForUrlPublished(idUserAccess, profile);
		} else {
			this.lockAll();
		}
	}

	/**
	 * Verify level access for user interactions against this impulse if its type is OWN_WRITING
	 * @param idUserAccess
	 * @param profile
	 */
	private void checkAccessForOwnWritting(Long idUserAccess, String profile) {
		// This user wrote this impulse content
		if (this.sameAuthorAndProfile(idUserAccess, profile)) {
			switch (this.getStatusObj()) {
				case DRAFT:
				case REJECTED:
					this.setEditableContent(true);
					this.setAskAprovable(true);
					
					this.setAprovableRejectable(false);
					break;
				case TO_BE_APPROVED: 
				case APPROVED:
				case PUBLISHED:
					this.lockAllButEditor();
					break;
				default:
					this.lockAll();
					break;
			}
		// This user didn't wrote this impulse content
		} else {
			this.setEditableContent(false);
			this.setAskAprovable(false);
			
			switch (this.getStatusObj()) {
				case TO_BE_APPROVED:
					this.setAprovableRejectable(true);
					break;
				case DRAFT:
				case APPROVED:
				case REJECTED:
				case PUBLISHED:
					this.setAprovableRejectable(false);
					break;
				default:
					this.lockAll();
			}
		}
	}
	
	/**
	 * Verify level access for user interactions against this impulse if its type is WORDING_REQUESTED
	 * @param idUserAccess
	 * @param profile
	 */
	private void checkAccessForWordingRequest(Long idUserAccess, String profile) {
		// If the client is checking the impulse
		if (profile.equals(EProfiles.CLIENT.getCode())) {
			this.setAccessForWordingRequestAsSolicitor();
		// The client is not checking the impulse
		} else {
			// This user requested the wording
			if (isUserCreator(idUserAccess, profile)) {
				this.setAccessForWordingRequestAsSolicitor();
			// This user didn't requested the wording
			} else {
				this.setAprovableRejectable(false);
				
				switch (this.getStatusObj()) {
					case AWAITING_DRAFTING:
					case REJECTED:
						this.setEditableContent(true);
						this.setAskAprovable(true);
						break;
					case TO_BE_APPROVED:
					case APPROVED:
					case PUBLISHED:
						this.setEditableContent(false);
						this.setAskAprovable(false);
						break;
					default:
						this.lockAll();
				}
			}
		}
	}
	
	private void setAccessForWordingRequestAsSolicitor() {
		this.setEditableContent(false);
		this.setAskAprovable(false);
		
		switch (this.getStatusObj()) {
			case AWAITING_DRAFTING:
				this.setAprovableRejectable(false);
				break;
			case TO_BE_APPROVED:
				this.setAprovableRejectable(true);
				break;
			case APPROVED:
			case REJECTED:
			case PUBLISHED:
				this.setAprovableRejectable(false);
				break;
			default:
				this.lockAll();
		}
	}
	
	/**
	 * Verify level access for user interactions against this impulse if its type is PUBLISHED_URL
	 * @param idUserAccess
	 * @param profile
	 */
	private void checkAccessForUrlPublished(Long idUserAccess, String profile) {
		this.setEditableContent(this.sameAuthorAndProfile(idUserAccess, profile));
		this.setAskAprovable(false);
		this.setAprovableRejectable(false);
	}

	private void lockAll() {
		this.setEditableContent(false);
		this.setAskAprovable(false);
		this.setAprovableRejectable(false);
	}
	private void lockAllButEditor() {
		this.setEditableContent(false);
		this.setAskAprovable(false);
		this.setAprovableRejectable(false);
	}

	private boolean sameAuthorAndProfile(Long idUserAccess, String profile) {
		return this.getContent() != null && this.getContent().getId_author().longValue() == idUserAccess.longValue() && this.getContent().getAuthor_profile().equals(profile);
	}

	private boolean isUserCreator(Long idUser, String profile) {
		return this.getId_creator().longValue() == idUser.longValue() && this.getCreator_profile().equals(profile);
	}

	public String getUuidScanner() {
		return uuidScanner;
	}

	public void setUuidScanner(String uuidScanner) {
		this.uuidScanner = uuidScanner;
	}

	@Override
	public String toString() {
		return "ScannerImpulse [content=" + content + ", keyword=" + keyword + ", typeObj=" + typeObj + ", statusObj="
				+ statusObj + "]";
	}	
}
