package pracownik;

public class Doradca {
	
	private int dbid;
	private String name;
	
	public Doradca(String name, int dbid) {
		this.dbid = dbid;
		this.name = name;
	}
	
	public int getDbid() {
		return dbid;
	}
	public void setDbid(int dbid) {
		this.dbid = dbid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
