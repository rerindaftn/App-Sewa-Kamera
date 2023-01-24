-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 16, 2022 at 02:46 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `database_tajirat`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_barang`
--

CREATE TABLE `tbl_barang` (
  `id_kamera` int(10) NOT NULL,
  `jenis_kamera` varchar(40) NOT NULL,
  `lensa` varchar(40) NOT NULL,
  `memori` varchar(20) NOT NULL,
  `jenis_aksesoris` varchar(25) NOT NULL,
  `harga_sewa` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_barang`
--

INSERT INTO `tbl_barang` (`id_kamera`, `jenis_kamera`, `lensa`, `memori`, `jenis_aksesoris`, `harga_sewa`) VALUES
(1, 'Fujifilm X-A2', 'Fujifilm 16-50mm f3-5', '16 gb', 'None', 170000),
(2, 'Fujifilm X-A2', 'Fujifilm 15-45mm f3.5', '32 gb', 'None', 145000);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_penyewa`
--

CREATE TABLE `tbl_penyewa` (
  `id_penyewa` int(10) NOT NULL,
  `nik` varchar(20) NOT NULL,
  `nama_penyewa` varchar(25) NOT NULL,
  `alamat_penyewa` varchar(30) NOT NULL,
  `nohp_penyewa` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_penyewa`
--

INSERT INTO `tbl_penyewa` (`id_penyewa`, `nik`, `nama_penyewa`, `alamat_penyewa`, `nohp_penyewa`) VALUES
(1, '320107665524', 'Hasna Syifa', 'Cibiru', '081234567890'),
(2, '320172635812', 'Rerinda', 'Cirebon', '081236735469'),
(6, '320176251497', 'Alya', 'Cileungsi', '081286372658'),
(7, '329824798245', 'Vinaputri', 'Lembang', '087654231425'),
(8, '320187376463', 'Andrea', 'Majalengka', '081235364736'),
(9, '320766514241', 'Kim Taehyung', 'Seoul', '13062013'),
(10, '32003827325', 'Jeon Jungkook', 'Busan', '02763651241');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_transaksi`
--

CREATE TABLE `tbl_transaksi` (
  `id_transaksi` int(10) NOT NULL,
  `id_penyewa` int(10) NOT NULL,
  `id_kamera` int(10) NOT NULL,
  `tgl_sewa` date NOT NULL,
  `tgl_kembali` date NOT NULL,
  `lama_sewa` int(10) NOT NULL,
  `status` varchar(20) NOT NULL,
  `total_bayar` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_transaksi`
--

INSERT INTO `tbl_transaksi` (`id_transaksi`, `id_penyewa`, `id_kamera`, `tgl_sewa`, `tgl_kembali`, `lama_sewa`, `status`, `total_bayar`) VALUES
(7, 1, 1, '2022-06-13', '2022-06-14', 1, 'Dikembalikan', 170000),
(8, 8, 1, '2022-06-07', '2022-06-09', 2, 'Disewa', 340000),
(9, 9, 2, '2022-06-20', '2022-06-23', 3, 'Dikembalikan', 570000),
(10, 7, 2, '2022-06-25', '2022-06-27', 2, 'Dikembalikan', 380000),
(11, 8, 1, '2022-06-25', '2022-06-27', 2, 'Dikembalikan', 340000);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `id_user` int(10) NOT NULL,
  `nama_user` varchar(25) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(10) NOT NULL,
  `akses` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`id_user`, `nama_user`, `username`, `password`, `akses`) VALUES
(1, 'Andrea Rahmania', 'Andrea', '123', 'Admin'),
(2, 'Vinaputri Purnama', 'Vina', '456', 'Pegawai'),
(3, 'Hasna Syifa', 'Hasna', '789', 'Pegawai'),
(4, 'Alya Arthamevia', 'Alya', '130', 'Pegawai'),
(5, 'Rerinda Fiktianti', 'Rere', '120', 'Pegawai'),
(6, 'Jeon Jungkook', 'Jeons', '1306', 'Admin'),
(8, 'Jaemin', 'Jae', '1234', 'Pegawai');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_barang`
--
ALTER TABLE `tbl_barang`
  ADD PRIMARY KEY (`id_kamera`);

--
-- Indexes for table `tbl_penyewa`
--
ALTER TABLE `tbl_penyewa`
  ADD PRIMARY KEY (`id_penyewa`);

--
-- Indexes for table `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_pelanggan` (`id_penyewa`) USING BTREE,
  ADD KEY `id_kamera` (`id_kamera`) USING BTREE;

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_barang`
--
ALTER TABLE `tbl_barang`
  MODIFY `id_kamera` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_penyewa`
--
ALTER TABLE `tbl_penyewa`
  MODIFY `id_penyewa` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  MODIFY `id_transaksi` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `id_user` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  ADD CONSTRAINT `tbl_transaksi_ibfk_1` FOREIGN KEY (`id_penyewa`) REFERENCES `tbl_penyewa` (`id_penyewa`),
  ADD CONSTRAINT `tbl_transaksi_ibfk_2` FOREIGN KEY (`id_kamera`) REFERENCES `tbl_barang` (`id_kamera`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
