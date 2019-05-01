create database IF NOT EXISTS moviedb;
use moviedb;

-- create table movies
create table IF NOT EXISTS movies(
	`id` varchar(10) not null primary key,
	`title` varchar(100) not null,
    `year` int not null,
    `director` varchar(100) not null
)charset utf8mb4;

-- create table stars
create table IF NOT EXISTS stars(
	`id` varchar(10) not null primary key,
	`name` varchar(100) not null,
    `birthYear` int
)charset utf8mb4;

-- create table stars_in_movies
create table IF NOT EXISTS stars_in_movies(
	`starId` varchar(10) not null,
	`movieId` varchar(10) not null,
    FOREIGN KEY (`starId`) references `stars` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`movieId`) references `movies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)charset utf8mb4;

-- create table genres
create table IF NOT EXISTS genres(
	`id` int not null primary key auto_increment,
	`name` varchar(32) not null
)charset utf8mb4;

-- create table genres
create table IF NOT EXISTS genres_in_movies(
	`genreId` int not null,
	`movieId` varchar(10) not null,
    FOREIGN KEY (`genreId`) references `genres` (`id`) ON DELETE CASCADE ON UPDATE CASCADE, 
    FOREIGN KEY (`movieId`) references `movies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)charset utf8mb4;

-- create table creditcards
create table IF NOT EXISTS creditcards(
	`id` varchar(20) not null primary key,
    `firstName` varchar(50) not null,
    `lastName` varchar(50) not null,
    `expiration` date not null
)charset utf8mb4;

-- create table customers
create table IF NOT EXISTS customers(
	`id` int not null primary key auto_increment,
    `firstName` varchar(50) not null,
    `lastName` varchar(50) not null,
    `ccId` varchar(20) not null,
    `address` varchar(200) not null,
    `email` varchar(50) not null,
    `password` varchar(20) not null,
    FOREIGN KEY (`ccId`) references `creditcards` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)charset utf8mb4;

-- create table sales
create table IF NOT EXISTS sales(
	`id` int not null primary key auto_increment,
    `customerId` int not null,
    `movieId` varchar(10) not null,
    `saleDate` date not null,
    FOREIGN KEY (`customerId`) references `customers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`movieId`) references `movies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)charset utf8mb4;

-- create table ratings
create table IF NOT EXISTS ratings(
	`movieId` varchar(10) not null,
    `rating` float not null,
    `numVotes` int not null,
    FOREIGN KEY (`movieId`) references `movies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)charset utf8mb4;

