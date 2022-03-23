create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     NOT NULL
);

alter table resume
    owner to postgres;

create table contact
(
    id          serial
        constraint contact_pk
            primary key,
    type        text     not null,
    value       text     not null,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on delete cascade
);

alter table contact
    owner to postgres;

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

create table list_section
(
    id serial
        constraint list_section_pk
            primary key,
    value text,
    resume_uuid char(36) not null
        constraint list_section_resume_uuid_fk
            references resume
            on delete cascade,
    type text
);

alter table list_section owner to postgres;





