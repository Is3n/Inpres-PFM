-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  sam. 02 jan. 2021 à 14:12
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
-- Base de données :  `bd_compta`
--

-- --------------------------------------------------------

--
-- Structure de la table `factures`
--

DROP TABLE IF EXISTS `factures`;
CREATE TABLE IF NOT EXISTS `factures` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `societe_id` varchar(100) NOT NULL,
  `date_facture` date NOT NULL,
  `montantHTVA` double DEFAULT NULL,
  `montantTVAC` double DEFAULT NULL,
  `facture_validee` int(11) NOT NULL,
  `validateur_id` int(11) DEFAULT NULL,
  `facture_envoyee` int(11) NOT NULL,
  `type_envoi` varchar(50) DEFAULT NULL,
  `facture_payee` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `factures`
--

INSERT INTO `factures` (`id`, `societe_id`, `date_facture`, `montantHTVA`, `montantTVAC`, `facture_validee`, `validateur_id`, `facture_envoyee`, `type_envoi`, `facture_payee`) VALUES
(1, 'CLIS', '2020-12-28', 67.91, 82.17, 1, 1, 0, NULL, 0),
(2, 'AMAZON', '2021-01-01', 29.08, 35.19, 0, NULL, 0, NULL, 0),
(3, 'CLIS', '2021-01-01', 17.81, 21.55, 0, NULL, 0, NULL, 0),
(4, 'HALO', '2021-01-01', 15.27, 18.48, 0, NULL, 0, NULL, 0);

-- --------------------------------------------------------

--
-- Structure de la table `items_facture`
--

DROP TABLE IF EXISTS `items_facture`;
CREATE TABLE IF NOT EXISTS `items_facture` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `facture_id` int(11) NOT NULL,
  `mouvement_id` int(11) NOT NULL,
  `container_id` varchar(100) NOT NULL,
  `destination` varchar(100) NOT NULL,
  `prixHTVA` double NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `items_facture`
--

INSERT INTO `items_facture` (`item_id`, `facture_id`, `mouvement_id`, `container_id`, `destination`, `prixHTVA`, `type`) VALUES
(1, 1, 1, 'Ctest01', 'Flemalle', 9.75, 'in'),
(2, 1, 2, 'Ctest02', 'Flemalle', 14.25, 'in'),
(3, 2, 3, 'CAT54', 'Boncelles', 11.25, 'in'),
(4, 3, 4, 'CAT23', 'Huy', 6, 'in'),
(5, 4, 5, 'CAT65', 'Boncelles', 3.15, 'in'),
(6, 1, 1, 'Ctest01', 'Flemalle', 15.6, 'out'),
(7, 1, 2, 'Ctest02', 'Flemalle', 22.8, 'out'),
(8, 2, 3, 'CAT54', 'Boncelles', 12.9, 'out'),
(9, 3, 4, 'CAT23', 'Huy', 6.88, 'out'),
(10, 4, 5, 'CAT65', 'Boncelles', 3.9, 'out'),
(11, 1, 1, 'Ctest01', 'Flemalle', 0.25, 'occupation'),
(12, 1, 2, 'Ctest02', 'Flemalle', 5.26, 'occupation'),
(13, 2, 3, 'CAT54', 'Boncelles', 4.93, 'occupation'),
(14, 3, 4, 'CAT23', 'Huy', 4.93, 'occupation'),
(15, 4, 5, 'CAT65', 'Boncelles', 8.22, 'occupation');

-- --------------------------------------------------------

--
-- Structure de la table `personnel`
--

DROP TABLE IF EXISTS `personnel`;
CREATE TABLE IF NOT EXISTS `personnel` (
  `matricule` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `login` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `mail` varchar(100) NOT NULL,
  `fonction` varchar(100) NOT NULL,
  PRIMARY KEY (`matricule`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `personnel`
--

INSERT INTO `personnel` (`matricule`, `nom`, `prenom`, `login`, `password`, `mail`, `fonction`) VALUES
(1, 'Claes', 'Isen', 'Isen', '123', 'isen@rti.com', 'CEO'),
(2, 'Calmant', 'Sebastien', 'Calmant', '123', 'calmant@rti.com', 'Chef comptable'),
(3, 'Hauseux', 'Loic', 'Loic', '123', 'loic@rti.com', 'Comptable'),
(4, 'Evrard', 'Regis', 'Regis', '123', 'regis@rti.com', 'Comptable'),
(5, 'Wilvers', 'Alexandre', 'Alex', '123', 'alex@rti.com', 'manutentionnaire'),
(6, 'Meulenberg', 'Thomas', 'Thomas', '123', 'thomas@rti.com', 'manutentionnaire');

-- --------------------------------------------------------

--
-- Structure de la table `tarifs`
--

DROP TABLE IF EXISTS `tarifs`;
CREATE TABLE IF NOT EXISTS `tarifs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prix` double NOT NULL,
  `type` varchar(100) NOT NULL,
  `materiel` varchar(100) NOT NULL,
  `petrol` int(11) NOT NULL,
  `last_update` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `tarifs`
--

INSERT INTO `tarifs` (`id`, `prix`, `type`, `materiel`, `petrol`, `last_update`) VALUES
(1, 0.86, 'manipulation', 'bateau', 0, '2020-12-24'),
(2, 1.3, 'manipulation', 'bateau', 1, '2020-12-24'),
(3, 0.75, 'manipulation', 'camion', 0, '2020-12-24'),
(4, 1.05, 'manipulation', 'camion', 1, '2020-12-24'),
(5, 1.2, 'manipulation', 'train', 0, '2020-12-24'),
(6, 1.7, 'manipulation', 'train', 1, '2020-12-24'),
(7, 30, 'occupation', 'pont roulant', 0, '2020-12-24'),
(8, 50, 'occupation', 'pont roulant', 1, '2020-12-24');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
