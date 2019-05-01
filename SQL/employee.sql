-- this is a sql scipt to set up the employee table and insert one record
use moviedb;
create table IF NOT EXISTS employees(
	`email` varchar(50) not null primary key,
	`password` varchar(20) not null,
    `fullname` varchar(100)
)charset utf8mb4;

insert into employees(email, password, fullname) values ('classta@email.edu','classta', 'TA CS122B');