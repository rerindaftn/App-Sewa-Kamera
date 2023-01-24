package sewakamera.tampilan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sewakamera.koneksi.*;

//Menghubungkan class java dengan interface Laporan menggunakan implements
public class Admin_LaporanDataPengembalian extends javax.swing.JFrame implements Laporan{
    Transaksi trs = new Transaksi();
    public Statement st;
    public ResultSet rs;
    public DefaultTableModel tabModel;
    Connection conn = DatabaseKoneksi.koneksi();
    
    /**
     * Creates new form LaporanPenyewa
     */
    public Admin_LaporanDataPengembalian() {
        initComponents();
        // Agar tampilan ke tengah ketika dibuka
        setLocationRelativeTo(this);
        makeTable();
        showData("");
    }
    
    //INTERFACE IMPLEMENTATION
    @Override
    public final void makeTable(){
        Object[]judul={
            "ID TRANSAKSI","NAMA PENYEWA","JENIS KAMERA","LENSA","MEMORI",
            "JENIS AKSESORIS","TANGGAL SEWA","TANGGAL KEMBALI",
            "LAMA SEWA","TOTAL BAYAR"
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
            rs = st.executeQuery("SELECT tbl_transaksi.id_transaksi, tbl_penyewa.nama_penyewa, "
                    + "tbl_barang.jenis_kamera, tbl_barang.lensa, tbl_barang.memori, "
                    + "tbl_barang.jenis_aksesoris, tbl_transaksi.tgl_sewa, tbl_transaksi.tgl_kembali, "
                    + "tbl_transaksi.lama_sewa, tbl_transaksi.total_bayar "
                    + "FROM tbl_penyewa INNER JOIN (tbl_transaksi INNER JOIN tbl_barang ON "
                    + "tbl_barang.id_kamera=tbl_transaksi.id_kamera) ON "
                    + "tbl_transaksi.id_penyewa=tbl_penyewa.id_penyewa "
                    + "WHERE tbl_transaksi.status='Dikembalikan'" + where);
            while(rs.next()){
                Object[]data={
                    rs.getString("id_transaksi"),
                    rs.getString("nama_penyewa"),
                    rs.getString("jenis_kamera"),
                    rs.getString("lensa"),
                    rs.getString("memori"),
                    rs.getString("jenis_aksesoris"),
                    rs.getString("tgl_sewa"),
                    rs.getString("tgl_kembali"),
                    rs.getString("lama_sewa"),
                    rs.getString("total_bayar"),
                };
                tabModel.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @Override
    public void exitAction(){
        AdminLaporanMenu back = new AdminLaporanMenu();
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
               sql="SELECT tbl_transaksi.id_transaksi, tbl_penyewa.nama_penyewa, "
                    +"tbl_barang.jenis_kamera, tbl_barang.lensa, tbl_barang.memori, "
                    +"tbl_barang.jenis_aksesoris, tbl_transaksi.tgl_sewa, tbl_transaksi.tgl_kembali, "
                    +"tbl_transaksi.lama_sewa, tbl_transaksi.total_bayar "
                    + "FROM tbl_penyewa INNER JOIN (tbl_transaksi INNER JOIN tbl_barang ON "
                    + "tbl_barang.id_kamera=tbl_transaksi.id_kamera) ON "
                    + "tbl_transaksi.id_penyewa=tbl_penyewa.id_penyewa "
                    + "WHERE tbl_transaksi.status='Dikembalikan' AND "
                    + "tbl_transaksi.id_penyewa='"+txt_cari.getText()+"'";
            }
            else if(txt_kategori.getSelectedItem().equals("Nama Penyewa")){
               sql="SELECT tbl_transaksi.id_transaksi, tbl_penyewa.nama_penyewa, "
                    +"tbl_barang.jenis_kamera, tbl_barang.lensa, tbl_barang.memori, "
                    +"tbl_barang.jenis_aksesoris, tbl_transaksi.tgl_sewa, tbl_transaksi.tgl_kembali, "
                    +"tbl_transaksi.lama_sewa, tbl_transaksi.total_bayar "
                    + "FROM tbl_penyewa INNER JOIN (tbl_transaksi INNER JOIN tbl_barang ON "
                    + "tbl_barang.id_kamera=tbl_transaksi.id_kamera) ON "
                    + "tbl_transaksi.id_penyewa=tbl_penyewa.id_penyewa "
                    + "WHERE tbl_transaksi.status='Dikembalikan' AND "
                    + "tbl_penyewa.nama_penyewa like'%"+txt_cari.getText()+"%'";
            }
            else if(txt_kategori.getSelectedItem().equals("Tanggal Sewa")){
               sql="SELECT tbl_transaksi.id_transaksi, tbl_penyewa.nama_penyewa, "
                    +"tbl_barang.jenis_kamera, tbl_barang.lensa, tbl_barang.memori, "
                    +"tbl_barang.jenis_aksesoris, tbl_transaksi.tgl_sewa, tbl_transaksi.tgl_kembali, "
                    +"tbl_transaksi.lama_sewa, tbl_transaksi.total_bayar "
                    + "FROM tbl_penyewa INNER JOIN (tbl_transaksi INNER JOIN tbl_barang ON "
                    + "tbl_barang.id_kamera=tbl_transaksi.id_kamera) ON "
                    + "tbl_transaksi.id_penyewa=tbl_penyewa.id_penyewa "
                    + "WHERE tbl_transaksi.status='Dikembalikan' AND "
                    + "tbl_transaksi.tgl_sewa like'%"+txt_cari.getText()+"%'";
            }
            else if(txt_kategori.getSelectedItem().equals("Tanggal Kembali")){
               sql="SELECT tbl_transaksi.id_transaksi, tbl_penyewa.nama_penyewa, "
                    +"tbl_barang.jenis_kamera, tbl_barang.lensa, tbl_barang.memori, "
                    +"tbl_barang.jenis_aksesoris, tbl_transaksi.tgl_sewa, tbl_transaksi.tgl_kembali, "
                    +"tbl_transaksi.lama_sewa, tbl_transaksi.total_bayar "
                    + "FROM tbl_penyewa INNER JOIN (tbl_transaksi INNER JOIN tbl_barang ON "
                    + "tbl_barang.id_kamera=tbl_transaksi.id_kamera) ON "
                    + "tbl_transaksi.id_penyewa=tbl_penyewa.id_penyewa "
                    + "WHERE tbl_transaksi.status='Dikembalikan' AND "
                    + "tbl_transaksi.tgl_kembali like'%"+txt_cari.getText()+"%'";
            }
            System.out.println(sql);
            txt_cari.setText("");
            rs=st.executeQuery(sql);
            while(rs.next()){
                Object[]data={
                    rs.getString("id_transaksi"),
                    rs.getString("nama_penyewa"),
                    rs.getString("jenis_kamera"),
                    rs.getString("lensa"),
                    rs.getString("memori"),
                    rs.getString("jenis_aksesoris"),
                    rs.getString("tgl_sewa"),
                    rs.getString("tgl_kembali"),
                    rs.getString("lama_sewa"),
                    rs.getString("total_bayar"),
                };
                tabModel.addRow(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_back = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txt_kategori = new javax.swing.JComboBox<>();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_transaksi = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(196, 189, 172));

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));

        jLabel53.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel53.setVerifyInputWhenFocusTarget(false);

        jLabel8.setFont(new java.awt.Font("Cambria", 3, 25)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("LAPORAN DATA PENGEMBALIAN");

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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(btn_back)
                .addGap(14, 14, 14))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btn_back)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Kategori :");

        txt_kategori.setBackground(new java.awt.Color(240, 228, 216));
        txt_kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih Kategori-", "ID Transaksi", "Nama Penyewa", "Tanggal Sewa", "Tanggal Kembali" }));
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
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "Nama Penyewa", "Jenis Kamera", "Lensa", "Memori", "Jenis Aksesoris", "Tanggal Sewa", "Tanggal Kembali", "Lama Sewa", "Total Bayar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 879, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cari)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void btn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_backMouseClicked

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        // TODO add your handling code here:
        // Memanggil method exitAction()
        exitAction();
    }//GEN-LAST:event_btn_backActionPerformed

    private void tbl_transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_transaksiMouseClicked
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(Admin_LaporanDataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_LaporanDataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_LaporanDataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_LaporanDataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_LaporanDataPengembalian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_cari;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbl_transaksi;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JComboBox<String> txt_kategori;
    // End of variables declaration//GEN-END:variables
}
