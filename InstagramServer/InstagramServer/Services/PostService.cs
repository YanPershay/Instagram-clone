using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using InstagramServer.IServices;
using InstagramServer.Models;
using Microsoft.EntityFrameworkCore;

namespace InstagramServer.Services
{
    public class PostService : IPostService
    {
        private readonly InstagramContext _context;

        public PostService(InstagramContext context)
        {
            _context = context;
        }

        public async Task<Post> Add(Post post)
        {
            var date = DateTime.UtcNow.Date;
            post.DateCreated = date;
            var addedPost = _context.Add(post);
            await _context.SaveChangesAsync();
            post.PostId = addedPost.Entity.PostId;
            return post;
        }

        public async Task<IEnumerable<Post>> GetPosts()
        {
            var posts = await _context.Posts.OrderByDescending(x => x.PostId).Include(nameof(User)).ToListAsync();

            return posts;
        }

        public async Task<IEnumerable<Post>> GetByUserId(Guid id)
        {
            return await _context.Posts.Where(x => x.User.UserId == id).OrderByDescending(x => x.PostId).Include(nameof(User)).ToListAsync();
        }

        public async Task DeletePost(int id)
        {
            var postToDelete = await _context.Posts.SingleOrDefaultAsync(x => x.PostId == id);
            _context.Remove(postToDelete);
            await _context.SaveChangesAsync();
        }
    }
}