package sewakamera.koneksi;

import java.sql.Connection;

// Inheritance Class
// Penerapan abstract kelas Abstract
public class ID_Collection extends Abstract{
    public Connection conn = DatabaseKoneksi.koneksi();
    int id_penyewa;
    int id_kamera;
    
    //Penerapan polymorphism dengan @Override
    @Override
    public int getId_Penyewa() {
        return id_penyewa;
    }

    @Override
    public void setId_Penyewa(int id_Penyewa) {
        this.id_penyewa = id_Penyewa;
    }

    @Override
    public int getId_kamera() {
        return id_kamera;
    }

    @Override
    public void setId_kamera(int id_kamera) {
        this.id_kamera = id_kamera;
    } 
}
