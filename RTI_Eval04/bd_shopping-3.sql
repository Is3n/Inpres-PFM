-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 20 déc. 2020 à 17:01
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
-- Base de données :  `bd_shopping`
--

-- --------------------------------------------------------

--
-- Structure de la table `achats`
--

DROP TABLE IF EXISTS `achats`;
CREATE TABLE IF NOT EXISTS `achats` (
  `idVente` int(11) NOT NULL AUTO_INCREMENT,
  `nomUser` varchar(100) NOT NULL,
  `idArticle` varchar(100) NOT NULL,
  `quantite` int(11) NOT NULL,
  `montant` float NOT NULL,
  `dateVente` date NOT NULL,
  PRIMARY KEY (`idVente`),
  KEY `fk_nomUser` (`nomUser`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `achats`
--

INSERT INTO `achats` (`idVente`, `nomUser`, `idArticle`, `quantite`, `montant`, `dateVente`) VALUES
(1, 'Isen', 'A1', 1, 35, '2020-12-04'),
(2, 'Isen', 'A6', 2, 35, '2020-12-04'),
(3, 'Claes', 'V2', 2, 30, '2020-12-04'),
(4, 'Claes', 'A3', 1, 30, '2020-12-04'),
(5, 'Claes', 'A4', 1, 7.5, '2020-12-04'),
(6, 'Test', 'A1', 1, 30, '2020-12-04'),
(7, 'Test', 'A4', 2, 30, '2020-12-04'),
(8, 'Test', 'V1', 2, 30, '2020-12-04');

-- --------------------------------------------------------

--
-- Structure de la table `articles`
--

DROP TABLE IF EXISTS `articles`;
CREATE TABLE IF NOT EXISTS `articles` (
  `idArticle` varchar(100) NOT NULL,
  `nom` varchar(150) NOT NULL,
  `prix` float NOT NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY (`idArticle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `articles`
--

INSERT INTO `articles` (`idArticle`, `nom`, `prix`, `stock`) VALUES
('A1', 'Plan du site', 5, 97),
('A2', 'Guide des insectes', 10, 50),
('A3', 'Guide du randonneur', 10, 49),
('A4', 'Chapeau', 7.5, 24),
('A5', 'Peluche Hibou', 12.5, 30),
('A6', 'Peluche Cerf', 15, 28);

-- --------------------------------------------------------

--
-- Structure de la table `promesses`
--

DROP TABLE IF EXISTS `promesses`;
CREATE TABLE IF NOT EXISTS `promesses` (
  `idPromesse` int(100) NOT NULL AUTO_INCREMENT,
  `idArticle` varchar(100) NOT NULL,
  `quantite` int(11) NOT NULL,
  `datePromesse` datetime NOT NULL,
  `userName` varchar(100) NOT NULL,
  PRIMARY KEY (`idPromesse`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `login` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`login`, `password`) VALUES
('Calmant', '123'),
('Claes', '123'),
('Isen', '123'),
('Test', '456');

-- --------------------------------------------------------

--
-- Structure de la table `visites`
--

DROP TABLE IF EXISTS `visites`;
CREATE TABLE IF NOT EXISTS `visites` (
  `idVisite` varchar(100) NOT NULL,
  `nom` varchar(150) NOT NULL,
  `prix` float NOT NULL,
  `nbrPlaces` int(11) NOT NULL,
  `dateVisite` date NOT NULL,
  PRIMARY KEY (`idVisite`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `visites`
--

INSERT INTO `visites` (`idVisite`, `nom`, `prix`, `nbrPlaces`, `dateVisite`) VALUES
('V1', 'Parc de loisirs verts', 5, 48, '2020-12-05'),
('V2', 'Reserve naturelle', 10, 18, '2020-12-06');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `achats`
--
ALTER TABLE `achats`
  ADD CONSTRAINT `fk_nomUser` FOREIGN KEY (`nomUser`) REFERENCES `users` (`login`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
