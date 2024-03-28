CREATE OR ALTER PROC get_profile @user_id INT
AS
BEGIN
SELECT u.id,u.full_name,u.email,u.url_avatar,u.date_of_birth,u.describe,u.is_online,u.url_image_cover,(
SELECT COUNT(*) 
FROM dbo.friend AS f 
WHERE 
f.is_accept=1 
AND
f.user_id_receiver=@user_id
AND
f.user_id_sender=@user_id ) 
AS amount_friend
,
(SELECT COUNT(*)
FROM dbo.post AS p
WHERE p.is_delete=0 AND p.user_id_create=@user_id)
AS amount_post
FROM dbo.[user] AS u
WHERE u.id=@user_id
END

EXEC dbo.get_profile  @user_id = 1000

CREATE OR ALTER PROC get_post_in_profile
    @user_id INT,
    @start_getter INT,
	@amount_post_get INT
AS
BEGIN
    SELECT p.id_post,
           p.caption,
           p.time_create_post,
           COUNT(i.post_id_interact) AS amount_interact,
           COUNT(c.id_post) AS amount_comment
    FROM dbo.post AS p
        LEFT JOIN dbo.interact AS i
            ON i.post_id_interact = p.id_post
        LEFT JOIN dbo.comment AS c
            ON p.id_post = c.id_post
    WHERE c.is_delete = 0
          AND p.user_id_create = @user_id
          AND p.is_delete = 0
    GROUP BY p.id_post,
             p.time_create_post,
             p.caption
    ORDER BY p.time_create_post OFFSET @start_getter ROWS FETCH NEXT @amount_Post_get ROWS ONLY;
END;



