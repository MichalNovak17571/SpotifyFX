-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hostiteľ: 127.0.0.1:3308
-- Čas generovania: Pi 17.Apr 2020, 21:06
-- Verzia serveru: 8.0.18
-- Verzia PHP: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáza: `spotify`
--

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `accounts`
--

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `birthdate` varchar(15) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Sťahujem dáta pre tabuľku `accounts`
--

INSERT INTO `accounts` (`id`, `username`, `password`, `birthdate`, `timestamp`) VALUES
(19, 'Teodor Navrátil', '$2a$12$oqx/eH4FGYgicHgU5p.TuOi1fVkuy2G2ALXiOYgiyyGQF7tJEdnR2', '17. 10. 1986', '2020-03-25 17:53:21'),
(22, 'MPaulíková', '$2a$12$OAFwI6jXO1YrPWjF0dqM1.cR/ErCYzumQV9EDin3JqjPGyDfQ34nO', '18. 6. 1815', '2020-03-25 19:12:23'),
(8, 'MichalN', '$2a$12$I/BtTruWRWhE.IArKvuuiuN14Qg6Eb5SLu6egfnUC3KyVKSho79te', '13. 3. 2002', '2020-03-09 19:55:59'),
(21, 'MartaRaisova', '$2a$12$Pye1/6aqyFC3xIZbKsH/beGEUlmdpzbIcS7n7Vsh/HHEasOfIfOAu', '29. 8. 1526', '2020-03-25 18:38:32');

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `band_detail`
--

DROP TABLE IF EXISTS `band_detail`;
CREATE TABLE IF NOT EXISTS `band_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `band_name` varchar(50) NOT NULL,
  `year` int(4) NOT NULL,
  `colour` varchar(30) NOT NULL,
  `state` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Sťahujem dáta pre tabuľku `band_detail`
--

INSERT INTO `band_detail` (`id`, `band_name`, `year`, `colour`, `state`) VALUES
(1, 'The Rolling Stones', 1962, 'Black', 'England'),
(2, 'Led Zeppelin', 1968, 'Red', 'England'),
(3, 'John Denver', 1962, 'Blue', 'USA'),
(4, 'Morandi', 2005, 'White', 'Romania'),
(5, 'Queen', 1970, 'Brown', 'England'),
(6, 'Omega', 1962, 'Purple', 'Hungary'),
(7, 'PINKFONG', 2010, 'Orange', 'South Korea'),
(8, 'KOLLÁROVCI', 1997, 'Silver', 'Slovakia'),
(9, 'Bon Jovi', 1983, 'Gray', 'USA');

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `songs`
--

DROP TABLE IF EXISTS `songs`;
CREATE TABLE IF NOT EXISTS `songs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `song` varchar(50) NOT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Sťahujem dáta pre tabuľku `songs`
--

INSERT INTO `songs` (`id`, `name`, `song`, `id_user`) VALUES
(45, 'PINKFONG', 'Baby Shark Dance', 19),
(5, 'Led Zeppelin', 'Stairway to Heaven', 8),
(6, 'Deep Purple', 'Child in Time', 8),
(7, 'Lynyrd Skynyrd', 'Free Bird', 8),
(8, 'John Denver', 'Take Me Home, Country Roads', 8),
(9, 'Drunken Sailer', 'Irish Rovers', 8),
(10, 'Boney M.', 'Rasputin', 8),
(11, 'The Rolling Stones', 'Sympathy For The Devil', 8),
(12, 'The Rolling Stones', 'Paint It, Black', 8),
(13, 'Uriah Heep', 'Lady In Black', 8),
(14, 'High Hopes', 'Pink Floyd', 8),
(15, 'Morandi', 'Angels ', 8),
(16, 'Red Hot Chili Peppers', 'Otherside', 8),
(17, 'Queen', 'The Show Must Go On', 8),
(18, 'Michael Jackson', 'They Don’t Care About Us', 8),
(19, 'Loituma', 'Ievan Polkka', 8),
(20, 'El Profesor', 'Bella Ciao', 8),
(21, 'Metallica', 'Nothing Else Matters', 8),
(22, 'Scorpions', 'Send Me An Angel', 8),
(23, 'Omega', 'Gyöngyhajú lány', 8),
(24, 'Evanescence', 'Bring Me To Life', 8),
(25, 'Billy Barman', 'SLOBODA', 8),
(26, 'Hečkovci', 'Taliansky muzikál', 8),
(27, 'Eiffel 65', 'Blue', 8),
(28, 'Gotye', 'Somebody That I Used To Know', 8),
(29, 'R.E.M.', 'Losing My Religion', 8),
(30, 'Peter Nagy', 'Poďme sa zachrániť', 8),
(31, 'O-Zone', 'Dragostea din tei', 8),
(32, 'Bon Jovi', 'Livin On A Prayer', 8),
(33, 'Peter Bič Project', 'Skúšame sa nájsť', 8),
(34, 'Kristina', 'Horehronie', 8),
(35, 'Bon Jovi', 'Its My Life', 8),
(36, 'Michael Jackson', 'Smooth Criminal', 8),
(48, 'Miro Jaroš', 'ČISTÉ RÚČKY', 19),
(47, 'Miro Jaroš', 'POĎ CVIČIŤ!', 19),
(46, 'FÍHA tralala', 'BUM BÁC', 19),
(49, 'Smejko a Tanculienka', 'Sestričky Farbičky', 19),
(50, 'LooLoo Kids', 'Johny Johny Yes Papa', 19),
(51, 'TONES AND I', 'DANCE MONKEY', 19),
(52, 'Kristína', 'Ta ne', 19),
(53, 'Naty Hrychová', 'Pátá', 19),
(54, 'Naty Hrychová', 'Moje matematika', 19),
(55, 'Luis Fonsi', 'Despacito', 19),
(56, 'PULCINO PIO', 'Das Kleine Küken Piept', 21),
(57, 'TextTest', 'ستايسي وأبي أغنية الفاكهة للأطفال', 21),
(58, 'PIKOTARO', 'PPAP', 21),
(59, 'Aqua', 'Barbie Girl', 21),
(60, 'Ed Sheeran', 'Shape of You', 21),
(61, 'Celine Dion', 'My Heart Will Go On', 21),
(62, 'Maduar', 'Hafanana', 21),
(63, 'Darina Rolincová', 'Až raz budem učitelkou', 21),
(64, 'Michaela', 'So mnou nikdy nezostarnes', 21),
(65, 'Barbara', 'Hral na trubku', 21),
(66, 'Twiins', 'Tak sa neboj mama', 21),
(67, 'Kristina', 'V sieti ta mam', 21),
(68, 'Adam Ďurica', 'Mandolína', 21),
(69, 'Alvaro Soler', 'Sofia', 21),
(70, 'Barbora Poláková', 'Nafrněná', 21),
(71, 'Lucie', 'Chci zas v tobe spat', 22),
(72, 'MC Erik & Barbara', 'Sen', 22),
(73, 'Fontána pre Zuzanu', 'Nemožná', 22),
(74, 'MODUS', 'Sklíčka', 22),
(75, 'Boney M', 'Daddy Cool', 22),
(76, 'Michal David', 'Nonstop', 22),
(77, 'Helena Vondráčková', 'Dlouhá noc', 22),
(78, 'Kortina', 'Bzum-bzum breke-keke', 22),
(79, 'HRDZA', 'Taká sa mi páči', 22),
(80, 'Hrdza', 'Štefan', 22),
(81, 'Ščamba', 'Nikdo na mne nemá', 22),
(82, 'HEĽENINE OČI', 'OBCHÁDZKA', 22),
(83, 'Mafia Corner', 'Zuzulienka', 22),
(84, 'Mafia Corner & Stefi', 'A ja taka Dzivočka', 22),
(85, 'KOLLÁROVCI', 'Ide furman dolinou', 22);

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `users_activity`
--

DROP TABLE IF EXISTS `users_activity`;
CREATE TABLE IF NOT EXISTS `users_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `state` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Sťahujem dáta pre tabuľku `users_activity`
--

INSERT INTO `users_activity` (`id`, `username`, `state`, `timestamp`) VALUES
(80, 'Teodor navratil', 'Log out', '2020-04-17 20:54:37'),
(79, 'Teodor navratil', 'Log in', '2020-04-17 20:51:30'),
(78, 'MPaulíková', 'Log out', '2020-04-17 20:50:55'),
(77, 'MPaulíková', 'Log in', '2020-04-17 20:46:12'),
(76, 'MartaRaisova', 'Log out', '2020-04-17 20:45:16'),
(75, 'MartaRaisova', 'Log in', '2020-04-17 20:40:28'),
(74, 'MPaulikova', 'Log out', '2020-04-17 20:39:47'),
(73, 'MPaulikova', 'Log in', '2020-04-17 20:36:54'),
(72, 'MartaRaisova', 'Log out', '2020-04-17 20:35:31'),
(71, 'MartaRaisova', 'Log in', '2020-04-17 20:32:01'),
(70, 'Teodor Navratil', 'Log out', '2020-04-17 20:31:46'),
(69, 'Teodor Navratil', 'Log in', '2020-04-17 20:29:18'),
(68, 'MichalN', 'Log out', '2020-04-17 20:28:49'),
(67, 'MichalN', 'Log in', '2020-04-17 20:27:41'),
(66, 'MichalN', 'Log out', '2020-04-17 20:27:26'),
(65, 'MichalN', 'Log in', '2020-04-17 20:24:45');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
