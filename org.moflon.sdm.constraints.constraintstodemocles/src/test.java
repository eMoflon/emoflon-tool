
public class test {

	public static void main(String[] args) {
		
		
		
		for(int i=0;i<=10000;i++){
			char x=(char) (i%26+97);
			String s;
			if(i/26>0){
				s=x + Integer.toString(i/26);
			}else{
				s=Character.toString(x);
			}
			System.out.println(s);
		}
		
		
	}
}
