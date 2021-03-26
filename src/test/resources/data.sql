insert into user (user_id ,username, password) values (1, 'userA', 'password');
insert into user (user_id, username, password) values (2, 'userB', 'password');
insert into user (user_id, username, password) values (3, 'userC', 'password');
insert into user (user_id, username, password) values (4, 'userD', 'password');
insert into user (user_id, username, password) values (5, 'userE', 'password');
insert into user (user_id, username, password) values (6, 'userF', 'password');
insert into user (user_id, username, password) values (7, 'userG', 'password');

-- PHOTO_ID  	CREATED_DATE  	LAST_MODIFIED_DATE  	CONTENT  	IMAGE_PATH  	USER_ID
 insert into photo (photo_id, content, image_path, user_id) values (1, '#tagA#tagB#tagC', 'imagePath', 1);
 insert into photo (photo_id, content, image_path, user_id) values (2, '#tagA#tagB', 'imagePath', 1);
 insert into photo (photo_id, content, image_path, user_id) values (3, '#tagC#tagD', 'imagePath', 1);
 insert into photo (photo_id, content, image_path, user_id) values (4, '#tagA#tagE', 'imagePath', 2);
 insert into photo (photo_id, content, image_path, user_id) values (5, '#tagE', 'imagePath', 2);
 insert into photo (photo_id, content, image_path, user_id) values (6, 'content6', 'imagePath', 3);
insert into tag(tag_id, name) values(1, 'tagA');
insert into tag(tag_id, name) values(2, 'tagB');
insert into tag(tag_id, name) values(3, 'tagC');
insert into tag(tag_id, name) values(4, 'tagD');
insert into tag(tag_id, name) values(5, 'tagE');
insert into photo_tag(photo_tag_id, photo_id, tag_id) values(1, 1, 1);
insert into photo_tag(photo_tag_id, photo_id, tag_id) values(2, 1, 2);
insert into photo_tag(photo_tag_id, photo_id, tag_id) values(3, 1, 3);
insert into photo_tag(photo_tag_id, photo_id, tag_id) values(4, 2, 1);
insert into photo_tag(photo_tag_id, photo_id, tag_id) values(5, 2, 2);
insert into photo_tag(photo_tag_id, photo_id, tag_id) values(6, 3, 3);
insert into photo_tag(photo_tag_id, photo_id, tag_id) values(7, 3, 4);
insert into photo_tag(photo_tag_id, photo_id, tag_id) values(8, 4, 1);
insert into photo_tag(photo_tag_id, photo_id, tag_id) values(9, 4, 5);
insert into photo_tag(photo_tag_id, photo_id, tag_id) values(10, 5, 5);
insert into follow(follow_id, who_id, whom_id) values(1, 1, 2);
insert into follow(follow_id, who_id, whom_id) values(2, 1, 3);
insert into follow(follow_id, who_id, whom_id) values(3, 1, 4);
insert into follow(follow_id, who_id, whom_id) values(4, 1, 5);
insert into follow(follow_id, who_id, whom_id) values(5, 2, 1);
insert into follow(follow_id, who_id, whom_id) values(6, 2, 3);
insert into follow(follow_id, who_id, whom_id) values(7, 2, 4);
insert into follow(follow_id, who_id, whom_id) values(8, 6, 5);
insert into follow(follow_id, who_id, whom_id) values(9, 7, 5);
insert into comment(comment_id, user_id, content, photo_id) values(1, 1, 'comment1' ,1);
insert into comment(comment_id, user_id, content, photo_id) values(2, 1, 'comment2' ,4);
insert into comment(comment_id, user_id, content, photo_id) values(3, 1, 'comment3' ,5);
insert into comment(comment_id, user_id, content, photo_id) values(4, 2, 'comment4' ,1);
insert into comment(comment_id, user_id, content, photo_id) values(5, 2, 'comment5' ,1);
insert into comment(comment_id, user_id, content, photo_id) values(6, 3, 'comment6' ,4);
insert into comment(comment_id, user_id, content, photo_id) values(7, 4, 'comment7' ,3);
-- insert into user (username, password) values ('userG', 'password');
-- insert into user (username, password) values ('userG', 'password');
-- insert into user (username, password) values ('userG', 'password');
