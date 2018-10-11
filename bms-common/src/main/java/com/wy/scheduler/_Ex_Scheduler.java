package com.wy.scheduler;

import org.springframework.stereotype.Component;

/**
 * 定时任务的使用示例
 * @author wanyang 2018年7月24日
 *
 */
@Component
public class _Ex_Scheduler {

	/**
	 * 直接将该类纳入到spring的管理中,然后使用@Scheduled注解,写表达式
	 */
//	@Scheduled(fixedRate=6000)
	public void scheduler1() {
		System.out.println(System.currentTimeMillis());
	}
}