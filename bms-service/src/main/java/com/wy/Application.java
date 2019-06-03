//package com.wy;
//
//import java.awt.EventQueue;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import com.wy.common.Props;
//import com.wy.frame.LoginFrm;
//
///**
// * 启动gui页面之前启动spring,可以同时让web和gui访问数据库数据
// * EventQueue是事件派发线程的使用,跟android的非主线程不可更改主线程界面差不多
// * 是为了防止耗时程序阻止主界面绘制而使用
// * @author paradiseWy
// */
//@SpringBootApplication
//public class Application {
//	
//	public static void main(String[] args) {
//		if(Props.USE_GUI == 1) {
//			EventQueue.invokeLater(new Runnable() {
//				
//				@Override
//				public void run() {
//					SpringApplication.run(Application.class, args);
//					LoginFrm loginFrm = new LoginFrm();
//					loginFrm.setVisible(true);
//				}
//			});
//		}else {
//			SpringApplication.run(Application.class, args);
//		}
//	}
//}