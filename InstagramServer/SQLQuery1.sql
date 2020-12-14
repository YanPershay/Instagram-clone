use inst

go

select * from posts
select * from userdata
select * from Users

select len(image) from Posts

--delete from Users where UserId != 'BE2A17A4-F601-4297-A4F4-3F8DE7140EA8'
--delete from Userdata where UserId != 'BE2A17A4-F601-4297-A4F4-3F8DE7140EA8'
--alter table users alter column password nvarchar(max)
--alter table Userdata drop column Username
--alter table Users add unique(Username)
--BA6C9BD4-7AEF-41E5-973E-3B6527C80157 new_user
--BE2A17A4-F601-4297-A4F4-3F8DE7140EA8 yan_pershay
--41F6DA3A-FE33-4611-A38A-AB473CC13760 skarli
--alter table Users drop column Username
--alter table userdata add Username nvarchar(20)

--alter table Users alter column Email nvarchar(140)
--alter table Users alter column Password nvarchar(40)
--alter table Users alter column Username nvarchar(40)
--alter table Posts alter column Image nvarchar(max)
--alter table Posts alter column DateCreated date
--go

--alter table Posts add commentid int
--foreign key (commentid) references comments(commentid)

--alter table Comments add foreign key (PostId) references posts(PostId) 

--alter table UserData add IdData int identity(1,1)

--alter table Posts drop CommentId

--select len(image) from posts

--delete Posts

