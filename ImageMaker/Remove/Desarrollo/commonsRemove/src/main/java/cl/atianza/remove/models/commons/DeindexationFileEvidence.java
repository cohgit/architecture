package cl.atianza.remove.models.commons;

import cl.atianza.remove.dtos.commons.DeindexationFileEvidenceDto;
@Deprecated
public class DeindexationFileEvidence extends DeindexationFileEvidenceDto {
	private Object content; //TODO: Delete this or see how this can work. (Chang when upload bucket files defined)
	private boolean markForDelete;

	
	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}
