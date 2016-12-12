package unlam.news;

public class Test {
	public static void main(String[] args) {

		News n = new News();

		n.setDateToNow();
		long l = n.getLong();
		System.out.println("Date1: "+n.getDate());

		n.setLong(l);	
		System.out.println("Date2: "+n.getDate());


	}

}
