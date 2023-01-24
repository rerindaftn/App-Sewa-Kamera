package sewakamera.koneksi;

//Penerapan inheritance kelas ID_Collection
public class Transaksi extends ID_Collection{
    private int id_transaksi;
    private String tgl_sewa;
    private String tgl_kembali;
    private int lama_sewa;
    private int total_bayar;
    
    public Transaksi(){
        
    }
    
    public Transaksi(int id_transaksi, int id_penyewa, int id_kamera, String tgl_sewa, String tgl_kembali, int lama_sewa, int total_bayar){
        this.id_transaksi = id_transaksi;
        this.id_penyewa = id_penyewa;
        this.id_kamera = id_kamera;
        this.tgl_sewa = tgl_sewa;
        this.tgl_kembali = tgl_kembali;
        this.lama_sewa = lama_sewa;
        this.total_bayar = total_bayar;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
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

    @Override
    public int getId_kamera() {
        return id_kamera;
    }

    @Override
    public void setId_kamera(int id_kamera) {
        this.id_kamera = id_kamera;
    }

    public String getTgl_sewa() {
        return tgl_sewa;
    }

    public void setTgl_sewa(String tgl_sewa) {
        this.tgl_sewa = tgl_sewa;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public int getLama_sewa() {
        return lama_sewa;
    }

    public void setLama_sewa(int lama_sewa) {
        this.lama_sewa = lama_sewa;
    }

    public int getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(int total_bayar) {
        this.total_bayar = total_bayar;
    } 
}
