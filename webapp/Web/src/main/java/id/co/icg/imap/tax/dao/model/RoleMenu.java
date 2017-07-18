package id.co.icg.imap.tax.dao.model;

public class RoleMenu implements java.io.Serializable {

    private Integer id;
    private String menuId;
    
    public RoleMenu() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}
