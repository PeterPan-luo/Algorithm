package com.javase.tree;
/**
 * 二叉查找树
 * @author Administrator
 *
 */
public class BSTree<T extends Comparable<T>> {

	//树节点的结构体
	public class BSNode<T extends Comparable<T>>{
		T key;  //关键字(键值)
		BSNode<T> left;  //左孩子
		BSNode<T> right;  //右孩子
		BSNode<T> parent;  //父节点
		public BSNode(T key, BSNode<T> left, BSNode<T> right, BSNode<T> parent) {
			this.key = key;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
		public T getKey() {
			return key;
		}
	}
	private BSNode<T> mRoot;  //根节点
	public BSTree() {
		mRoot = null;
	}
	/**
	 * 前序遍历
	 */
	private void preOrder(BSNode<T> tree) {
		if (tree != null) {
			System.out.println(tree.key);
			preOrder(tree.left);
			preOrder(tree.right);
		}
	}
	public void preOrder() {
		preOrder(mRoot);
	}
	/**
	 * 中序遍历
	 * @param tree
	 */
	private void inOrder(BSNode<T> tree) {
		if (tree != null) {
			inOrder(tree.left);
			System.out.println(tree.key);
			inOrder(tree.right);
		}
	}
	public void inOrder() {
		inOrder(mRoot);
	}
	/**
	 * 后序遍历
	 * @param tree
	 */
	private void postOrder(BSNode<T> tree) {
		if (tree != null) {
			postOrder(tree.left);
			postOrder(tree.right);
			System.out.println(tree.key);
		}
	}
	public void postOrder() {
		postOrder(mRoot);
	}
	/**
	 * 查找二叉树中值为key的节点(递归实现)
	 * @param tree
	 * @param key
	 * @return
	 */
	private BSNode<T> search(BSNode<T> tree, T key) {
		if(tree == null)
			return null;
		
		int cmp = tree.key.compareTo(key);
		if(cmp < 0)
			return search(tree.right, key);
		else if(cmp > 0)
			return search(tree.right, key);
		else
			return tree;
	}
	
	public BSNode<T> search(T key) {
		return search(mRoot, key);
	}
	
	/**
	 * 查找二叉树中值为key的节点(非递归实现)
	 * @param tree
	 * @param key
	 * @return
	 */
	private BSNode<T> iterativeSearch(BSNode<T> tree, T key) {
		BSNode<T> pNode = tree;
		int cmp;
		while (pNode != null) {
			cmp = pNode.key.compareTo(key);
			if(cmp < 0)
				pNode = pNode.right;
			else if (cmp > 0) 
				pNode = pNode.left;
			else 
				return pNode;
		}
		return null;
	}
	
	public BSNode<T> iterativeSearch(T key) {
		return iterativeSearch(mRoot,key);
	}
	/**
	 * 查找二叉树最小节点
	 * @param tree
	 * @return
	 */
	private BSNode<T> minimun(BSNode<T> tree) {
		if(tree == null)
			return null;
		BSNode<T> pNode = tree;
		while (pNode.left != null) 
			pNode = pNode.left;
		return pNode;
	}
	public T minimum() {
		BSNode<T> pNode = minimun(mRoot);
		if(pNode != null)
			return pNode.key;
		return null;
	}
	/**
	 * 查找二叉树最大的节点
	 * @param tree
	 * @return
	 */
	private BSNode<T> maximum(BSNode<T> tree) {
		if(tree == null)
			return null;
		BSNode<T> pNode = tree;
		while (pNode.right != null) 
			pNode = pNode.right;
		return pNode;
	}
	public T maximum() {
		BSNode<T> pNode =maximum(mRoot);
		if(pNode != null)
			return pNode.key;
		return null;
	}
	/**
	 * 找结点(x)的前驱结点。即，查找"二叉树中数据值小于该结点"的"最大结点"。
	 * @param x
	 * @return
	 */
	public BSNode<T> successor(BSNode<T> x) {
		// 如果x存在左孩子，则"x的前驱结点"为 "以其左孩子为根的子树的最大结点"。
		if(x.left != null)
			return maximum(x);
		
		// 如果x没有右孩子。则x有以下两种可能：
        // (01) x是"一个左孩子"，则"x的后继结点"为 "它的父结点"。
        // (02) x是"一个右孩子"，则查找"x的最低的父结点，并且该父结点要具有左孩子"，找到的这个"最低的父结点"就是"x的后继结点"。
		BSNode<T> y = x.parent;
        while ((y!=null) && (x==y.right)) {
            x = y;
            y = y.parent;
        }

        return y;
	}
	
	/* 
     * 找结点(x)的前驱结点。即，查找"二叉树中数据值小于该结点"的"最大结点"。
     */
    public BSNode<T> predecessor(BSNode<T> x) {
        // 如果x存在左孩子，则"x的前驱结点"为 "以其左孩子为根的子树的最大结点"。
        if (x.left != null)
            return maximum(x.left);

        // 如果x没有左孩子。则x有以下两种可能：
        // (01) x是"一个右孩子"，则"x的前驱结点"为 "它的父结点"。
        // (01) x是"一个左孩子"，则查找"x的最低的父结点，并且该父结点要具有右孩子"，找到的这个"最低的父结点"就是"x的前驱结点"。
        BSNode<T> y = x.parent;
        while ((y!=null) && (x==y.left)) {
            x = y;
            y = y.parent;
        }

        return y;
    }
    /**
     * 将节点插入二叉树
     * @param tree -- 要插入的二叉树
     * @param node -- 要插入的节点
     */
    private void insert(BSTree<T> tree, BSNode<T> newNode) {
		int cmp;
		BSNode<T> parentNode = null;
		BSNode<T> pNode = tree.mRoot; 
		//查找新节点要插入的位置
		while (pNode != null) {
			parentNode = pNode;
			cmp = newNode.key.compareTo(pNode.key);
			if(cmp < 0)//新插入的节点值小
				pNode = pNode.left;
			else 
				pNode = pNode.right;
		}
		newNode.parent = parentNode;
		if(parentNode == null)
			tree.mRoot = newNode;
		else {
			cmp = parentNode.key.compareTo(newNode.key);
			if(cmp <= 0)//插入的值大
				parentNode.right = newNode;
			else
				parentNode.left = newNode;
		}
	}
    /**
     * 新建节点，并将其插入树中
     * @param key
     */
    public void insert(T key) {
		BSNode<T> node = new BSNode<T>(key, null, null, null);
		insert(this, node);
	}
    /**
     * 删除节点，并返回被删除的节点说
     * @param tree
     * @param delNode
     * @return
     */
    public BSNode<T> remove(BSTree<T> tree, BSNode<T> delNode) {
    	//1. 需要删除的节点下并没有其它节点，直接删除即可
    	//2. 需要删除的节点下有一个子节点（左节点或右节点），
    	//3. 需要删掉的节点下有2个子节点，在需要删除的节点的右子树中找到最小的子节点（后继节点）
    	//用找到的最小值与需要删的的节点进行替换。然后，再删除最小值节点
    	
    	BSNode<T> x =null;//要删除节点的子节点
    	BSNode<T> y = null; //记录要处理子树的节点（即如果删除delNode后，要对它的子树进行处理）
    	if(delNode.left != null || delNode.right != null)
    		y = delNode; //情况2.
    	else
    		y = successor(delNode); //情况3
    	//查找要删除节点的子节点
    	if(y.left != null)
    		x = y.left;
    	else
    		x = y.right;
    	//子树的处理
    	if(x != null)
    		x.parent = y.parent;
    	if(y == y.parent.left)
    		y.parent.left = x;
    	else
    		y.parent.right = x;
    	//删除delNode节点,即将节点值替换
    	if(delNode != y)
    		delNode.key = y.key;
		return y;
	}
    
    /**
     * 删除节点,并返回被删除的节点
     * @param key
     */
    public BSNode<T> remove(T key) {
		BSNode<T> z, node;
		if((z = search(mRoot, key)) != null)
			if((node = remove(this, z)) !=null)
				return node;
		return null;
	}
    /**
     * 打印二叉查找树
     * @param key -- 节点的键值
     * @param direction -- 0，表示该节点是根节点
     * 					   1，右孩子
     * 					  -1，左孩子 
     */
    private void print(BSNode<T> tree, T key, int direction) {
		if (tree != null) {
			if(direction == 0)
				System.out.printf("%2d is root\n", tree.key);
			else
				System.out.printf("%2d is %2d's %6s child \n", tree.key, key, direction == 1?"right":"left");
			print(tree.left, tree.key, -1);
			print(tree.right, tree.key, 1);
		}
	}
    public void print() {
		if (mRoot != null) {
			print(mRoot, mRoot.key, 0);
		}
	}
    /**
     * 销毁二叉树
     * @param tree
     */
    private void destroy(BSNode<T> tree) {
		if(tree == null)
			return;
		if(tree.left !=null)
			destroy(tree.left);
		if(tree.right != null)
			destroy(tree.right);
		tree = null;
	}
    public void clear() {
		if(mRoot != null)
			destroy(mRoot);
	}
    
    public static void main(String[] args) {
		
	}
}
