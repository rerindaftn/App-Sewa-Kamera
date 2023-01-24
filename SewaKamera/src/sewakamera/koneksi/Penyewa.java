package sewakamera.koneksi;

//Penerapan inheritance kelas ID_Collection
public class Penyewa extends ID_Collection{
    private String nik;
    private String nama_penyewa;
    private String alamat_penyewa;
    private String nohp_penyewa;
    
    public Penyewa(){
        
    }
    
    public Penyewa(int id_penyewa, String nik, String nama_penyewa, String alamat_penyewa, String nohp_penyewa){
        this.id_penyewa = id_penyewa;
        this.nik = nik;
        this.nama_penyewa = nama_penyewa;
        this.alamat_penyewa = alamat_penyewa;
        this.nohp_penyewa = nohp_penyewa;
    }

    //Penerapan polymorphism dengan @Override
    @Override
    public int getId_Penyewa() {
        return id_penyewa;
    }

    @Override
    public void setId_Penyewa(int id_penyewa) {
        this.id_penyewa = id_penyewa;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama_penyewa() {
        return nama_penyewa;
    }

    public void setNama_penyewa(String nama_penyewa) {
        this.nama_penyewa = nama_penyewa;
    }

    public String getAlamat_penyewa() {
        return alamat_penyewa;
    }

    public void setAlamat_penyewa(String alamat_penyewa) {
        this.alamat_penyewa = alamat_penyewa;
    }

    public String getNohp_penyewa() {
        return nohp_penyewa;
    }

    public void setNohp_penyewa(String nohp_penyewa) {
        this.nohp_penyewa = nohp_penyewa;
    }
}
