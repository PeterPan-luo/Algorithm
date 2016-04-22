package queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
	
	public class Test {
	
		public static void main(String[] args) {
			final SynchronousQueue<String> queue = new SynchronousQueue<>();
//			final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
			final Semaphore semaphore = new Semaphore(1);
			for(int i = 0; i < 10; i++)
			{
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							semaphore.acquire();
							String input = queue.take();
							String output = TestDo.doSome(input);
							System.out.println(Thread.currentThread().getName()+ ":" + output);
							semaphore.release();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
			System.out.println("begin:"+(System.currentTimeMillis()/1000));
			for(int i=0;i<10;i++){  //这行不能改动
				String input = i+"";  //这行不能改动
				try {
					queue.put(input);
//					queue.offer(input);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//不能改动此TestDo类
	class TestDo {
		public static String doSome(String input){
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String output = input + ":"+ (System.currentTimeMillis() / 1000);
			return output;
		}
	}