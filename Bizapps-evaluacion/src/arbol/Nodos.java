package arbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Nodos {

	private Nodos _parent;
	private List<Nodos> _children;
	private int _index;
	private String _label = "";
    private String _ficha = "";

    public Nodos(Nodos parent, int index, String label, String ficha) {
            _parent = parent;
            _index = index;
            _label = label;
            _ficha = ficha;
            
    }
    
    public String getFicha() {
            return _ficha;
    }

    public String toString2() {
            return getFicha();
    }

	public void setParent(Nodos parent) {
		_parent = parent;
	}

	public Nodos getParent() {
		return _parent;
	}

	public void appendChild(Nodos child) {
		if (_children == null)
			_children = new ArrayList();
		_children.add(child);
	}

	public List<Nodos> getChildren() {
		if (_children == null)
			return Collections.EMPTY_LIST;
		return _children;
	}

	public void setIndex(int index) {
		_index = index;
	}

	public int getIndex() {
		return _index;
	}

	public String getLabel() {
		return _label;
	}

	public String toString() {
		return getLabel();
	}
}