using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using InstagramServer.IServices;
using InstagramServer.Models;
using Microsoft.AspNetCore.Mvc;

namespace InstagramServer.Controllers
{
    [Route("[controller]")]
    public class PostsController : Controller
    {
        private readonly IPostService _postService;

        public PostsController(IPostService postService)
        {
            _postService = postService;
        }

        [HttpGet]
        public async Task<IEnumerable<Post>> Get()
        {
            return await _postService.GetPosts();
        }

        [HttpGet("{id}", Name = "GetPost")]
        public async Task<IActionResult> GetById(Guid id)
        {
            var post = await _postService.GetByUserId(id);
            if (post == null)
            {
                return NotFound();
            }

            return Ok(post);
        }

        [HttpPost]
        public async Task<IActionResult> AddPost([FromBody] Post post)
        {
            if (post == null)
            {
                return BadRequest();
            }
            var createdPost = await _postService.Add(post);

            return CreatedAtRoute("GetPost", new {id = createdPost.PostId}, createdPost);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeletePost(int id)
        {
            await _postService.DeletePost(id);

            return NoContent();
        }
    }
}