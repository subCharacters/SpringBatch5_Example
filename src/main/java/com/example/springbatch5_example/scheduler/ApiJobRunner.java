package com.example.springbatch5_example.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ApiJobRunner extends JobRunner {

    private final Scheduler scheduler;

    public ApiJobRunner(Scheduler scheduler) {
        super(scheduler);
        this.scheduler = scheduler;
    }

    @Override
    public void doRun(ApplicationArguments args) {
        // 여기서 전달하는 그룹은 apiJob이 속한 그룹.
        // 패키지는 아니고 job을 구분하기 위해 설정하는 일종의 키 역할도 겸한다.
        JobDetail jobDetail = buildJobDetail(ApiSchJob.class, "apiJob", "batch", new HashMap());

        // ┌───────────── 초 (0~59)
        // │ ┌───────────── 분 (0~59)
        // │ │ ┌───────────── 시 (0~23)
        // │ │ │ ┌───────────── 일 (1~31)
        // │ │ │ │ ┌───────────── 월 (1~12)
        // │ │ │ │ │ ┌───────────── 요일 (1~7 or SUN~SAT)
        // │ │ │ │ │ │
        //  - 초: 0부터 시작해서 30초마다 (0, 30초)
        //  - 분: 매 분
        //  - 시: 매 시각
        //  - 일: 매일
        //  - 월: 매월
        //  - 요일: 무시 (일 필드를 사용하므로 ?)
        // 30초마다 트리거 작동
        Trigger trigger = buildJobTrigger("0/30 * * * * ?");

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
