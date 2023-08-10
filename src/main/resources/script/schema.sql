
-- create user table------------------------------------
create table user_tb(
                        id serial primary key ,
                        email varchar(100) not null ,
                        password text not null ,
                        role varchar(50) default 'ROLE_USER'
);

insert into user_tb(email, password, role)
values ('admin@gmail.com', '$2a$10$x5XcIoEm.10ksszDuxcApec3auwtlV4KpSBZFo1OljaXs.Afq2xqu', 'ROLE_USER'),
       ('user@gmail.com', '$2a$10$x5XcIoEm.10ksszDuxcApec3auwtlV4KpSBZFo1OljaXs.Afq2xqu', 'ROLE_USER');

-- create table task  ---------------------------------
create table category_tb(
                            id serial primary key ,
                            name varchar(255) not null ,
                            date timestamp default current_date,
                            user_id int references user_tb(id) on UPDATE cascade on DELETE cascade
);

insert into category_tb(name, user_id)
values ('hrd task', 1);

-- create table task ---------------------------------------
create table task_tb(
                        id serial primary key ,
                        name varchar(255) not null ,
                        description text ,
                        date date default current_date ,
                        status varchar(100) not null ,
                        user_id int references user_tb(id) on update cascade on delete cascade,
                        category_id int references category_tb(id) on update cascade on delete cascade
);

insert into task_tb(name, description, status, user_id, category_id)
values ('do spring mini project', 'create api end point', 'is_in_progress', 1, 1);
