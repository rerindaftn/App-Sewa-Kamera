package sewakamera.tampilan;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sewakamera.koneksi.*;

//Menghubungkan class java dengan interface FormPenyewaan menggunakan implements
public class DataPenyewaan extends javax.swing.JFrame implements FormPenyewaan{
    Transaksi trs = new Transaksi();
    public Statement st;
    public ResultSet rs;
    public DefaultTableModel tabModel;
    Connection conn = DatabaseKoneksi.koneksi();
    
    /**
     * Creates new form DataPenyewaan
     */
    public DataPenyewaan() {
        initComponents();
        // Agar tampilan ke tengah ketika dibuka
        setLocationRelativeTo(this);
        autonumber();
        makeTable();
        showData("");
        showData_IdP();
        showData_IdB();
    }
    
    public final void autonumber(){
        try {
            String an = "SELECT MAX(id_transaksi) FROM tbl_transaksi";
            st = conn.prepareStatement(an);
            rs = st.executeQuery(an);
            if(rs.next()){
                int a = rs.getInt(1);
                txt_idTransaksi.setText(Integer.toString(a+1));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void hitungAction(){
        try {
            conn=(Connection)DatabaseKoneksi.koneksi();
            st = conn.createStatement();
            String sql="SELECT harga_sewa FROM tbl_barang where id_kamera='"+txt_idBarang.getSelectedItem()+"'";
            rs = st.executeQuery(sql);
            int a=Integer.parseInt(txt_hargaSewa.getText());
            int b=Integer.parseInt(txt_lamaSewa.getText());
            int jumlah=a*b;
            txt_total.setText (Integer.toString (jumlah));   
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    public void insertData(){
        try {
            String sql = "INSERT INTO tbl_transaksi VALUES"
                    + "('"+txt_idTransaksi.getText()
                    +"','"+txt_idPenyewa.getSelectedItem()
                    +"','"+txt_idBarang.getSelectedItem()
                    +"','"+txt_tglSewa.getText()
                    +"', '2022-01-01', '"+txt_lamaSewa.getText()
                    +"','"+txt_status.getText()
                    +"','"+txt_total.getText()+"')";
            conn=(Connection)DatabaseKoneksi.koneksi();
            st=conn.prepareStatement(sql);
            st.execute(sql);
            showData("");
            JOptionPane.showMessageDialog(null, "Data Penyewaan Berhasil Ditambahkan!");
            clearData();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    // INTERFACE IMPLEMENTATION
    @Override
    public void clearData(){
        //Memanggil method autonumber()
        autonumber();
        txt_idPenyewa.setSelectedItem("-Pilih ID Penyewa-");
        txt_nik.setText("");
        txt_nama.setText("");
        txt_alamat.setText("");
        txt_noHp.setText("");
        txt_idBarang.setSelectedItem("-Pilih ID Barang-");
        txt_jenisKamera.setText("");
        txt_lensa.setText("");
        txt_memori.setText("");
        txt_jenisAksesoris.setText("");
        txt_hargaSewa.setText("");
        txt_tglSewa.setText("");
        txt_lamaSewa.setText("");
        txt_status.setText("Disewa");
        txt_total.setText("");
    }
    
    @Override
    public final void makeTable(){
        Object[]judul={
            "ID TRANSAKSI","ID PENYEWA","ID BARANG","TANGGAL SEWA",
            "LAMA SEWA","STATUS","TOTAL BAYAR"
        };
        tabModel = new DefaultTableModel(null, judul);
        tbl_transaksi.setModel(tabModel);
    }
    
    @Override
    public final void showData(String where){
        try {
            st = conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            tabModel.fireTableDataChanged();
            rs = st.executeQuery("SELECT * FROM tbl_transaksi" + where);
            while(rs.next()){
                Object[]data={
                    rs.getString("id_transaksi"),
                    rs.getString("id_penyewa"),
                    rs.getString("id_kamera"),
                    rs.getString("tgl_sewa"),
                    rs.getString("lama_sewa"),
                    rs.getString("status"),
                    rs.getString("total_bayar"),
                };
                tabModel.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @Override
    public final void showData_IdP(){
        try {
            conn=(Connection)DatabaseKoneksi.koneksi();
            st = conn.createStatement();
            String sql = "SELECT id_penyewa FROM tbl_penyewa";
            rs=st.executeQuery(sql);

            while(rs.next()){
                Object[] ob = new Object[5];
                ob[0] = rs.getString(1);

                // fungsi ini bertugas menampung isi dari database
                txt_idPenyewa.addItem((String) ob[0]);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public final void showDataP(){
        try {
            conn=(Connection)DatabaseKoneksi.koneksi();
            st = conn.createStatement();
            String sql = "SELECT nik, nama_penyewa, alamat_penyewa, nohp_penyewa FROM tbl_penyewa where id_penyewa='"+txt_idPenyewa.getSelectedItem()+"'";  
            rs = st.executeQuery(sql);

            while(rs.next()){
                Object[] ob = new Object[4];
                ob[0]=  rs.getString(1);
                ob[1]= rs.getString(2);
                ob[2]= rs.getString(3);
                ob[3]= rs.getString(4);

                txt_nik.setText((String) ob[0]);
                txt_nama.setText((String) ob[1]);
                txt_alamat.setText((String) ob[2]);
                txt_noHp.setText((String) ob[3]);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public final void showData_IdB(){
        try {
            conn=(Connection)DatabaseKoneksi.koneksi();
            st = conn.createStatement();
            String sql = "SELECT id_kamera FROM tbl_barang";
            rs=st.executeQuery(sql);

            while(rs.next()){
                Object[] ob = new Object[5];
                ob[0] = rs.getString(1);

                // fungsi ini bertugas menampung isi dari database
                txt_idBarang.addItem((String) ob[0]);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public final void showDataB(){
        try {
            conn=(Connection)DatabaseKoneksi.koneksi();
            st = conn.createStatement();
            String sql = "SELECT jenis_kamera, lensa, memori, jenis_aksesoris, harga_sewa FROM tbl_barang where id_kamera='"+txt_idBarang.getSelectedItem()+"'";  
            rs = st.executeQuery(sql);

            while(rs.next()){
                Object[] ob = new Object[5];
                ob[0]=  rs.getString(1);
                ob[1]= rs.getString(2);
                ob[2]= rs.getString(3);
                ob[3]= rs.getString(4);
                ob[4]= rs.getString(5);

                txt_jenisKamera.setText((String) ob[0]);
                txt_lensa.setText((String) ob[1]);
                txt_memori.setText((String) ob[2]);
                txt_jenisAksesoris.setText((String) ob[3]);
                txt_hargaSewa.setText((String) ob[4]);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void updateData(){
        try {
            conn=(Connection)DatabaseKoneksi.koneksi();
            st = conn.createStatement();
            st.executeUpdate("UPDATE tbl_transaksi set " 
                + "id_penyewa='"+ txt_idPenyewa.getSelectedItem() + "', "
                + "id_kamera='"+ txt_idBarang.getSelectedItem()+ "', "
                + "tgl_sewa='"+ txt_tglSewa.getText()+ "', "
                + "tgl_kembali='2022-01-01', "
                +"lama_sewa='"+txt_lamaSewa.getText() + "', "
                + "status='"+ txt_status.getText()+ "', "
                + "total_bayar='"+ txt_total.getText()+ "' "
                + "where id_transaksi='"+txt_idTransaksi.getText()+ "'");
            showData("");
            JOptionPane.showMessageDialog(null, "Data Pegawai Berhasil Diubah!");
            clearData();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    @Override
    public void deleteData(){
        try {
            int jawab;
            if ((jawab = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Menghapus Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION)) == 0) {
                conn=(Connection)DatabaseKoneksi.koneksi();
                st = conn.createStatement();
                st.executeUpdate("DELETE FROM tbl_transaksi WHERE id_transaksi='"
                    + tabModel.getValueAt(tbl_transaksi.getSelectedRow(), 0) + "'");
                showData("");
                clearData();
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
   @Override
    public void exitAction(){
        PegawaiMainMenu back = new PegawaiMainMenu();
        back.setVisible(true);
        setVisible(false);
    }
    
    @Override
    public void kategoriAction(){
        //Mengatur jika user memilih '-Pilih Kategori-'
        if(txt_kategori.getSelectedItem().equals("-Pilih Kategori-")){
            //Memanggil method showData(String where)
            //Untuk menampilkan seluruh isi tabel
            showData("");
            //Mengosongkan field txt_cari
            txt_cari.setText("");
        }
    }
    
    @Override
    public void cariAction(){
        try {
            conn=(Connection)DatabaseKoneksi.koneksi();
            st = conn.createStatement();
            String sql = null;
            makeTable();
           if(txt_kategori.getSelectedItem().equals("ID Transaksi")){
               sql="SELECT * FROM tbl_transaksi where id_transaksi='"+txt_cari.getText()+"'";
           }else if(txt_kategori.getSelectedItem().equals("ID Penyewa")){
               sql="SELECT * FROM tbl_transaksi where id_penyewa='"+txt_cari.getText()+"'";
           }else if(txt_kategori.getSelectedItem().equals("ID Barang")){
               sql="SELECT * FROM tbl_transaksi where id_kamera='"+txt_cari.getText()+"'";
           }else if(txt_kategori.getSelectedItem().equals("Status")){
               sql="SELECT * FROM tbl_transaksi where status like'%"+txt_cari.getText()+"%'";
           }
           System.out.println(sql);
           txt_cari.setText("");
           rs=st.executeQuery(sql);
           while(rs.next()){
                Object[]data={
                    rs.getString("id_transaksi"),
                    rs.getString("id_penyewa"),
                    rs.getString("id_kamera"),
                    rs.getString("tgl_sewa"),
                    rs.getString("lama_sewa"),
                    rs.getString("status"),
                    rs.getString("total_bayar"),
                };
                tabModel.addRow(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void userClicked(){
        //Mengatur agar ketika salah satu data ditabel di klik
        //Maka akan muncul di text field yang telah diatur
        txt_idTransaksi.setText(tbl_transaksi.getValueAt(tbl_transaksi.getSelectedRow(), 0).toString());
        txt_idPenyewa.setSelectedItem(tbl_transaksi.getValueAt(tbl_transaksi.getSelectedRow(), 1).toString());
        txt_idBarang.setSelectedItem(tbl_transaksi.getValueAt(tbl_transaksi.getSelectedRow(), 2).toString());
        txt_tglSewa.setText(tbl_transaksi.getValueAt(tbl_transaksi.getSelectedRow(), 3).toString());
        txt_lamaSewa.setText(tbl_transaksi.getValueAt(tbl_transaksi.getSelectedRow(), 4).toString());
        txt_status.setText(tbl_transaksi.getValueAt(tbl_transaksi.getSelectedRow(), 5).toString());
        txt_total.setText(tbl_transaksi.getValueAt(tbl_transaksi.getSelectedRow(), 6).toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_idTransaksi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_nik = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btn_reset = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        txt_alamat = new javax.swing.JTextField();
        txt_noHp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_idPenyewa = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txt_jenisKamera = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_idBarang = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txt_lensa = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_memori = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_jenisAksesoris = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_hargaSewa = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_tglSewa = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txt_lamaSewa = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txt_status = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        txt_HariLamaPinjam = new javax.swing.JLabel();
        btn_hitung = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        btn_back = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txt_kategori = new javax.swing.JComboBox<>();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_transaksi = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Pristina", 1, 25)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(244, 238, 225));
        jLabel6.setText("Tajir Kamirat");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sewakamera/gambar/icon_dataCamera-removebg-preview.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID Transaksi");

        txt_idTransaksi.setBackground(new java.awt.Color(240, 228, 216));

        jLabel7.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ID Penyewa");

        jLabel4.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("NIK");

        txt_nik.setBackground(new java.awt.Color(240, 228, 216));

        jLabel5.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nama");

        txt_nama.setBackground(new java.awt.Color(240, 228, 216));
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Alamat");

        btn_reset.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btn_reset.setForeground(new java.awt.Color(255, 102, 102));
        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        btn_save.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btn_save.setForeground(new java.awt.Color(0, 0, 204));
        btn_save.setText("Save");
        btn_save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_saveMouseClicked(evt);
            }
        });
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_update.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btn_update.setForeground(new java.awt.Color(51, 102, 255));
        btn_update.setText("Update");
        btn_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_updateMouseClicked(evt);
            }
        });
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_delete.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 0, 0));
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        txt_alamat.setBackground(new java.awt.Color(240, 228, 216));
        txt_alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_alamatActionPerformed(evt);
            }
        });

        txt_noHp.setBackground(new java.awt.Color(240, 228, 216));
        txt_noHp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_noHpActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("No. Hp");

        txt_idPenyewa.setBackground(new java.awt.Color(240, 228, 216));
        txt_idPenyewa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih ID Penyewa-" }));
        txt_idPenyewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_idPenyewaMouseClicked(evt);
            }
        });
        txt_idPenyewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idPenyewaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("ID Barang");

        txt_jenisKamera.setBackground(new java.awt.Color(240, 228, 216));

        jLabel10.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Jenis Kamera");

        txt_idBarang.setBackground(new java.awt.Color(240, 228, 216));
        txt_idBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih ID Barang-" }));
        txt_idBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_idBarangMouseClicked(evt);
            }
        });
        txt_idBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idBarangActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Lensa");

        txt_lensa.setBackground(new java.awt.Color(240, 228, 216));

        jLabel12.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Memori");

        txt_memori.setBackground(new java.awt.Color(240, 228, 216));
        txt_memori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_memoriActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Jenis Aksesoris");

        txt_jenisAksesoris.setBackground(new java.awt.Color(240, 228, 216));
        txt_jenisAksesoris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jenisAksesorisActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Harga Sewa");

        txt_hargaSewa.setBackground(new java.awt.Color(240, 228, 216));
        txt_hargaSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaSewaActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tanggal Sewa");

        txt_tglSewa.setBackground(new java.awt.Color(240, 228, 216));

        jLabel20.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Lama Sewa");

        txt_lamaSewa.setBackground(new java.awt.Color(240, 228, 216));
        txt_lamaSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_lamaSewaActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Status");

        txt_status.setBackground(new java.awt.Color(240, 228, 216));
        txt_status.setText("Disewa");
        txt_status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_statusActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Total Bayar");

        txt_total.setBackground(new java.awt.Color(240, 228, 216));
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        txt_HariLamaPinjam.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        txt_HariLamaPinjam.setForeground(new java.awt.Color(255, 255, 255));
        txt_HariLamaPinjam.setText("Hari");

        btn_hitung.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btn_hitung.setText("Hitung");
        btn_hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hitungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(246, 246, 246))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_lamaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_HariLamaPinjam))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_tglSewa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_total, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_nik, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_noHp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_idPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_idTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel11)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel12)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                    .addComponent(txt_jenisAksesoris, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_hargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_jenisKamera, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txt_lensa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btn_reset)
                                        .addComponent(btn_save))
                                    .addGap(32, 32, 32)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btn_update)
                                        .addComponent(btn_hitung))
                                    .addGap(34, 34, 34)))
                            .addComponent(txt_memori, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_delete)
                        .addGap(91, 91, 91))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_idTransaksi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_idPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_noHp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tglSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txt_idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txt_jenisKamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txt_lensa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_memori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txt_jenisAksesoris, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txt_hargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_reset)
                            .addComponent(btn_hitung))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_update)
                            .addComponent(btn_save)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_lamaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(txt_HariLamaPinjam))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(btn_delete))))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        txt_idTransaksi.setEditable(false);
        txt_nik.setEditable(false);
        txt_nama.setEditable(false);
        txt_alamat.setEditable(false);
        txt_noHp.setEditable(false);
        txt_jenisKamera.setEditable(false);
        txt_lensa.setEditable(false);
        txt_memori.setEditable(false);
        txt_jenisAksesoris.setEditable(false);
        txt_hargaSewa.setEditable(false);
        txt_status.setEditable(false);
        txt_total.setEditable(false);

        jPanel1.setBackground(new java.awt.Color(196, 189, 172));

        jLabel15.setFont(new java.awt.Font("Cambria", 3, 25)); // NOI18N
        jLabel15.setText("DATA PENYEWAAN");

        btn_back.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btn_back.setForeground(new java.awt.Color(51, 153, 0));
        btn_back.setText("Back");
        btn_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_backMouseClicked(evt);
            }
        });
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Kategori :");

        txt_kategori.setBackground(new java.awt.Color(240, 228, 216));
        txt_kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih Kategori-", "ID Transaksi", "ID Penyewa", "ID Barang", "Status" }));
        txt_kategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_kategoriMouseClicked(evt);
            }
        });
        txt_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kategoriActionPerformed(evt);
            }
        });

        txt_cari.setBackground(new java.awt.Color(240, 228, 216));

        btn_cari.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        tbl_transaksi.setBackground(new java.awt.Color(235, 207, 196));
        tbl_transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "ID Penyewa", "ID Barang", "Tanggal Sewa", "Lama Sewa", "Status", "Total Bayar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_transaksiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_transaksi);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cari)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_back)))
                        .addGap(18, 18, 18))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(btn_back))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
        // Memanggil method clearData()
        clearData();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btn_saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_saveMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_saveMouseClicked

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
        // Memanggil method insertData()
        insertData();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_updateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_updateMouseClicked

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
        //Memanggil method updateData()
        updateData();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        // Memanggil method deleteData()
        deleteData();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void txt_alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_alamatActionPerformed

    private void txt_noHpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_noHpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noHpActionPerformed

    private void txt_idPenyewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_idPenyewaMouseClicked
        // TODO add your handling code here:
        showDataP();
    }//GEN-LAST:event_txt_idPenyewaMouseClicked

    private void txt_idPenyewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idPenyewaActionPerformed
        // TODO add your handling code here:
        showDataP();
    }//GEN-LAST:event_txt_idPenyewaActionPerformed

    private void txt_idBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_idBarangMouseClicked
        // TODO add your handling code here:
        showDataB();
    }//GEN-LAST:event_txt_idBarangMouseClicked

    private void txt_idBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idBarangActionPerformed
        // TODO add your handling code here:
        showDataB();
    }//GEN-LAST:event_txt_idBarangActionPerformed

    private void txt_memoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_memoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_memoriActionPerformed

    private void txt_jenisAksesorisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jenisAksesorisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jenisAksesorisActionPerformed

    private void txt_hargaSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaSewaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaSewaActionPerformed

    private void txt_lamaSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_lamaSewaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_lamaSewaActionPerformed

    private void txt_statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_statusActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void btn_hitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hitungActionPerformed
        // TODO add your handling code here:
        // Memanggil method hitungAction()
        hitungAction();
    }//GEN-LAST:event_btn_hitungActionPerformed

    private void btn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_backMouseClicked

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        // TODO add your handling code here:
        // Memanggil method backAction()
        exitAction();
    }//GEN-LAST:event_btn_backActionPerformed

    private void txt_kategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_kategoriMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kategoriMouseClicked

    private void txt_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kategoriActionPerformed
        // TODO add your handling code here:
        // Memanggil method kategoriAction()
        kategoriAction();
    }//GEN-LAST:event_txt_kategoriActionPerformed

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        // Memanggil method cariAction()
        cariAction();
    }//GEN-LAST:event_btn_cariActionPerformed

    private void tbl_transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_transaksiMouseClicked
        // TODO add your handling code here:
        // Memanggil method transClicked()
        userClicked();
    }//GEN-LAST:event_tbl_transaksiMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DataPenyewaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataPenyewaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataPenyewaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataPenyewaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataPenyewaan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_hitung;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbl_transaksi;
    private javax.swing.JLabel txt_HariLamaPinjam;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_hargaSewa;
    private javax.swing.JComboBox<String> txt_idBarang;
    private javax.swing.JComboBox<String> txt_idPenyewa;
    private javax.swing.JTextField txt_idTransaksi;
    private javax.swing.JTextField txt_jenisAksesoris;
    private javax.swing.JTextField txt_jenisKamera;
    private javax.swing.JComboBox<String> txt_kategori;
    private javax.swing.JTextField txt_lamaSewa;
    private javax.swing.JTextField txt_lensa;
    private javax.swing.JTextField txt_memori;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nik;
    private javax.swing.JTextField txt_noHp;
    private javax.swing.JTextField txt_status;
    private javax.swing.JTextField txt_tglSewa;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
