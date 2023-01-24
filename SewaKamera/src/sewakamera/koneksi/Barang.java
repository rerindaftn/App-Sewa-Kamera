package sewakamera.koneksi;

//Penerapan inheritance kelas ID_Collection
public class Barang extends ID_Collection{
    private String jenis_kamera;
    private String lensa;
    private String memori;
    private String jenis_aksesoris;
    private int harga_sewa;
    
    public Barang(){
        
    }
    
    public Barang(int id_Kamera, String jenis_kamera, String lensa, String memori, String jenis_aksesoris, int harga_sewa){
        this.id_kamera = id_Kamera;
        this.jenis_kamera = jenis_kamera;
        this.lensa = lensa;
        this.memori = memori;
        this.jenis_aksesoris = jenis_aksesoris;
        this.harga_sewa = harga_sewa;
    }
    
    //Penerapan polymorphism dengan @Override
    @Override
    public int getId_kamera() {
        return id_kamera;
    }
    
    @Override
    public void setId_kamera(int id_kamera) {
        this.id_kamera = id_kamera;
    }

    public String getJenis_kamera() {
        return jenis_kamera;
    }

    public void setJenis_kamera(String jenis_kamera) {
        this.jenis_kamera = jenis_kamera;
    }

    public String getLensa() {
        return lensa;
    }

    public void setLensa(String lensa) {
        this.lensa = lensa;
    }

    public String getMemori() {
        return memori;
    }

    public void setMemori(String memori) {
        this.memori = memori;
    }

    public String getJenis_aksesoris() {
        return jenis_aksesoris;
    }

    public void setJenis_aksesoris(String jenis_aksesoris) {
        this.jenis_aksesoris = jenis_aksesoris;
    }

    public int getHarga_sewa() {
        return harga_sewa;
    }

    public void setHarga_sewa(int harga_sewa) {
        this.harga_sewa = harga_sewa;
    }
}
