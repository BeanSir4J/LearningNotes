public class Dijkstra {
	
	
	//open���ڴ洢δ�����Ľڵ�
	Set<Node> open=new HashSet<Node>();
	//close�����洢�ѱ����Ľڵ�
	Set<Node> close=new HashSet<Node>();
	//��װ·������
	Map<String,Integer> path=new HashMap<String,Integer>();
	//��װ·����Ϣ
	Map<String,String> pathInfo=new HashMap<String,String>();
	
	
	
	public Node init(){
		//��ʼ·��,��û��A->E����·��,����path(E)����ΪInteger.MAX_VALUE
		path.put("B", 1);
		pathInfo.put("B", "A->B");
		path.put("C", 1);
		pathInfo.put("C", "A->C");
		path.put("D", 4);
		pathInfo.put("D", "A->D");
		//��ΪE û�к�A�ڵ�ֱ�����ӣ����и���һ�����ֵ
		path.put("E", Integer.MAX_VALUE);
		pathInfo.put("E", "A");
		path.put("F", 2);
		pathInfo.put("F", "A->F");
		path.put("G", 5);
		pathInfo.put("G", "A->G");
		path.put("H", Integer.MAX_VALUE);
		pathInfo.put("H", "A");
		//����ʼ�ڵ����close,�����ڵ����open
		Node start=new MapBuilder().build(open,close);
		//�����Ա����ڵ�nodeA
		return start;
	}
	
	
	
	
	/**
	 * ��nodeA�ڵ�  ��������
	 * @param start
	 */
	public void computePath(Node start){
		//��ȡ�������һ���ڵ�
		Node nearest=getShortestPath(start);
		if(nearest==null){
			return;
		}
		//�����Ա�����
		close.add(nearest);
		//�Ƴ������б�
		open.remove(nearest);
		//��ȡ������ڵ��ȫ�����ӵ�
		Map<Node,Integer> childs=nearest.getChild();
		//ѭ������ڵ���������ӵ�
		for(Node child:childs.keySet()){
			//���ѭ������A�ڵ������ڵ��������ʱ
			if(open.contains(child)){
				//��ȡ������ڵ������
				Integer newCompute=path.get(nearest.getName())+childs.get(child);	//C��G�ڵ�ľ���
				if(path.get(child.getName())>newCompute){//֮ǰ���õľ�������¼�������ľ���
					path.put(child.getName(), newCompute);
					pathInfo.put(child.getName(), pathInfo.get(nearest.getName())+"->"+child.getName());
				}
			}
		}
		computePath(start);//�ظ�ִ���Լ�,ȷ�������ӽڵ㱻����
		computePath(nearest);//����һ���ݹ�,ֱ�����ж��㱻����
	}
	
	
	
	
	public void printPathInfo(){
		Set<Map.Entry<String, String>> pathInfos=pathInfo.entrySet();
		for(Map.Entry<String, String> pathInfo:pathInfos){
			System.out.println(pathInfo.getKey()+":"+pathInfo.getValue());
		}
	}
	/**
	 * ��ȡ��node������ӽڵ�
	 */
	private Node getShortestPath(Node node){
		//��һ��node�����flag
		Node res=null;
		//��Ϊһ������flag
		int minDis=Integer.MAX_VALUE;
		//��ȡ��nodeA �ڵ�������������ӵ�
		Map<Node,Integer> childs=node.getChild();
		//ѭ����Щ�ӽڵ㡣
		for(Node child:childs.keySet()){
			//�����ʼ���Ľڵ��У�������nodeA �ڵ�����ӵ�ʱ
			if(open.contains(child)){
				//�ö����еĵ�ǰ ���������ӵ������
				int distance=childs.get(child);
				//ð������
				if(distance<minDis){
					//
					minDis=distance;
					res=child;
				}
			}
		}
		//���ؼ����С��nodel����
		return res;
	}
}