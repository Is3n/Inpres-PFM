-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 20 déc. 2020 à 17:00
-- Version du serveur :  5.7.19
-- Version de PHP :  5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `bd_mouvements`
--

-- --------------------------------------------------------

--
-- Structure de la table `containers`
--

DROP TABLE IF EXISTS `containers`;
CREATE TABLE IF NOT EXISTS `containers` (
  `id` varchar(100) NOT NULL,
  `societe_id` varchar(100) DEFAULT NULL,
  `nature_contenu` varchar(100) DEFAULT NULL,
  `capacite` int(11) DEFAULT NULL,
  `dangers` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `societeid_fk` (`societe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `containers`
--

INSERT INTO `containers` (`id`, `societe_id`, `nature_contenu`, `capacite`, `dangers`) VALUES
('C279', 'REEV', 'Electronique', 15, 'Aucun'),
('C467', 'REEV', 'Chimique', 16, 'Inflammable'),
('C7845s', 'CLIS', 'Charbon', 6, 'Inflammable'),
('C891', 'REEV', 'Alimentaire', 19, 'Aucuns'),
('CAT23', 'CLIS', 'Alimentation', 3, 'Perissable'),
('CAT54', 'AMAZON', 'Electronique', 4, 'Fragile'),
('CAT65', 'HALO', 'Mobilier', 1, 'Fragile'),
('CATb4', 'REEV', 'Alimentaire', 4, 'Perissable'),
('CATn39', 'AMAZON', 'Electonique', 2, 'Fragile'),
('Cres01', 'HALO', 'ElectroMenager', 5, 'Fragile'),
('Cres02', 'CLIS', 'Mobilier', 4, 'Fragile'),
('Cres03', 'AMAZON', 'Gaming', 2, 'Fragile'),
('Ctest01', 'CLIS', 'Electronique', 13, 'Aucun'),
('Ctest02', 'CLIS', 'Meubles', 23, 'Aucuns'),
('Cweb01', 'CLIS', 'Gaming', 4, 'Fragile'),
('Cweb02', 'AMAZON', 'Produits chimiques', 2, 'Inflammable'),
('Cweb03', 'HALO', 'Alimentaire', 3, 'Perissable'),
('Cweb04', 'REEV', 'Electronique', 1, 'Aucun'),
('Cweb07', 'HALO', 'Alimentaire', 1, 'Perissable'),
('Cweb08', 'CLIS', 'Gaming', 3, 'Fragile'),
('CWEB51', 'AMAZON', 'Electronique', 3, 'Aucun');

-- --------------------------------------------------------

--
-- Structure de la table `destinations`
--

DROP TABLE IF EXISTS `destinations`;
CREATE TABLE IF NOT EXISTS `destinations` (
  `ville` varchar(100) NOT NULL,
  `distance_bateau` float DEFAULT NULL,
  `distance_train` float DEFAULT NULL,
  `distance_route` float DEFAULT NULL,
  PRIMARY KEY (`ville`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `destinations`
--

INSERT INTO `destinations` (`ville`, `distance_bateau`, `distance_train`, `distance_route`) VALUES
('Anvers', 0, 0, 0),
('Boncelles', 0, 0, 0),
('Dinant', 0, 0, 0),
('Flemalle', 0, 0, 0),
('Hasselt', 0, 0, 0),
('Huy', 0, 0, 0),
('Liege', 0, 0, 0),
('Maastricht', 0, 0, 0),
('Namur', 0, 0, 0),
('Seraing', 0, 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `mouvements`
--

DROP TABLE IF EXISTS `mouvements`;
CREATE TABLE IF NOT EXISTS `mouvements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `container_id` varchar(100) DEFAULT NULL,
  `transport_entrant` varchar(100) DEFAULT NULL,
  `date_arriv` date DEFAULT NULL,
  `transport_sortant` varchar(100) DEFAULT NULL,
  `poids` int(11) DEFAULT NULL,
  `date_depart` date DEFAULT NULL,
  `destination` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `containerid_fk2` (`container_id`),
  KEY `transportid_fk1` (`transport_entrant`),
  KEY `transportid_fk2` (`transport_sortant`),
  KEY `destination_fk` (`destination`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `mouvements`
--

INSERT INTO `mouvements` (`id`, `container_id`, `transport_entrant`, `date_arriv`, `transport_sortant`, `poids`, `date_depart`, `destination`) VALUES
(1, 'Ctest01', '1-ABC-456', '2020-10-18', 'TR473', 13, '2020-10-21', 'Flemalle'),
(2, 'Ctest02', '1-DEF-789', '2020-10-25', NULL, 19, NULL, 'Flemalle'),
(3, 'CAT54', '1-aaa-zae', '2020-11-02', NULL, 15, NULL, 'Boncelles'),
(4, 'CAT23', '1-bbb-rti', '2020-11-02', NULL, 8, NULL, 'Huy'),
(5, 'CAT65', '1-ccc-874', '2020-11-02', NULL, 3, NULL, 'Boncelles'),
(7, 'CATb4', '1-ddd-111', '2020-11-02', NULL, 12, NULL, 'Liege'),
(8, 'CATn39', '1-fff-222', '2020-11-02', NULL, 6, NULL, 'Boncelles'),
(9, 'Cres01', '1-ooo-000', '2020-11-02', NULL, 10, NULL, 'Hasselt'),
(11, 'Cres03', '1-RRR-213', '2020-11-02', NULL, 5, NULL, 'Boncelles'),
(12, 'C7845s', '1-ppp-888', '2020-11-02', NULL, 10, NULL, 'Seraing'),
(14, 'Cres02', '1-WWW-999', '2020-11-03', NULL, 4, NULL, 'Hasselt');

-- --------------------------------------------------------

--
-- Structure de la table `parc`
--

DROP TABLE IF EXISTS `parc`;
CREATE TABLE IF NOT EXISTS `parc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coordonees` varchar(100) NOT NULL,
  `container_id` varchar(100) DEFAULT NULL,
  `etat_emplacement` char(1) NOT NULL DEFAULT '0',
  `date_reserv` date DEFAULT NULL,
  `date_arriv` date DEFAULT NULL,
  `poids` int(11) DEFAULT NULL,
  `destination` varchar(100) DEFAULT NULL,
  `type_transport` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `coord_unique` (`coordonees`),
  UNIQUE KEY `containerid_fk` (`container_id`) USING BTREE,
  KEY `destination_fk2` (`destination`),
  KEY `typetransport_fk` (`type_transport`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `parc`
--

INSERT INTO `parc` (`id`, `coordonees`, `container_id`, `etat_emplacement`, `date_reserv`, `date_arriv`, `poids`, `destination`, `type_transport`) VALUES
(3, '1,1', 'Ctest01', '2', NULL, '2020-10-23', 15, 'Boncelles', NULL),
(4, '1,2', 'Ctest02', '2', NULL, '2020-10-25', 19, 'Flemalle', NULL),
(5, '1,3', 'CATb4', '2', NULL, '2020-11-02', 12, 'Liege', NULL),
(6, '1,4', 'CATn39', '2', NULL, '2020-11-02', 6, 'Boncelles', NULL),
(7, '1,5', 'C7845s', '2', NULL, '2020-11-02', 10, 'Seraing', NULL),
(9, '2,2', 'Cres01', '2', '2020-11-02', '2020-11-02', 10, 'Hasselt', NULL),
(10, '2,3', 'Cweb01', '1', '2020-12-01', NULL, NULL, 'Boncelles', NULL),
(11, '2,4', 'C467', '2', '2020-10-20', '2020-10-23', 16, 'Anvers', NULL),
(12, '2,5', 'Cweb02', '1', '2020-12-01', NULL, NULL, 'Maastricht', NULL),
(13, '3,1', 'Cres02', '2', '2020-11-02', '2020-11-03', 4, 'Hasselt', NULL),
(14, '3,2', 'Cweb03', '1', '2020-12-01', NULL, NULL, 'Huy', NULL),
(15, '3,3', 'Cweb04', '1', '2020-12-01', NULL, NULL, 'Dinant', NULL),
(16, '3,4', 'Cweb08', '1', '2020-12-01', NULL, NULL, 'Flemalle', NULL),
(17, '3,5', 'C279', '2', '2020-10-15', '2020-10-18', 13, 'Flemalle', NULL),
(18, '4,1', 'Cweb07', '1', '2020-12-01', NULL, NULL, 'Maastricht', NULL),
(19, '4,2', 'CWEB51', '1', '2020-12-04', NULL, NULL, 'Seraing', NULL),
(20, '4,3', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(21, '4,4', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(22, '4,5', 'Cres03', '2', '2020-11-02', '2020-11-02', 5, 'Boncelles', NULL),
(23, '5,1', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(24, '5,2', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(25, '5,3', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(26, '5,4', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(27, '5,5', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(28, '6,1', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(29, '6,2', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(30, '6,3', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(31, '6,4', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(32, '6,5', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(33, '7,1', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(34, '7,2', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(35, '7,3', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(36, '7,4', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(37, '7,5', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(38, '8,1', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(39, '8,2', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(40, '8,3', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(41, '8,4', NULL, '0', NULL, NULL, NULL, NULL, NULL),
(42, '8,5', 'C891', '2', '2020-10-11', '2020-10-25', 23, 'Namur', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
CREATE TABLE IF NOT EXISTS `reservations` (
  `num_reserv` varchar(100) NOT NULL,
  `container_id` varchar(100) DEFAULT NULL,
  `destination` varchar(100) DEFAULT NULL,
  `etat` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`num_reserv`),
  KEY `destination_fk3` (`destination`),
  KEY `container_fk3` (`container_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `reservations`
--

INSERT INTO `reservations` (`num_reserv`, `container_id`, `destination`, `etat`) VALUES
('Res01', 'Cres01', 'Hasselt', 1),
('Res02', 'Cres02', 'Hasselt', 1),
('Res03', 'Cres03', 'Boncelles', 1),
('ResWeb01', 'Cweb01', 'Boncelles', 0),
('ResWeb02', 'Cweb02', 'Maastricht', 0),
('ResWeb03', 'Cweb03', 'Huy', 0),
('ResWeb04', 'Cweb04', 'Dinant', 0),
('ResWeb7', 'Cweb08', 'Flemalle', 0),
('ResWeb8', 'Cweb07', 'Maastricht', 0),
('ResWeb9', 'CWEB51', 'Seraing', 0);

-- --------------------------------------------------------

--
-- Structure de la table `societes`
--

DROP TABLE IF EXISTS `societes`;
CREATE TABLE IF NOT EXISTS `societes` (
  `id` varchar(100) NOT NULL,
  `nom_contact` varchar(100) DEFAULT NULL,
  `mail_contact` varchar(100) DEFAULT NULL,
  `tel_contact` varchar(100) DEFAULT NULL,
  `adresse` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `societes`
--

INSERT INTO `societes` (`id`, `nom_contact`, `mail_contact`, `tel_contact`, `adresse`) VALUES
('AMAZON', 'Jeff Bezos', 'jeff.bezos@gmail.com', '+32412345678', 'rue Peetermans 80, 4100 Seraing'),
('CLIS', 'Isen Claes', 'isen.claes@gmail.com', '+32412345678', 'rue Wagner 27, 4100 Boncelles'),
('HALO', 'Loic Hauseux', 'hauseux.loic@gmail.com', '+32412345678', 'rue Peetermans 80, 4100 Seraing'),
('REEV', 'Regis Evrard', 'regis.evrard@gmail.com', '+32412345678', 'rue Peetermans 80, 4100 Seraing');

-- --------------------------------------------------------

--
-- Structure de la table `transporteurs`
--

DROP TABLE IF EXISTS `transporteurs`;
CREATE TABLE IF NOT EXISTS `transporteurs` (
  `id` varchar(100) NOT NULL,
  `societe_id` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `capacite` int(11) DEFAULT NULL,
  `caracteristiques` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `societeid_fk2` (`societe_id`),
  KEY `type_fk` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `transporteurs`
--

INSERT INTO `transporteurs` (`id`, `societe_id`, `type`, `capacite`, `caracteristiques`) VALUES
('1-aaa-zae', 'AMAZON', 'CAMION', 9, 'Long'),
('1-ABC-456', 'CLIS', 'CAMION', 2, NULL),
('1-bbb-rti', 'CLIS', 'CAMION', 4, 'Large'),
('1-ccc-874', 'HALO', 'CAMION', 1, 'Petit'),
('1-ddd-111', 'REEV', 'CAMION', 6, 'Large'),
('1-DEF-789', 'HALO', 'CAMION', 1, 'null'),
('1-fff-222', 'AMAZON', 'CAMION', 3, 'Petit'),
('1-ooo-000', 'REEV', 'CAMION', 12, 'Tres long'),
('1-ppp-888', 'CLIS', 'CAMION', 7, 'Large'),
('1-RRR-213', 'AMAZON', 'CAMION', 2, 'Petit'),
('1-WWW-999', 'HALO', 'CAMION', 4, 'Petit'),
('1-ZZZ-888', 'REEV', 'CAMION', 6, 'Large'),
('TR473', 'REEV', 'TRAIN', 10, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `type_transporteur`
--

DROP TABLE IF EXISTS `type_transporteur`;
CREATE TABLE IF NOT EXISTS `type_transporteur` (
  `type` varchar(100) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `type_transporteur`
--

INSERT INTO `type_transporteur` (`type`) VALUES
('BATEAU'),
('CAMION'),
('TRAIN');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `login` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`login`, `password`) VALUES
('Isen', '123'),
('Test', '123'),
('Loic', '123'),
('Calmant', '123'),
('Claes', '123'),
('Regis', '123'),
('Test2', '123');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `containers`
--
ALTER TABLE `containers`
  ADD CONSTRAINT `societeid_fk` FOREIGN KEY (`societe_id`) REFERENCES `societes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `mouvements`
--
ALTER TABLE `mouvements`
  ADD CONSTRAINT `containerid_fk2` FOREIGN KEY (`container_id`) REFERENCES `containers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `destination_fk` FOREIGN KEY (`destination`) REFERENCES `destinations` (`ville`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transportid_fk1` FOREIGN KEY (`transport_entrant`) REFERENCES `transporteurs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transportid_fk2` FOREIGN KEY (`transport_sortant`) REFERENCES `transporteurs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `parc`
--
ALTER TABLE `parc`
  ADD CONSTRAINT `containerid_fk` FOREIGN KEY (`container_id`) REFERENCES `containers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `destination_fk2` FOREIGN KEY (`destination`) REFERENCES `destinations` (`ville`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `typetransport_fk` FOREIGN KEY (`type_transport`) REFERENCES `type_transporteur` (`type`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `container_fk3` FOREIGN KEY (`container_id`) REFERENCES `containers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `destination_fk3` FOREIGN KEY (`destination`) REFERENCES `destinations` (`ville`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `transporteurs`
--
ALTER TABLE `transporteurs`
  ADD CONSTRAINT `societeid_fk2` FOREIGN KEY (`societe_id`) REFERENCES `societes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `type_fk` FOREIGN KEY (`type`) REFERENCES `type_transporteur` (`type`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
