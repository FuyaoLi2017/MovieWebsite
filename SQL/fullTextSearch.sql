-- create the inverted index of full text search
use moviedb;

ALTER TABLE `movies` ADD FULLTEXT INDEX title_index  (`title`);