package it.sincrono;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {

	@Autowired
	private DeleteRecord deleteRecord;

	private final JobLauncher jobLauncher;
	private final Job job;

	public BatchScheduler(JobLauncher jobLauncher, Job job) {
		this.jobLauncher = jobLauncher;
		this.job = job;
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void runJob() throws JobExecutionException {
		JobParameters jobParameters = new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();

		jobLauncher.run(job, jobParameters);
	}

	@Scheduled(cron = "0 * * * * ?")
	public void deleteAllRecordsJob() {
		deleteRecord.deleteAllRecords();
	}
}
