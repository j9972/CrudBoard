# CrudBoard
๐์คํ๋ง์ผ๋ก ๋ง๋๋ CRUD ๊ฒ์ํ -> ์ฝ๋ ์ฌ์ฌ์ฉ์ฑ์ ์ํจ

# ๊ฐ๋ฐํ๊ฒฝ
1. IDE: IntelliJ IDEA Community
2. Spring Boot 
3. JDK 17
4. mysql
5. Spring Data JPA
6. Thymeleaf

# ๊ฒ์ํ ์ฃผ์๊ธฐ๋ฅ
1. ๊ธ์ฐ๊ธฐ(/board/save)
2. ๊ธ๋ชฉ๋ก(/board/)
3. ๊ธ์กฐํ(/board/{id})
4. ๊ธ์์ (/board/update/{id})
    - ์์ธํ๋ฉด์์ ์์  ๋ฒํผ ํด๋ฆญ
    - ์๋ฒ์์ ํด๋น ๊ฒ์๊ธ์ ์ ๋ณด๋ฅผ ๊ฐ์ง๊ณ  ์์  ํ๋ฉด ์ถ๋ ฅ
    - ์ ๋ชฉ, ๋ด์ฉ ์์  ์๋ ฅ ๋ฐ์์ ์๋ฒ๋ก ์์ฒญ
    - ์์  ์ฒ๋ฆฌ
5. ๊ธ์ญ์ (/board/delete/{id})
6. ํ์ด์ง์ฒ๋ฆฌ(/board/paging)
    - /board/paging?page=2
    - /board/paging/2
    - ๊ฒ์๊ธ 14
        - ํํ์ด์ง์ 5๊ฐ์ฉ => 3๊ฐ
        - ํํ์ด์ง์ 3๊ฐ์ฉ => 5๊ฐ
7. ํ์ผ(์ด๋ฏธ์ง)์ฒจ๋ถํ๊ธฐ
    - ๋จ์ผ ํ์ผ ์ฒจ๋ถ
    - ๋ค์ค ํ์ผ ์ฒจ๋ถ
    - ํ์ผ ์ฒจ๋ถ์ ๊ด๋ จํ์ฌ ์ถ๊ฐ๋  ๋ถ๋ถ๋ค
        - save.html
        - BoardDTO
        - BoardService.save()
        - BoardEntity
        - BoardFileEntity, BoardFileRepository ์ถ๊ฐ
        - detail.html

    - board_table(๋ถ๋ชจ) - board_file_table(์์)
```
create table board_table
(
id             bigint auto_increment primary key,
created_time   datetime     null,
updated_time   datetime     null,
board_contents varchar(500) null,
board_hits     int          null,
board_pass     varchar(255) null,
board_title    varchar(255) null,
board_writer   varchar(20)  not null,
file_attached  int          null
);
```
```
create table board_file_table
(
id                 bigint auto_increment primary key,
created_time       datetime     null,
updated_time       datetime     null,
original_file_name varchar(255) null,
stored_file_name   varchar(255) null,
board_id           bigint       null,
constraint FKcfxqly70ddd02xbou0jxgh4o3
    foreign key (board_id) references board_table (id) on delete cascade
);
```

8. ๋๊ธ ์ฒ๋ฆฌํ๊ธฐ
    - ๊ธ ์์ธ ํ์ด์ง์์ ๋๊ธ ์๋ ฅ ( ajax )
    - ์์ธ์กฐํํ  ๋ ๊ธฐ์กด์ ์์ฑ๋ ๋๊ธ ๋ชฉ๋ก์ด ๋ณด์
    - ๋๊ธ์ ์๋ ฅํ๋ฉด ๊ธฐ์กด ๋๊ธ ๋ชฉ๋ก์ ์๋ก ์์ฑํ ๋๊ธ ์ถ๊ฐ
    - ๋๊ธ์ฉ ํ์ด๋ธ ํ์



9. mysql DataBase ๊ณ์  ์์ฑ ๋ฐ ๊ถํ ๋ถ์ฌ
```
create database db_codingrecipe;
create user user_codingrecipe@localhost identified by '1234';
grant all privileges on db_codingrecipe.* to user_codingrecipe@localhost;
```
