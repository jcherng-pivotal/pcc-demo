package io.pivotal.demo.pcc.loader.scheduling;

import io.pivotal.demo.pcc.loader.service.CustomerLoadService;
import io.pivotal.demo.pcc.loader.service.CustomerOrderLoadService;
import io.pivotal.demo.pcc.loader.service.ItemLoadService;
import io.pivotal.demo.pcc.loader.service.PagingLoadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Slf4j
@AllArgsConstructor
@Component
public class LoadServiceScheduler {
    private final CustomerLoadService customerLoadService;
    private final CustomerOrderLoadService customerOrderLoadService;
    private final ItemLoadService itemLoadService;

    @Async
    @Scheduled(cron = "0 0 0 * * *", zone = "US/Central")
    @Transactional
    public void loadAll() {
        log.info("loadAll()");
        StopWatch stopWatch = new StopWatch();
        trackLoad(customerLoadService, stopWatch);
        trackLoad(customerOrderLoadService, stopWatch);
        trackLoad(itemLoadService, stopWatch);
        report(stopWatch);
    }

    private void trackLoad(PagingLoadService loadService, StopWatch stopWatch) {
        stopWatch.start(loadService.getClass().getSimpleName() + ": load()");
        log.info(stopWatch.currentTaskName());
        loadService.load();
        stopWatch.stop();
    }

    private void report(StopWatch stopWatch) {
        log.info("1. Pretty Print Result: " + stopWatch.prettyPrint());
        log.info("2. Short Summary: " + stopWatch.shortSummary());
        log.info("3. Total Task Count: " + stopWatch.getTaskCount());
    }
}
