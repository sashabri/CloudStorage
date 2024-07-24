create table if not exists users_info (
                                     id serial primary key,
                                     login  varchar(50) not null,
                                     password varchar(10) not null
);
create table if not exists user_files_info (
                                    id serial primary key,
                                    file_name varchar(50) not null,
                                    user_id int references users_info(id),
                                    data bytea not null
)