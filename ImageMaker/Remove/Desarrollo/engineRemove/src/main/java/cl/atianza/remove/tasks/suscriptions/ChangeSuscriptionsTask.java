package cl.atianza.remove.tasks.suscriptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.daos.client.ClientPlanMigrationDao;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.helpers.SuscriptionHelper;
import cl.atianza.remove.models.client.ClientPlanMigration;
import cl.atianza.remove.utils.RemoveDateUtils;

/**
 * Check every scanner to be executed and schedule it.
 * @author pilin
 *
 */
public class ChangeSuscriptionsTask extends TimerTask {
	private Logger log = LogManager.getLogger(ChangeSuscriptionsTask.class);
	
	public ChangeSuscriptionsTask() {
		super();
	}

	@Override
	public void run() {
		log.info("Starting Change Suscription Task Manager at:" + RemoveDateUtils.nowLocalDateTime());
		
		try {
			List<ClientPlanMigration> list = ClientPlanMigrationDao.init().listProgrammedNotExecuted();
			
			if (list != null) {
				log.info("Total Subscriptions to change founds to execute: " + list.size());
				SuscriptionHelper helper = SuscriptionHelper.init();
				list.forEach(migration -> {
					if (migration.getProgrammed_date() != null && migration.getProgrammed_date().isBefore(LocalDateTime.now())) {
						try {
							helper.changePlan(migration);
						} catch (RemoveApplicationException e) {
							log.error("Error changing plan: " + migration, e);
						}
					} else {
						log.error("Migration doesn't have a programmed date or is not executable yet... " + migration.getId() + " - " + migration.getProgrammed_date());		
					}
				});
			} else {
				log.info("There's no subscriptions changes to verify...");
			}
		} catch (RemoveApplicationException e) {
			log.error("Error scheduling tasks: ", e);
		}
		
		log.info("Change Subscription Task Manager Execution Finished at: " + RemoveDateUtils.nowLocalDateTime());
	}
}
