package sewakamera.koneksi;

//Interface class
public interface FormPenyewaan {
    void clearData();
    void makeTable();
    void showData(String where);
    void showData_IdP();
    void showDataP();
    void showData_IdB();
    void showDataB();
    void updateData();
    void deleteData();
    void exitAction();
    void kategoriAction();
    void cariAction();
    void userClicked();
}
