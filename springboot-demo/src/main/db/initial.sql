create table lufs_user
(
	id bigint auto_increment
		primary key,
	emp_no varchar(8) null,
	user_name varchar(45) not null,
	password char(32) null,
	user_address varchar(200) null,
	user_gender char null,
	date_of_birth date null,
	user_status char default '0' null,
	created_by varchar(45) null,
	create_timestamp datetime default CURRENT_TIMESTAMP null,
	updated_by varchar(45) null,
	update_timestamp datetime default CURRENT_TIMESTAMP null,
	constraint lufs_user_emp_no_uindex
		unique (emp_no)
)
;

create table lufs_dtalk_xref
(
	id bigint auto_increment
		primary key,
	emp_no varchar(8) not null,
	dtalk_userid varchar(64) null,
	constraint emp_no_UNIQUE
		unique (emp_no)
)
;

