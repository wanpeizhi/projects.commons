package com.chengjia.commons.utils;

import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 任务工具类
 * 
 * @author Administrator
 *
 */
public class QuartzUtils {
	private final Logger logger = LogManager.getLogger(getClass());
	private static final String JOB = "job";
	private String path = QuartzUtils.class.getResource("/").getPath();
	private String fileName = "job.properties";
	private Properties properties;
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	private Scheduler scheduler;

	public QuartzUtils() {
	}

	public QuartzUtils(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;
	}

	public static Scheduler getScheduler() {
		try {
			return schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 启动定时任务
	 */
	public boolean start() {
		try {
			properties = PropertiesUtils.loadProperty(path, fileName);
			if (properties == null) {
				logger.error("任务属性文件加载失败!");
				return false;
			}

			if (scheduler == null) {
				scheduler = getScheduler();
				if (scheduler == null) {
					logger.error("获取scheduler失败!");
					return false;
				}
			}

			if (!properties.isEmpty()) {
				Set<Object> keys = properties.keySet();
				for (Object keyObj : keys) {
					String key = keyObj.toString();
					String jobPrefix = key.substring(0, key.lastIndexOf("."));
					if (key.toLowerCase().endsWith(JOB) && isEnable(properties.getProperty(jobPrefix + ".enable"))) {
						String jobClassName = properties.getProperty(key);
						String jobCronExp = properties.getProperty(jobPrefix + ".cron");
						String jobGroup = properties.getProperty((jobPrefix + ".group"), "default");
						String id = properties.getProperty(jobPrefix + ".id",
								String.valueOf(System.currentTimeMillis()));
						String priority = properties.getProperty(jobPrefix + ".priority",
								String.valueOf(Trigger.DEFAULT_PRIORITY));
						String startTime = properties.getProperty(jobPrefix + ".startTime",
								DateUtils.getCurDatetimeString());
						String endTime = properties.getProperty(jobPrefix + ".endTime");

						Class<? extends Job> jobClass = null;
						jobClass = (Class<? extends Job>) Class.forName(jobClassName);

						JobDetail job = JobBuilder.newJob(jobClass).withIdentity(id, jobGroup).build();
						Trigger trigger;
						TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(id, jobGroup);
						if (StringUtils.isNotBlank(startTime)) {
							triggerBuilder.startAt(DateUtils.stringToDate(startTime));
						}
						if (StringUtils.isNotBlank(endTime)) {
							triggerBuilder.endAt(DateUtils.stringToDate(endTime));
						}

						trigger = triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(jobCronExp))
								.withPriority(Integer.valueOf(priority)).build();
						scheduler.scheduleJob(job, trigger);
					}
				}
			}

			if (!scheduler.isShutdown() && !scheduler.isStarted()) {
				scheduler.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("任务调度启动失败!" + e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * 停止定时任务
	 */
	public boolean stop() {
		try {
			if (scheduler.isStarted())
				scheduler.shutdown();
		} catch (SchedulerException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 判断任务是否启用
	 * 
	 * @param key
	 * @return
	 */
	public boolean isEnable(String key) {
		Object enable = properties.get(key);
		if (enable != null && "false".equalsIgnoreCase((enable + "").trim())) {
			return false;
		}
		return true;
	}

	/**
	 * 添加任务
	 * 
	 * @param jobDetail
	 * @param trigger
	 * @return
	 */
	public boolean addJob(JobDetail jobDetail, Trigger trigger) {
		try {
			scheduler.scheduleJob(jobDetail, trigger);
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	/**
	 * 删除任务
	 * 
	 * @param tk
	 */
	public boolean removeJob(TriggerKey tk) {
		try {
			scheduler.pauseTrigger(tk);// 停止触发器
			scheduler.unscheduleJob(tk);// 移除触发器
			scheduler.deleteJob(new JobKey(tk.getName()));// 删除任务
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	/**
	 * 暂停任务
	 * 
	 * @param tk
	 */
	public boolean pauseJob(TriggerKey tk) {
		try {
			scheduler.pauseTrigger(tk);// 暂停
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

}
