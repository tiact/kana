package com.tiact.websocket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebsocketTests {


	private static class T1 extends Thread{
		@Override
		public void run(){
			for (int i = 0; i < 10; i++) {
				try {
					TimeUnit.MICROSECONDS.sleep(1);//更加安全的使用Thread.sleep
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("T1");
			}
		}
	}

	@Test
	public void contextLoads() {
		new T1().start();//这里使用start就是启动线程
//        new T1().run();//如果使用run就只是简单的调用方法
		for(int i=0; i<10; i++) {
			try {
				TimeUnit.MICROSECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("main");
		}
	}

}
