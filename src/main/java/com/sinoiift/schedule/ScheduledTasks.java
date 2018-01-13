package com.sinoiift.schedule;

import com.sinoiift.comm.aop.LoggerManage;
import com.sinoiift.domain.view.IndexCollectorView;
import com.sinoiift.service.RedisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private RedisService redisService;


	@Scheduled(cron="11 11 0 * * ?")
	@LoggerManage(description="查询收藏夹放到缓存定时")
	public void putRedisCollector() {
		try {
			IndexCollectorView indexCollectorView = new IndexCollectorView();

			redisService.setObject("collector", indexCollectorView);
		}catch (Exception e){
			logger.error("查询收藏夹放到缓存定时任务异常：",e);
		}
	}

}
