package arbol;


import java.util.List;

import org.zkoss.zul.AbstractTreeModel;

public class MArbol extends AbstractTreeModel<Object> {
	
	private Nodos _root;
    public MArbol(Object root) {
        super(root);
        _root = (Nodos)root;
    }
    public boolean isLeaf(Object node) {
        return ((Nodos)node).getChildren().size() == 0; 
    }
    public Object getChild(Object parent, int index) {
        return ((Nodos)parent).getChildren().get(index);
    }
    public int getChildCount(Object parent) {
        return ((Nodos)parent).getChildren().size();
    }
    public int getIndexOfChild(Object parent, Object child) {
    	List<Nodos> children = ((Nodos)parent).getChildren();
    	for (int i = 0; i < children.size(); i++) {
    		if (children.get(i).equals(children))
    			return i;
    	}
        return -1;
    }
};