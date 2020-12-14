using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using InstagramServer.Models;

namespace InstagramServer.IServices
{
    public interface IPostService
    {
        Task<Post> Add(Post post);
        Task<IEnumerable<Post>> GetPosts();
        Task<IEnumerable<Post>> GetByUserId(Guid id);
        Task DeletePost(int id);
    }
}