package cn.itcast.fastdfs.demo;

public class GoToDemo {
	
	public static void main(String[] args) {
		
		//testBreak();
		
		//testBreakAndLable();
		
		//testContinue();
		
		testContinueAndLable();
	}

	private static void testContinueAndLable() {
		{
			label1:
			    for (int i = 0; i < 2; i++) {
			        System.out.println("L1----"+i);
			        for (int j = 0; j < 4; j++) {
			            if (j == 2) {
			                continue label1;
			            }
			            System.out.println("--------L2---"+j);
			        }
			    }
		}
		/**
		 * continue中断掉内部的for循环后继续执行跳到标签label1处的外部for循环，继续执行
		 * L1----0
			--------L2---0
			--------L2---1
			L1----1
			--------L2---0
			--------L2---1
		 */
		
	}

	private static void testContinue() {
		for (int i = 0; i < 2; i++) {
            System.out.println("L1----"+i);
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    continue;
                }
                System.out.println("--------L2---"+j);
            }
        }
		/**
		 * continue中断掉内部的for循环后继续执行内部for循环。
		 * L1----0
			--------L2---0
			--------L2---1
			--------L2---3
			L1----1
			--------L2---0
			--------L2---1
			--------L2---3
		 */
		
	}

	private static void testBreakAndLable() {
		label1:
		    for (int i = 0; i < 2; i++) {
		        System.out.println("L1----"+i);
		        for (int j = 0; j < 4; j++) {
		            if (j == 2) {
		                break label1;
		            }
		            System.out.println("--------L2---"+j);
		        }
		    }
	
		/**
		 * Break+Lable break中断标签label1处的外部for循环。
		 * L1----0
			--------L2---0
			--------L2---1
		 */
		
	}

	private static void testBreak() {
		for (int i = 0; i < 2; i++) {
	        System.out.println("L1----"+i);
	        for (int j = 0; j < 4; j++) {
	            if (j == 2) {
	                break;
	            }
	            System.out.println("--------L2---"+j);
	        }
	    }
		/**
		 * break终止内部循环
		 * L1----0
			--------L2---0
			--------L2---1
			L1----1
			--------L2---0
			--------L2---1
		 */
		
	}
}
