package sewakamera.koneksi;

public class User{
    private int id_user;
    private String nama_user;
    private String username;
    private String password;
    private String akses;
    
    public User(){
        
    }
    
    public User(int id_user, String nama_user, String username, String password, String akses){
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.username = username;
        this.password = password;
        this.akses = akses;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAkses() {
        return akses;
    }

    public void setAkses(String akses) {
        this.akses = akses;
    }
}
