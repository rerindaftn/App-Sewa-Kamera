package sewakamera.tampilan;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sewakamera.koneksi.*;

//Menghubungkan class java dengan interface Metode menggunakan implements
public class Admin_DataPenyewa extends javax.swing.JFrame implements Metode{
    Penyewa psw = new Penyewa();
    public Statement st;
    public ResultSet rs;
    public DefaultTableModel tabModel;
    Connection conn = DatabaseKoneksi.koneksi();
    
    /**
     * Creates new form DataPegawai
     */
    public Admin_DataPenyewa() {
        initComponents();
        // Agar tampilan ke tengah ketika dibuka
        setLocationRelativeTo(this);
        autonumber();
        makeTable();
        showData("");
    }
    
    // INTERFACE IMPLEMENTATION
    @Override
    public final void autonumber(){
        try {
            //Mengambil id_user tertinggi dari tabel tbl_penyewa pada database
            String an = "SELECT MAX(id_penyewa) FROM tbl_penyewa";
            st = conn.prepareStatement(an);
            rs = st.executeQuery(an);
            if(rs.next()){
                int a = rs.getInt(1);
                txt_idpenyewa.setText(Integer.toString(a+1));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @Override
    public void clearData(){
        //Memanggil method autonumber()
        autonumber();
        txt_nik.setText("");
        txt_nama.setText("");
        txt_alamat.setText("");
        txt_nohp.setText("");
    }
    
    @Override
    public final void makeTable(){
        Object[]judul={
            //Membuat kolom pada tabel
            "ID","NIK","NAMA","ALAMAT","NO.HP"
        };
        tabModel = new DefaultTableModel(null, judul);
        tbl_penyewa.setModel(tabModel);
    }
    
    @Override
    public final void showData(String where){
        try {
            st = conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            tabModel.fireTableDataChanged();
            rs = st.executeQuery("SELECT * FROM tbl_penyewa" + where);
            //Mengatur isi kolom di setiap tabel yang sudah dibentuk pada method judul()
            while(rs.next()){
                Object[]data={
                    rs.getString("id_penyewa"),
                    rs.getString("nik"),
                    rs.getString("nama_penyewa"),
                    rs.getString("alamat_penyewa"),
                    rs.getString("nohp_penyewa"),
                };
                tabModel.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @Override
    public void insertData(){
        try {
            //Menulis SQL INSERT ke tabel tbl_penyewa
            String sql = "INSERT INTO tbl_penyewa VALUES"
                    + "('"+txt_idpenyewa.getText()
                    +"','"+txt_nik.getText()
                    +"','"+txt_nama.getText()
                    +"','"+txt_alamat.getText()
                    +"','"+txt_nohp.getText()+"')";
            conn=(Connection)DatabaseKoneksi.koneksi();
            st=conn.prepareStatement(sql);
            st.execute(sql);
            //Memanggil method showData(String where)
            //Agar memunculkan data yang baru dimasukkan ke dalam tabel
            showData("");
            //Mengatur pesan yang keluar ketika create berhasil dilakukan
            JOptionPane.showMessageDialog(null, "Data Penyewa Berhasil Ditambahkan!");
            //Memanggil method clearData()
            //Agar text field kembali kosong
            clearData();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    @Override
    public void updateData(){
        try {
            conn=(Connection)DatabaseKoneksi.koneksi();
            st = conn.createStatement();
            //Masukkan SQL UPDATE untuk tabel tbl_penyewa sesuai id_penyewa yang dipilih
            st.executeUpdate("UPDATE tbl_penyewa set " 
                + "nik='"+ txt_nik.getText() + "', "
                + "nama_penyewa='"+ txt_nama.getText()+ "', "
                + "alamat_penyewa='"+ txt_alamat.getText()+ "', "
                + "nohp_penyewa='"+ txt_nohp.getText()+ "'"
                + "where id_penyewa= '"+txt_idpenyewa.getText()+ "'");
            //Memanggil method showData(String where)
            //Agar memunculkan data yang telah di update ke dalam tabel
            showData("");
            //Mengatur pesan yang keluar ketika update berhasil dilakukan
            JOptionPane.showMessageDialog(null, "Data Penyewa Berhasil Diubah!");
            //Memanggil method clearData()
            //Agar text field kembali kosong
            clearData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @Override
    public void deleteData(){
        try {
            int jawab;
            //Mengatur jendela peringatan konfirmasi mengenai penghapusan data
            if ((jawab = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Menghapus Data?", "Konfirmasi", JOptionPane.YES_NO_OPTION)) == 0) {
                conn=(Connection)DatabaseKoneksi.koneksi();
                st = conn.createStatement();
                //Menulis SQL DELETE untuk tabel tbl_penyewa sesuai id_penyewa yang dipilih
                st.executeUpdate("DELETE FROM tbl_penyewa WHERE id_penyewa='"
                    + tabModel.getValueAt(tbl_penyewa.getSelectedRow(), 0) + "'");
                //Memanggil method showData(String where)
                //Agar memunculkan kembali data lain yang tidak terhapus ke dalam tabel
                showData("");
                //Memanggil method clearData()
                //Agar text field kembali kosong
                clearData();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @Override
    public void exitAction(){
        AdminMainMenu back = new AdminMainMenu();
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
            //Memanggil method makeTable()
            makeTable();
            if(txt_kategori.getSelectedItem().equals("ID")){
               sql="SELECT * FROM tbl_penyewa where id_penyewa='"+txt_cari.getText()+"'";
            }else if(txt_kategori.getSelectedItem().equals("NIK")){
               sql="SELECT * FROM tbl_penyewa where nik like'%"+txt_cari.getText()+"%'";
            }else if(txt_kategori.getSelectedItem().equals("NAMA")){
               sql="SELECT * FROM tbl_penyewa where nama_penyewa like '%"+txt_cari.getText()+"%'";
            }
            //Menampilkan data yang dimasukkan ke dalam field txt_cari sesuai kategori yang dipilih
            System.out.println(sql);
            //Mengosongkan kembali field txt_cari
            txt_cari.setText("");
            rs=st.executeQuery(sql);
            //Mengatur isi tabel saat proses cari data
            while(rs.next()){
                Object[]data={
                    rs.getString("id_penyewa"),
                    rs.getString("nik"),
                    rs.getString("nama_penyewa"),
                    rs.getString("alamat_penyewa"),
                    rs.getString("nohp_penyewa"),
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
        txt_idpenyewa.setText(tbl_penyewa.getValueAt(tbl_penyewa.getSelectedRow(), 0).toString());
        txt_nik.setText(tbl_penyewa.getValueAt(tbl_penyewa.getSelectedRow(), 1).toString());
        txt_nama.setText(tbl_penyewa.getValueAt(tbl_penyewa.getSelectedRow(), 2).toString());
        txt_alamat.setText(tbl_penyewa.getValueAt(tbl_penyewa.getSelectedRow(), 3).toString());
        txt_nohp.setText(tbl_penyewa.getValueAt(tbl_penyewa.getSelectedRow(), 4).toString());
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
        txt_idpenyewa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_nik = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_alamat = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btn_reset = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        txt_nohp = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_penyewa = new javax.swing.JTable();
        btn_back = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txt_kategori = new javax.swing.JComboBox<>();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Pristina", 1, 25)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(244, 238, 225));
        jLabel6.setText("Tajir Kamirat");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sewakamera/gambar/icon_dataCamera-removebg-preview.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID");

        txt_idpenyewa.setBackground(new java.awt.Color(240, 228, 216));

        jLabel7.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("NIK");

        txt_nik.setBackground(new java.awt.Color(240, 228, 216));
        txt_nik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nikActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nama");

        txt_nama.setBackground(new java.awt.Color(240, 228, 216));

        jLabel5.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Alamat");

        txt_alamat.setBackground(new java.awt.Color(240, 228, 216));
        txt_alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_alamatActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("No. HP");

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

        txt_nohp.setBackground(new java.awt.Color(240, 228, 216));
        txt_nohp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nohpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(50, 50, 50)
                                            .addComponent(txt_idpenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                    .addComponent(jLabel6)
                                                    .addGap(25, 25, 25))
                                                .addComponent(txt_nik, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addComponent(jLabel2)
                                .addComponent(txt_nama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_nohp, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_reset)
                            .addComponent(btn_update))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_delete)
                            .addComponent(btn_save))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_idpenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_nik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nohp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_reset)
                    .addComponent(btn_save))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_update)
                    .addComponent(btn_delete))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        txt_idpenyewa.setEditable(false);

        jPanel1.setBackground(new java.awt.Color(196, 189, 172));

        jLabel8.setFont(new java.awt.Font("Cambria", 3, 25)); // NOI18N
        jLabel8.setText("DATA PENYEWA");

        tbl_penyewa.setBackground(new java.awt.Color(235, 207, 196));
        tbl_penyewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "NIK", "NAMA", "ALAMAT", "NO. HP"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_penyewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_penyewaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_penyewa);

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

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Kategori :");

        txt_kategori.setBackground(new java.awt.Color(240, 228, 216));
        txt_kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih Kategori-", "ID", "NIK", "NAMA" }));
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(43, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cari)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_back)
                        .addGap(18, 18, 18))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(btn_back))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nikActionPerformed

    private void txt_alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_alamatActionPerformed

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

    private void tbl_penyewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_penyewaMouseClicked
        // TODO add your handling code here:
        // Memanggil method userClicked()
        userClicked();
    }//GEN-LAST:event_tbl_penyewaMouseClicked

    private void btn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_backMouseClicked

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        // TODO add your handling code here:
        // Memanggil method exitAction()
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

    private void txt_nohpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nohpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nohpActionPerformed

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
            java.util.logging.Logger.getLogger(Admin_DataPenyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_DataPenyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_DataPenyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_DataPenyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_DataPenyewa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_penyewa;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_idpenyewa;
    private javax.swing.JComboBox<String> txt_kategori;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nik;
    private javax.swing.JTextField txt_nohp;
    // End of variables declaration//GEN-END:variables
}
