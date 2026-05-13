
package Proyecto.modelo;
import Proyecto.interfaces.Autenticable;

public abstract class  Usuario implements Autenticable {
    protected String username;
    protected String password;
    protected String rol;
    
    public Usuario(String username, String password, String rol){
        this.username=username;
        this.password=password;
        this.rol=rol;
        
    }
    @Override
    public boolean login(String Username, String Password){
        return this.username.equals(username) && this.password.equals(password);
    }
    public String getUsername() {
        return this.username;
    }
    public String getRol(){
        return this.rol;
    }
           
}
