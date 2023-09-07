package cl.atianza.remove.models.views;

public class ServiceAccess {
	private String key;
	private boolean read = false;
	private boolean save = false;
	private boolean update = false;
	private boolean delete = false;
	private boolean list = false;
	private boolean file = false;
	
	public ServiceAccess() {
		super();
	}
	public ServiceAccess(String key, boolean read, boolean save, boolean update, boolean delete, boolean list,
			boolean file) {
		super();
		this.key = key;
		this.read = read;
		this.save = save;
		this.update = update;
		this.delete = delete;
		this.list = list;
		this.file = file;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public boolean isSave() {
		return save;
	}
	public void setSave(boolean save) {
		this.save = save;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public boolean isList() {
		return list;
	}
	public void setList(boolean list) {
		this.list = list;
	}
	public boolean isFile() {
		return file;
	}
	public void setFile(boolean file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "ServiceAccess [key=" + key + ", get=" + read + ", post=" + save + ", put=" + update
				+ ", delete=" + delete + ", list=" + list + ", file=" + file + "]";
	}
}
