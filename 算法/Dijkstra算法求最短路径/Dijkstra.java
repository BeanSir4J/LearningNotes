public class Dijkstra {
	
	
	//open用于存储未遍历的节点
	Set<Node> open=new HashSet<Node>();
	//close用来存储已遍历的节点
	Set<Node> close=new HashSet<Node>();
	//封装路径距离
	Map<String,Integer> path=new HashMap<String,Integer>();
	//封装路径信息
	Map<String,String> pathInfo=new HashMap<String,String>();
	
	
	
	public Node init(){
		//初始路径,因没有A->E这条路径,所以path(E)设置为Integer.MAX_VALUE
		path.put("B", 1);
		pathInfo.put("B", "A->B");
		path.put("C", 1);
		pathInfo.put("C", "A->C");
		path.put("D", 4);
		pathInfo.put("D", "A->D");
		//因为E 没有和A节点直接连接，所有给他一个最大值
		path.put("E", Integer.MAX_VALUE);
		pathInfo.put("E", "A");
		path.put("F", 2);
		pathInfo.put("F", "A->F");
		path.put("G", 5);
		pathInfo.put("G", "A->G");
		path.put("H", Integer.MAX_VALUE);
		pathInfo.put("H", "A");
		//将初始节点放入close,其他节点放入open
		Node start=new MapBuilder().build(open,close);
		//返回以遍历节点nodeA
		return start;
	}
	
	
	
	
	/**
	 * 将nodeA节点  传入其中
	 * @param start
	 */
	public void computePath(Node start){
		//获取到最近的一个节点
		Node nearest=getShortestPath(start);
		if(nearest==null){
			return;
		}
		//存入以遍历中
		close.add(nearest);
		//移除遍历列表
		open.remove(nearest);
		//获取到最近节点的全部连接点
		Map<Node,Integer> childs=nearest.getChild();
		//循环最近节点的所有连接点
		for(Node child:childs.keySet()){
			//如果循环到了A节点的最近节点的连接线时
			if(open.contains(child)){
				//获取到这个节点的连接
				Integer newCompute=path.get(nearest.getName())+childs.get(child);	//C到G节点的距离
				if(path.get(child.getName())>newCompute){//之前设置的距离大于新计算出来的距离
					path.put(child.getName(), newCompute);
					pathInfo.put(child.getName(), pathInfo.get(nearest.getName())+"->"+child.getName());
				}
			}
		}
		computePath(start);//重复执行自己,确保所有子节点被遍历
		computePath(nearest);//向外一层层递归,直至所有顶点被遍历
	}
	
	
	
	
	public void printPathInfo(){
		Set<Map.Entry<String, String>> pathInfos=pathInfo.entrySet();
		for(Map.Entry<String, String> pathInfo:pathInfos){
			System.out.println(pathInfo.getKey()+":"+pathInfo.getValue());
		}
	}
	/**
	 * 获取与node最近的子节点
	 */
	private Node getShortestPath(Node node){
		//做一个node对象的flag
		Node res=null;
		//作为一个数据flag
		int minDis=Integer.MAX_VALUE;
		//获取到nodeA 节点下面的所有连接点
		Map<Node,Integer> childs=node.getChild();
		//循环这些子节点。
		for(Node child:childs.keySet()){
			//如果初始化的节点中，包含了nodeA 节点的连接点时
			if(open.contains(child)){
				//得多所有的当前 到所有连接点的数据
				int distance=childs.get(child);
				//冒泡排序
				if(distance<minDis){
					//
					minDis=distance;
					res=child;
				}
			}
		}
		//返回间距最小的nodel对象
		return res;
	}
}