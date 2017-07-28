package crawler.life.aia;

public class PrinDivisor {

	public static void main(String[] args) {

	        int num =123123124;
	        int a;
	 
	        System.out.println(num+"의약수");
	        
	        for(a=1; a<=num; a++){
	            if((num%a)==0){
	                System.out.println(a);
	                
	        }
	    };
		/*String someString = "abcdefghijklmn";
		
		char[] arr = someString.toCharArray(); // 해당 문자열로부터 캐릭터 배열을 선언한다
		StringBuffer sb = new StringBuffer();
		int size = 0;
		for(char c : arr) {
		    size += (c > 5) ? 2 : 1; // 조건에 따라 2 또는 1을 증가시킨다
		    System.out.println("c=" + c);
		    System.out.println("size=" + size);
		    sb.append(c);
		    if(size >=3) {
		        break;
		    }
		}
		System.out.println(sb.toString());
*/
	}

}
