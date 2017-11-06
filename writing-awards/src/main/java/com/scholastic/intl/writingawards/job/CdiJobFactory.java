/**
 * 
 */
package com.scholastic.intl.writingawards.job;

/**
 * @author madhusmita.nayak
 *
 */
import org.jboss.solder.beanManager.BeanManagerAware;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Named;
 
 
@Named
public class CdiJobFactory extends BeanManagerAware implements JobFactory {
 
   private final Logger log = LoggerFactory.getLogger(getClass());
 
   protected Logger getLog() {
      return log;
   }
 
   public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler) throws SchedulerException {
 
      final JobDetail jobDetail = bundle.getJobDetail();
      final Class<? extends Job> jobClass = jobDetail.getJobClass();
      try {
         if (log.isDebugEnabled()) {
            log.debug(
                  "Producing instance of Job '" + jobDetail.getKey() +
                        "', class=" + jobClass.getName());
         }
 
         return getBean(jobClass);
      } catch (Exception e) {
         throw new SchedulerException(
               "Problem instantiating class '"
                     + jobDetail.getJobClass().getName() + "'", e);
      }
   }
 
   private Job getBean(Class jobClazz) {
      final BeanManager bm = getBeanManager();
      final Bean<?> bean =  bm.getBeans(jobClazz).iterator().next();
      final CreationalContext<?> ctx = bm.createCreationalContext(bean);
      return (Job) bm.getReference(bean, jobClazz, ctx);
   }
 
}

