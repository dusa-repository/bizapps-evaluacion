package modelo.pk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rol_menu database table.
 * 
 */
@Embeddable
public class RolMenuPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_rol")
	private int idRol;

	@Column(name="id_menu")
	private int idMenu;

	public RolMenuPK() {
	}
	public int getIdRol() {
		return this.idRol;
	}
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	public int getIdMenu() {
		return this.idMenu;
	}
	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RolMenuPK)) {
			return false;
		}
		RolMenuPK castOther = (RolMenuPK)other;
		return 
			(this.idRol == castOther.idRol)
			&& (this.idMenu == castOther.idMenu);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idRol;
		hash = hash * prime + this.idMenu;
		
		return hash;
	}
}