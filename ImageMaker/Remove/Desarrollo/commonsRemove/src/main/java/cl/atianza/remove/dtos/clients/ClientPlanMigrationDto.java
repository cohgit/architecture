package cl.atianza.remove.dtos.clients;

import java.time.LocalDateTime;
import cl.atianza.remove.dtos.AbstractDto;
@Deprecated
public class ClientPlanMigrationDto extends AbstractDto {
	private Long id_client;
	private Long id_client_plan_from;
	private Long id_client_plan_to;
	private String scanners_to_migrate;
	private LocalDateTime creation_date;
	private LocalDateTime programmed_date;
	private LocalDateTime execution_date;
	private String payment_platform;
	private String payment_method_key;
	private boolean programmed;
	private boolean executed;
	
	public ClientPlanMigrationDto() {
		super();
	}

	public Long getId_client() {
		return id_client;
	}

	public void setId_client(Long id_client) {
		this.id_client = id_client;
	}

	public Long getId_client_plan_from() {
		return id_client_plan_from;
	}

	public void setId_client_plan_from(Long id_client_plan_from) {
		this.id_client_plan_from = id_client_plan_from;
	}

	public Long getId_client_plan_to() {
		return id_client_plan_to;
	}

	public void setId_client_plan_to(Long id_client_plan_to) {
		this.id_client_plan_to = id_client_plan_to;
	}

	public String getScanners_to_migrate() {
		return scanners_to_migrate;
	}

	public void setScanners_to_migrate(String scanners_to_migrate) {
		this.scanners_to_migrate = scanners_to_migrate;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public LocalDateTime getProgrammed_date() {
		return programmed_date;
	}

	public void setProgrammed_date(LocalDateTime programmed_date) {
		this.programmed_date = programmed_date;
	}

	public LocalDateTime getExecution_date() {
		return execution_date;
	}

	public void setExecution_date(LocalDateTime execution_date) {
		this.execution_date = execution_date;
	}

	public boolean isProgrammed() {
		return programmed;
	}

	public void setProgrammed(boolean programmed) {
		this.programmed = programmed;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public String getPayment_platform() {
		return payment_platform;
	}

	public void setPayment_platform(String payment_platform) {
		this.payment_platform = payment_platform;
	}

	public String getPayment_method_key() {
		return payment_method_key;
	}

	public void setPayment_method_key(String payment_method_key) {
		this.payment_method_key = payment_method_key;
	}

	@Override
	public String toString() {
		return "ClientPlanMigrationDto [id_client=" + id_client + ", id_client_plan_from=" + id_client_plan_from
				+ ", id_client_plan_to=" + id_client_plan_to + ", scanners_to_migrate=" + scanners_to_migrate
				+ ", creation_date=" + creation_date + ", programmed_date=" + programmed_date + ", execution_date="
				+ execution_date + ", programmed=" + programmed + ", executed=" + executed + "]";
	}
}
