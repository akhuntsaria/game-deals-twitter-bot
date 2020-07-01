create table post_history
(
    id bigserial not null
        constraint post_history_pk
        primary key,
    posted_at timestamp with time zone not null,
    reddit_post_full_name varchar(256) not null
);

create unique index post_history_reddit_post_full_name_uindex
    on post_history (reddit_post_full_name);
