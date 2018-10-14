public class Main {
	public static void main(String[] args) {
		Dijkstra test=new Dijkstra();
		Node start=test.init();
		test.computePath(start);
		test.printPathInfo();
	}
}